import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;


public class AnalysteJDBC{
	/**la question actuelle */
	private Question questionAct;
	/**le sondage actuel */
	private Sondage sondageAct;
	/**La connnexion pour les requêtes */
	private ConnexionMySQL laConnexion;
	/**un statement sql */
	private Statement st;
	/**la liste des rep à la question actuelle */
	private List<Reponse> reponsesAct;
	/**
	 * le constructeur
	 * @param laConnexion
	 * @param question
	 * @param sondageAct
	 */
	public AnalysteJDBC(ConnexionMySQL laConnexion, Question question, Sondage sondageAct){
		this.laConnexion=laConnexion;
		this.questionAct = question;
		this.sondageAct = sondageAct;
		this.reponsesAct = new ArrayList<>();}
	/**
	 * fonction non finie
	 * @throws SQLException
	 */
	public void triParAge() throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select numeSond , idTr from SONDE natural join CARACTERISTIQUE natural join TRANCHE group by idTr order by idTr;");
	} 
	/**
	 * fonction non finie
	 * @throws SQLException
	 */
    public void triParCat() throws SQLException{ 
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("select numeSond , idCat from SONDE natrual join CARACTERISTIQUE natural join CATEGORIE group by idCat order by idCat;");
    }
	/**
	 * get question actuelle
	 */
	public Question gQuestion(){
		return this.questionAct;
	}
	/**
	 * get sondage actuel
	 * @return
	 */
	public Sondage getSondage(){
		return this.sondageAct;
	}
	/**
	 * get une question a un indice particulier
	 * prevu pour le bouton aller à
	 * @param ind
	 * @return
	 * @throws SQLException
	 */
	public Question getQuestion(int ind) throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select  idQ,numQ, texteQ , MaxVal,idT from QUESTION natural join QUESTIONNAIRE where numQ = " + ind+";");
		rs.next();
		Question que = new Question(rs.getInt(1),rs.getInt(2) ,rs.getString(3), rs.getInt(4),rs.getString(5));
		return que;
	}
	/**
	 * passe à la question suivante
	 */
	public void questionSuiv(){
		if(!this.derniereQuest()){
			
		this.questionAct= this.sondageAct.getQuestion(this.sondageAct.getQuestions().indexOf(this.questionAct)+1);
	}	
	}
	/**
	 * passe à la question précédente
	 */
	public void questionPrec(){
		if(!this.premiereQuestion()){
		this.questionAct= this.sondageAct.getQuestion(this.sondageAct.getQuestions().indexOf(this.questionAct)-1);}
	}
	/**
	 * verifie si c'est la dernière question
	 * @return
	 */
	public boolean derniereQuest(){
		if(this.sondageAct.getQuestions().indexOf(this.questionAct) == this.sondageAct.getQuestions().size() -1){
				return true;
			}
		
		return false;
		
	}
	/**
	 * verifie si c'est la première question
	 * @return
	 */
	public boolean premiereQuestion(){
	
		if(this.sondageAct.getQuestions().indexOf(this.questionAct) == 0){
				return true;	
		}
		return false;
	}
	/**
	 * set la question act
	 * @param question
	 */
	public void setQuestionAct(Question question){
		this.questionAct = 	question;
	}
	/**
	 * set le sondage act
	 * @param sondage
	 */
	public void setSondageAct(Sondage sondage){
		this.sondageAct = sondage;
	}
	/**
	 * recupere les reps a la question actuelle et les mets dans la variable
	 * @param q
	 * @param sond
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void setReponses(Question q, Sondage sond) throws SQLException, ParseException{
		st = laConnexion.createStatement();
		//requete pour avoir les choses necessaires pour ajouter une reponse
		ResultSet rs = st.executeQuery("Select valeur,idT,numSond,nomSond,prenomSond,dateNaisSond,telephoneSond,idC,sexe,idTr,valDebut,valFin,idCat,intituleCat from REPONDRE NATURAL JOIN QUESTION NATURAL JOIN SONDE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE NATURAL JOIN CATEGORIE where numQ = " + q.getNumQ() + " and idQ = "+ sond.getId());
		List<Reponse> reponses = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		while(rs.next()){
			if(rs.getString(2).equals("u")){       
				Sonde s =new Sonde(rs.getInt(3), rs.getString(4), rs.getString(5), formatter.parse(rs.getString(6)), rs.getString(7), rs.getString(9), rs.getString(13), rs.getString(14), rs.getString(10),rs.getInt(11),rs.getInt(12),rs.getString(8));
				Reponse r = new ReponseSimple(this.sondageAct, this.questionAct, s,  rs.getString(1));
				reponses.add(r);
			}
			else if(rs.getString(2).equals("n")){
				Sonde s =new Sonde(rs.getInt(3), rs.getString(4), rs.getString(5), formatter.parse(rs.getString(6)), rs.getString(7), rs.getString(9), rs.getString(13), rs.getString(14), rs.getString(10),rs.getInt(11),rs.getInt(12),rs.getString(8));
				Reponse r = new ReponsesNote(this.sondageAct, this.questionAct, s,  rs.getInt(1));
				reponses.add(r);
			} 
			else if(rs.getString(2).equals("l")){
				Sonde s =new Sonde(rs.getInt(3), rs.getString(4), rs.getString(5), formatter.parse(rs.getString(6)), rs.getString(7), rs.getString(9), rs.getString(13), rs.getString(14), rs.getString(10),rs.getInt(11),rs.getInt(12),rs.getString(8));
				Reponse r = new ReponseSimple(this.sondageAct, this.questionAct, s,  rs.getString(1));
				reponses.add(r);
			}
			else if(rs.getString(2).equals("c")){
				
				Sonde s =new Sonde(rs.getInt(3), rs.getString(4), rs.getString(5), formatter.parse(rs.getString(6)), rs.getString(7), rs.getString(9), rs.getString(13), rs.getString(14), rs.getString(10),rs.getInt(11),rs.getInt(12),rs.getString(8));
				Reponse r = new ReponseClassement(this.sondageAct, this.questionAct, s,  rs.getString(1));
				reponses.add(r);
			}
			else{
				Sonde s =new Sonde(rs.getInt(3), rs.getString(4), rs.getString(5), formatter.parse(rs.getString(6)), rs.getString(7), rs.getString(9), rs.getString(13), rs.getString(14), rs.getString(10),rs.getInt(11),rs.getInt(12),rs.getString(8));
				Reponse r = new ReponseMultiple(this.sondageAct, this.questionAct, s,  rs.getString(1));
				reponses.add(r);
			}
		}
		this.reponsesAct=reponses;
	}
	/**
	 * fnction pour avoir la pie (note,simple,multiple,libre)
	 * @return
	 * @throws SQLException
	 */
	public PieChart getPie() throws SQLException{
		st = laConnexion.createStatement();
		//get type question
		ResultSet rsBase = st.executeQuery("Select idT from TYPEQUESTION NATURAL JOIN QUESTION where numQ = " +questionAct.getNumQ());
		rsBase.next();
		if (rsBase.getString(1).equals("u")){
			PieChart camembert = new PieChart();
			HashMap<String,Integer> dico_rep = new HashMap<>();
			for(Reponse reponse : this.reponsesAct){
				ReponseSimple repSimple = (ReponseSimple)(reponse);
				st = laConnexion.createStatement();
				ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.Valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV where REPONDRE.valeur = \"" + repSimple.getValeurReponseSimple() + "\" group by REPONDRE.valeur;");
				//puis creer des slices en for pour chaque reponses avec en valeur l'occurence
				while(rs.next()){
					if(!(dico_rep.containsKey(rs.getString(2)))){
						dico_rep.put(rs.getString(2), rs.getInt(1));
					}
				}	
				
			}
			
			for (String key : dico_rep.keySet()){
				PieChart.Data slice = new PieChart.Data(key, dico_rep.get(key));
				slice.setName(key+"->"+slice.getPieValue());
				camembert.getData().add(slice);
			}
			return camembert;
		}
		else if (rsBase.getString(1).equals("m")){
			PieChart camembert = new PieChart();
			HashMap<String,Integer> dico_rep = new HashMap<>();
			for(Reponse reponse : this.reponsesAct){
				ReponseMultiple repMultiple = (ReponseMultiple)(reponse);
				st = laConnexion.createStatement();
				ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.Valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV where REPONDRE.valeur = \"" + repMultiple.getReponses() + "\" group by REPONDRE.valeur;");
				while(rs.next()){
					String[] vals = rs.getString(2).split(";");
					for(String item :vals){
						if(!(dico_rep.containsKey(item))){
							dico_rep.put(item, rs.getInt(1));
						}
					}
				}
			}
			for (String key : dico_rep.keySet()){
				PieChart.Data slice = new PieChart.Data(key, dico_rep.get(key));
				slice.setName(key+"->"+slice.getPieValue());
				camembert.getData().add(slice);
			}
			return camembert;

		}
		else if (rsBase.getString(1).equals("c")){
			System.out.println("Représentation indisponible pour les réponses de type 'classement'");
			
		} 
		else if (rsBase.getString(1).equals("n")){
			PieChart camembert = new PieChart();
			HashMap<String,Integer> dico_rep = new HashMap<>();
			for(Reponse reponse : this.reponsesAct){
				ReponsesNote rep = (ReponsesNote)(reponse);
				st = laConnexion.createStatement();
				ResultSet rs = st.executeQuery("Select count(valeur) , valeur from REPONDRE where valeur = \"" + rep.getNote() + "\" group by valeur;");
				while(rs.next()){
					if(!(dico_rep.containsKey(rs.getString(2)))){
						dico_rep.put(rs.getString(2), rs.getInt(1));
					}
				}	
				
			}
			
			for (String key : dico_rep.keySet()){
				PieChart.Data slice = new PieChart.Data(key, dico_rep.get(key));
				slice.setName(key+"->"+slice.getPieValue());
				camembert.getData().add(slice);
			}
			return camembert;
			}

		else {
			PieChart camembert = new PieChart();
			HashMap<String,Integer> dico_rep = new HashMap<>();
			for(Reponse reponse : this.reponsesAct){
				ReponseSimple repSimple = (ReponseSimple)(reponse);
				st = laConnexion.createStatement();
				ResultSet rs = st.executeQuery("Select count(valeur),valeur from REPONDRE where valeur = \"" + repSimple.getValeurReponseSimple() + "\" group by valeur;");
				//puis creer des slices en for pour chaque reponses avec en valeur l'occurence
				while(rs.next()){
					if(!(dico_rep.containsKey(rs.getString(2)))){
						dico_rep.put(rs.getString(2), rs.getInt(1));
					}
				}
			}

			for (String key : dico_rep.keySet()){
				PieChart.Data slice = new PieChart.Data(key, dico_rep.get(key));
				slice.setName(key+"->"+slice.getPieValue());
				camembert.getData().add(slice);
			}
			return camembert;
		}
		return new PieChart(); 
	}
	/**
	 * get histogramme(classement) mais on a pas réussi à ajouter les données comme on le voulait
	 * @return
	 * @throws SQLException
	 */
	public BarChart<String,Number> getHisto() throws SQLException{
		HashMap<String,Integer> dico_rep1 = new HashMap<>();
		HashMap<String,Integer> dico_rep2 = new HashMap<>();
		HashMap<String,Integer> dico_rep3 = new HashMap<>();
		int max =0;
		st = laConnexion.createStatement();
		ResultSet rsBase = st.executeQuery("Select idT  from TYPEQUESTION NATURAL JOIN QUESTION where numQ = " +questionAct.getNumQ());
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel(this.sondageAct.getTitreS());
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Percent");
		BarChart<String, Number> battons = new BarChart<String, Number>(xAxis,yAxis);	
		rsBase.next();
		List<String> noms=new ArrayList<>();
		if (rsBase.getString(1).equals("c")){
			for(Reponse reponse : this.reponsesAct){
				ReponseClassement repClassement = (ReponseClassement)(reponse);
				st = laConnexion.createStatement();
				ResultSet rs = st.executeQuery("Select REPONDRE.Valeur,MaxVal from QUESTION NATURAL JOIN REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV where REPONDRE.valeur = \""+repClassement.getReponseClassement()+"\"");
				while(rs.next()){
					max = rs.getInt(2);
					String[] vals= rs.getString(1).split(";");
					int i=1;
					for (String val :vals){
						ResultSet rsV = st.executeQuery("Select Valeur from VALPOSSIBLE where idV ="+val);
						rsV.next();
						String nv= rsV.getString(1);
						if (! noms.contains(nv)){noms.add(nv);}
						if(i==1 ){
								if(!(dico_rep1.containsKey(nv))){
									dico_rep1.put(nv,1);
									}
								else{dico_rep1.put(nv,dico_rep1.get(nv)+1);}
						}
						if(i==2 ){

							if(!(dico_rep2.containsKey(nv))){
								dico_rep2.put(nv,1);
								}
							else{dico_rep2.put(nv,dico_rep2.get(nv)+1);}
						}

						if(i==3 ){
							
							if(!(dico_rep3.containsKey(nv))){
								dico_rep3.put(nv,1);
								}
							else{dico_rep3.put(nv,dico_rep3.get(nv)+1);}
							}
						}
						i++;
					
				} 
				
					
				}
				XYChart.Series<String,Number> dataSeries1 = new XYChart.Series<>();
				dataSeries1.setName("premier");
				XYChart.Series<String,Number> dataSeries2 = new XYChart.Series<>();
				dataSeries2.setName("deuxième");
				XYChart.Series<String,Number> dataSeries3 = new XYChart.Series<>();
				dataSeries3.setName("troisième");
				for (int ind=0;ind<max;ind++){
					String nom = noms.get(ind);
					if(! dico_rep1.keySet().contains(nom)){
						dataSeries1.getData().add(new XYChart.Data<String,Number>(nom,0));
					}
					else{dataSeries1.getData().add(new XYChart.Data<String,Number>(nom,dico_rep1.get(nom)));}
					if(! dico_rep2.keySet().contains(nom)){
						dataSeries2.getData().add(new XYChart.Data<String,Number>(nom,0));
					}
					else{dataSeries2.getData().add(new XYChart.Data<String,Number>(nom,dico_rep2.get(nom)));}
					if(! dico_rep3.keySet().contains(nom)){
						dataSeries3.getData().add(new XYChart.Data<String,Number>(nom,0));
					}
					else{dataSeries3.getData().add(new XYChart.Data<String,Number>(nom,dico_rep3.get(nom)));}
					
				battons.getData().addAll(dataSeries1,dataSeries2,dataSeries3);
				battons.setBarGap(5);
				}
			
		}
		return battons;
		
	}
	/**
	 * recupere les infos du panel pour la popup du menu
	 * @return
	 */
	public String getInfosPanel(){
		return "Nom du Panel: "+this.sondageAct.getPanel().getNom();
	}
}

