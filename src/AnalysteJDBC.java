import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
		if (rsBase.getString(1).equals("u") || rsBase.getString(1).equals("l") || rsBase.getString(1).equals("n")){
			PieChart camembert = new PieChart();
			//recuperer les reponses a la question actuelle
			ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur;");
			while (reponsesQ.next()){
				PieChart.Data data = new PieChart.Data(reponsesQ.getString(2), reponsesQ.getInt(1));
				data.setName(reponsesQ.getString(2) + " : " + data.getPieValue());
				camembert.getData().add(data);
			}
			return camembert;
		}
		else if (rsBase.getString(1).equals("m")){
			PieChart camembert = new PieChart();
			HashMap<String,Integer> dico_rep = new HashMap<>();
			
				ResultSet reponsesQ = st.executeQuery("Select valeur from REPONDRE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ() + ";");
				while(reponsesQ.next()){
					String[] vals = reponsesQ.getString(1).split(";");
					for(String item :vals){
						ResultSet rsval = st.executeQuery("Select valeur from VALPOSSIBLE where idV = " + item);
						rsval.next();
						String val = rsval.getString(1);
						if(!(dico_rep.containsKey(val))){
							dico_rep.put(val, reponsesQ.getInt(1));
						}
						else{
							dico_rep.put(val, dico_rep.get(val)+reponsesQ.getInt(1));
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
		else{
			System.out.println("Représentation indisponible pour les réponses de type 'classement'");
			
		} 
		
		return new PieChart(); 
	}
	/**
	 * get histogramme(classement) mais on a pas réussi à ajouter les données comme on le voulait
	 * @return
	 * @throws SQLException
	 */
	public BarChart<String,Number> getHisto() throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rsBase = st.executeQuery("Select idT  from TYPEQUESTION NATURAL JOIN QUESTION where numQ = " +questionAct.getNumQ());
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel(this.sondageAct.getTitreS());
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Percent");
		BarChart<String, Number> battons = new BarChart<String, Number>(xAxis,yAxis);	
		rsBase.next();
		//dic of string float
		HashMap<String,Float> dico_rep = new HashMap<>();
		if (rsBase.getString(1).equals("c")){
				st = laConnexion.createStatement();
				ResultSet valPoss = st.executeQuery("Select valeur from VALPOSSIBLE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ());
				while(valPoss.next()){
					dico_rep.put(valPoss.getString(1), 0f);
				}
				ResultSet rs = st.executeQuery("Select valeur from REPONDRE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ());
				//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
				while(rs.next()){
					String[] vals = rs.getString(1).split(";");
					for(int i=0;i<vals.length;i++){
						ResultSet rsval = st.executeQuery("Select valeur from VALPOSSIBLE where idV = " + vals[i]);
						rsval.next();
						String val = rsval.getString(1);
						if(i==0){
							dico_rep.put(val, dico_rep.get(val)+1f);
						}
						else if(i==1){
							dico_rep.put(val, dico_rep.get(val)+0.5f);
						}
						else if(i==2){
							dico_rep.put(val, dico_rep.get(val)+0.25f);
						}
					}	
				} 
				for (String key : dico_rep.keySet()){
					XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
					series.setName(key);
					series.getData().add(new XYChart.Data<String, Number>(key, dico_rep.get(key)));
					battons.getData().add(series);
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

