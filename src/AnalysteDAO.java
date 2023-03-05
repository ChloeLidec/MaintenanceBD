import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;


public class AnalysteDAO{
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
	public AnalysteDAO(ConnexionMySQL laConnexion, Question question, Sondage sondageAct){
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

	public void allerAQ(int id){
		try{
			this.questionAct = this.sondageAct.getQuestion(id-1);
		}
		catch(Exception e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Nombre de question invalide");
			alert.setHeaderText("Le nombre de question est invalide");
			alert.setContentText("Le nombre de question doit être compris entre 1 et " + this.sondageAct.getQuestions().size());
			alert.getDialogPane().setMinHeight(200);
			alert.showAndWait();
		}
		
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
		if (rsBase.getString(1).equals("u") ){
			PieChart camembert = new PieChart();
			//recuperer les reponses a la question actuelle
			ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.texteVal from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur;");
			while (reponsesQ.next()){
				PieChart.Data data = new PieChart.Data(reponsesQ.getString(2), reponsesQ.getInt(1));
				data.setName(reponsesQ.getString(2) + " : " + data.getPieValue());
				camembert.getData().add(data);
			}
			return camembert;
		}
		else if (rsBase.getString(1).equals("l") || rsBase.getString(1).equals("n")){
			PieChart camembert = new PieChart();
			//recuperer les reponses a la question actuelle
			ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur from REPONDRE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur;");
			while (reponsesQ.next()){
				PieChart.Data data = new PieChart.Data(reponsesQ.getString(2), reponsesQ.getInt(1));
				data.setName(reponsesQ.getString(2) + " : " + data.getPieValue());
				camembert.getData().add(data);
			}
			return camembert;
		}
		
		else if (rsBase.getString(1).equals("c")){
			HashMap<String,Float> dico_rep = new HashMap<>();
			ResultSet valPoss = st.executeQuery("Select texteVal from VALPOSSIBLE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ());
				while(valPoss.next()){
					dico_rep.put(valPoss.getString(1), 0f);
				}
				ResultSet rs = st.executeQuery("Select valeur from REPONDRE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ());
				//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
				while(rs.next()){
					String[] vals = rs.getString(1).split(";");
					for(int i=0;i<vals.length;i++){
						ResultSet rsval = st.executeQuery("Select texteVal from VALPOSSIBLE where idV = " + vals[i]);
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
				PieChart camembert = new PieChart();
				for (String key : dico_rep.keySet()){
					PieChart.Data slice = new PieChart.Data(key, dico_rep.get(key));
					slice.setName(key+"->"+slice.getPieValue());
					camembert.getData().add(slice);
				}
				return camembert;
		} 
		return null;
	}

	public PieChart getPieTri(String typeTri) throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rsBase = st.executeQuery("Select idT from TYPEQUESTION NATURAL JOIN QUESTION where numQ = " +questionAct.getNumQ());
		rsBase.next();
		List<String> listeCouleurs = new ArrayList<String>();
		for (int j = 0; j < 10; j++){
			listeCouleurs.add("#"+Integer.toHexString((int)(Math.random()*16777215)));
		}
		if (typeTri.equals("Age")){
		if (rsBase.getString(1).equals("u") ){
			PieChart camembert = new PieChart();
			//recuperer les reponses a la question actuelle
			ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.texteVal,idTr,valDebut,valFin,REPONDRE.valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idTr order by REPONDRE.valeur;");
			int i = -1;
			while (reponsesQ.next()){
				if (i == -1 || i != reponsesQ.getInt(6)){
					i = reponsesQ.getInt(6);
				}
				//check if data already exists
				PieChart.Data data = new PieChart.Data(reponsesQ.getInt(4)+"-"+reponsesQ.getInt(5)+ " " + reponsesQ.getString(2), reponsesQ.getInt(1));
				data.setName(reponsesQ.getInt(4)+"-"+reponsesQ.getInt(5) +" " + reponsesQ.getString(2) + " : " + data.getPieValue());
				camembert.getData().add(data);
				data.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
			}
			//hide legend
			camembert.setLegendVisible(false);
			//set size of chart
			
			return camembert;
		}
		else if (rsBase.getString(1).equals("n")){
			PieChart camembert = new PieChart();
			//recuperer les reponses a la question actuelle
			int i = -1;
			ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur,idTr,valDebut,valFin from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idTr order by REPONDRE.valeur;");
			while (reponsesQ.next()){
				if (i == -1 || i != reponsesQ.getInt(2)){
					i = reponsesQ.getInt(2);
				}
				PieChart.Data data = new PieChart.Data(reponsesQ.getString(4)+"-"+reponsesQ.getString(5) + " " + reponsesQ.getString(2), reponsesQ.getInt(1));
				data.setName(reponsesQ.getString(4)+"-"+reponsesQ.getString(5) + " " + reponsesQ.getString(2) + " : " + data.getPieValue());
				camembert.getData().add(data);
				data.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
			}
			//hide legend
			camembert.setLegendVisible(false);
			//set size of chart
			
			return camembert;
		}
		else if (rsBase.getString(1).equals("l")){
			PieChart camembert = new PieChart();
			//recuperer les reponses a la question actuelle
			String val = "";
			int i = -1;
			ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur,idTr,valDebut,valFin from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idTr order by REPONDRE.valeur;");
			while (reponsesQ.next()){
				if (val.equals("") || !val.equals(reponsesQ.getString(2))){
					val = reponsesQ.getString(2);
					i++;
				}
				PieChart.Data data = new PieChart.Data(reponsesQ.getString(4)+"-"+reponsesQ.getString(5) + " " + reponsesQ.getString(2), reponsesQ.getInt(1));
				data.setName(reponsesQ.getString(4)+"-"+reponsesQ.getString(5) + " " + reponsesQ.getString(2) + " : " + data.getPieValue());
				camembert.getData().add(data);
				data.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
			}
			//hide legend
			camembert.setLegendVisible(false);
			//set size of chart
			
			return camembert;
		}
		
		else if (rsBase.getString(1).equals("c")){
			HashMap<String,HashMap<String,Float>> dico_rep = new HashMap<>();
			
				ResultSet rs = st.executeQuery("Select valeur,idTr,valDebut,valFin from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ()+ " group by valeur,idTr order by idTr");
				//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
				while(rs.next()){
					String[] vals = rs.getString(1).split(";");
					for(int i=0;i<vals.length;i++){
						ResultSet rsval = st.executeQuery("Select texteVal from VALPOSSIBLE where idV = " + vals[i]);
						rsval.next();
						String val = rsval.getString(1);
						if(!dico_rep.containsKey(val)){
							HashMap<String,Float> dico = new HashMap<>();
							dico_rep.put(val, dico);
						}
						if(i==0){
							HashMap<String,Float> dico = dico_rep.get(val);
							if (dico.containsKey(rs.getString(3) + "-" + rs.getString(4))){
								dico.put(rs.getString(3) + "-" + rs.getString(4), dico.get(rs.getString(3) + "-" + rs.getString(4))+1f);
							}
							else{
								dico.put(rs.getString(3) + "-" + rs.getString(4), 1f);
							}
						}
						else if(i==1){
							HashMap<String,Float> dico = dico_rep.get(val);
							if (dico.containsKey(rs.getString(3) + "-" + rs.getString(4))){
								dico.put(rs.getString(3) + "-" + rs.getString(4), dico.get(rs.getString(3) + "-" + rs.getString(4))+0.5f);
							}
							else{
								dico.put(rs.getString(3) + "-" + rs.getString(4), 0.5f);
							}
						}
						else if(i==2){
							HashMap<String,Float> dico = dico_rep.get(val);
							if (dico.containsKey(rs.getString(3) + "-" + rs.getString(4))){
								dico.put(rs.getString(3) + "-" + rs.getString(4), dico.get(rs.getString(3) + "-" + rs.getString(4))+0.25f);
							}
							else{
								dico.put(rs.getString(3) + "-" + rs.getString(4), 0.25f);
							}
						}
					}	
				}
				PieChart camembert = new PieChart();
				int i = -1;
				for (String valrep : dico_rep.keySet()){
					i++;
					for (String tranche : dico_rep.get(valrep).keySet()){
						PieChart.Data slice = new PieChart.Data(tranche+":"+valrep, dico_rep.get(valrep).get(tranche));
						slice.setName(tranche+":"+valrep+"->"+slice.getPieValue());
						camembert.getData().add(slice);
						slice.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
					}
				}
			camembert.setLegendVisible(false);
			//set size of chart
			
			return camembert;
		}}
		else if (typeTri.equals("Socio")){
			HashMap<Integer,String> dico_categ = new HashMap<>();
			if (rsBase.getString(1).equals("u") ){
				PieChart camembert = new PieChart();
				//recuperer les reponses a la question actuelle
				ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.texteVal,idCat,intituleCat,REPONDRE.valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idCat order by REPONDRE.valeur;");
				int i = -1;
				while (reponsesQ.next()){
					if (i == -1 || i != reponsesQ.getInt(5)){
						i = reponsesQ.getInt(5);
					}
					dico_categ.put(reponsesQ.getInt(3), reponsesQ.getString(4));
					PieChart.Data data = new PieChart.Data(reponsesQ.getString(3)+ " " + reponsesQ.getString(2), reponsesQ.getInt(1));
					data.setName(reponsesQ.getString(4) +" " + reponsesQ.getString(2) + " : " + data.getPieValue());
					camembert.getData().add(data);
					data.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
				}
				
				camembert.setLegendVisible(false);
				//set size of chart
				return camembert;
			}
			else if (rsBase.getString(1).equals("n")){
				PieChart camembert = new PieChart();
				//recuperer les reponses a la question actuelle
				int i = -1;
				ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur,idCat,intituleCat from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idCat order by REPONDRE.valeur;");
				while (reponsesQ.next()){
					if (i == -1 || i != reponsesQ.getInt(2)){
						i = reponsesQ.getInt(2);
					}
					PieChart.Data data = new PieChart.Data(reponsesQ.getString(4) + " " + reponsesQ.getString(2), reponsesQ.getInt(1));
					data.setName(reponsesQ.getString(4) + " " + reponsesQ.getString(2) + " : " + data.getPieValue());
					camembert.getData().add(data);
					data.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
				}
				//hide legend
				camembert.setLegendVisible(false);
				//set size of chart
				return camembert;
			}
			else if (rsBase.getString(1).equals("l")){
				PieChart camembert = new PieChart();
				//recuperer les reponses a la question actuelle
				String val = "";
				int i = -1;
				ResultSet reponsesQ = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur,idCat,intituleCat from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idCat order by REPONDRE.valeur;");
				while (reponsesQ.next()){
					if (val.equals("") || !val.equals(reponsesQ.getString(2))){
						val = reponsesQ.getString(2);
						i++;
					}
					PieChart.Data data = new PieChart.Data(reponsesQ.getString(4)+ " " + reponsesQ.getString(2), reponsesQ.getInt(1));
					data.setName(reponsesQ.getString(4)+ " " + reponsesQ.getString(2) + " : " + data.getPieValue());
					camembert.getData().add(data);
					data.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
				}
				//hide legend
				camembert.setLegendVisible(false);
				//set size of chart
				
				return camembert;
			}
			
			else if (rsBase.getString(1).equals("c")){
				HashMap<String,HashMap<String,Float>> dico_rep = new HashMap<>();
				
					ResultSet rs = st.executeQuery("Select valeur,idCat,intituleCat from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ()+ " group by valeur,idCat order by idCat");
					//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
					while(rs.next()){
						String[] vals = rs.getString(1).split(";");
						for(int i=0;i<vals.length;i++){
							ResultSet rsval = st.executeQuery("Select texteVal from VALPOSSIBLE where idV = " + vals[i]);
							rsval.next();
							String val = rsval.getString(1);
							if(!dico_rep.containsKey(val)){
								HashMap<String,Float> dico = new HashMap<>();
								dico_rep.put(val, dico);
							}
							if(i==0){
								HashMap<String,Float> dico = dico_rep.get(val);
								if (dico.containsKey(rs.getString(3))){
									dico.put(rs.getString(3), dico.get(rs.getString(3))+1f);
								}
								else{
									dico.put(rs.getString(3), 1f);
								}
							}
							else if(i==1){
								HashMap<String,Float> dico = dico_rep.get(val);
								if (dico.containsKey(rs.getString(3) )){
									dico.put(rs.getString(3), dico.get(rs.getString(3))+0.5f);
								}
								else{
									dico.put(rs.getString(3), 0.5f);
								}
							}
							else if(i==2){
								HashMap<String,Float> dico = dico_rep.get(val);
								if (dico.containsKey(rs.getString(3))){
									dico.put(rs.getString(3), dico.get(rs.getString(3))+0.25f);
								}
								else{
									dico.put(rs.getString(3), 0.25f);
								}
							}
						}	
					}
					PieChart camembert = new PieChart();
					int i = -1;
					for (String valrep : dico_rep.keySet()){
						i++;
						for (String tranche : dico_rep.get(valrep).keySet()){
							PieChart.Data slice = new PieChart.Data(tranche+":"+valrep, dico_rep.get(valrep).get(tranche));
							slice.setName(tranche+":"+valrep+"->"+slice.getPieValue());
							camembert.getData().add(slice);
							slice.getNode().setStyle("-fx-pie-color: " + listeCouleurs.get(i%listeCouleurs.size()) + ";");
						}
					}
				camembert.setLegendVisible(false);
				//set size of chart
				
				return camembert;
			}
		}
		return null;
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
		yAxis.setLabel("Votes");
		BarChart<String, Number> battons = new BarChart<String, Number>(xAxis,yAxis);	
		rsBase.next();
		//dic of string float
		HashMap<String,Float> dico_rep = new HashMap<>();
		if (rsBase.getString(1).equals("c")){
				ResultSet valPoss = st.executeQuery("Select texteVal from VALPOSSIBLE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ());
				while(valPoss.next()){
					dico_rep.put(valPoss.getString(1), 0f);
				}
				ResultSet rs = st.executeQuery("Select valeur from REPONDRE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ());
				//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
				while(rs.next()){
					String[] vals = rs.getString(1).split(";");
					for(int i=0;i<vals.length;i++){
						ResultSet rsval = st.executeQuery("Select texteVal from VALPOSSIBLE where idV = " + vals[i]);
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
				Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
					@Override
					public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
						return o1.getData().get(0).getYValue().intValue() - o2.getData().get(0).getYValue().intValue();
					}
				});
				return battons;
		}
		else if (rsBase.getString(1).equals("u")){
			ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.texteVal from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur;");
			while(rs.next()){
				XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
				series.setName(rs.getString(2));
				series.getData().add(new XYChart.Data<String, Number>(rs.getString(2), rs.getInt(1)));
				battons.getData().add(series);
			}
			Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
				@Override
				public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
					return o1.getData().get(0).getYValue().intValue() - o2.getData().get(0).getYValue().intValue();
				}
			});
			return battons;
		}
		else if (rsBase.getString(1).equals("n") || rsBase.getString(1).equals("l")){
			ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur from REPONDRE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur;");
			while(rs.next()){
				XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
				series.setName(rs.getString(2));
				series.getData().add(new XYChart.Data<String, Number>(rs.getString(2), rs.getInt(1)));
				battons.getData().add(series);
			}
			if (rsBase.getString(1).equals("n")){
				Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
					@Override
					public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
						//compare int of string
						return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
					}
				});
			}
			return battons;
		}
		
		return null;
		
	}

	public BarChart<String,Number> getHistoTri(String typeTri) throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rsBase = st.executeQuery("Select idT  from TYPEQUESTION NATURAL JOIN QUESTION where numQ = " +questionAct.getNumQ());
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel(this.sondageAct.getTitreS());
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Votes");
		BarChart<String, Number> battons = new BarChart<String, Number>(xAxis,yAxis);	
		rsBase.next();
		if (typeTri.equals("Age")){
		if (rsBase.getString(1).equals("c")){
			HashMap<String,HashMap<String,Float>> dico_rep = new HashMap<>();
			ResultSet rs = st.executeQuery("Select valeur,idTr,valDebut,valFin from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ()+ " group by valeur,idTr order by idTr");
			//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
			while(rs.next()){
				String[] vals = rs.getString(1).split(";");
				for(int i=0;i<vals.length;i++){
					ResultSet rsval = st.executeQuery("Select texteVal from VALPOSSIBLE where idV = " + vals[i]);
					rsval.next();
					String val = rsval.getString(1);
					if(!dico_rep.containsKey(val)){
						HashMap<String,Float> dico = new HashMap<>();
						dico_rep.put(val, dico);
					}
					if(i==0){
						HashMap<String,Float> dico = dico_rep.get(val);
						if (dico.containsKey(rs.getString(3) + "-" + rs.getString(4))){
							dico.put(rs.getString(3) + "-" + rs.getString(4), dico.get(rs.getString(3) + "-" + rs.getString(4))+1f);
						}
						else{
							dico.put(rs.getString(3) + "-" + rs.getString(4), 1f);
						}
					}
					else if(i==1){
						HashMap<String,Float> dico = dico_rep.get(val);
						if (dico.containsKey(rs.getString(3) + "-" + rs.getString(4))){
							dico.put(rs.getString(3) + "-" + rs.getString(4), dico.get(rs.getString(3) + "-" + rs.getString(4))+0.5f);
						}
						else{
							dico.put(rs.getString(3) + "-" + rs.getString(4), 0.5f);
						}
					}
					else if(i==2){
						HashMap<String,Float> dico = dico_rep.get(val);
						if (dico.containsKey(rs.getString(3) + "-" + rs.getString(4))){
							dico.put(rs.getString(3) + "-" + rs.getString(4), dico.get(rs.getString(3) + "-" + rs.getString(4))+0.25f);
						}
						else{
							dico.put(rs.getString(3) + "-" + rs.getString(4), 0.25f);
						}
					}
				}	
			}
				for (String key : dico_rep.keySet()){
					XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
					series.setName(key);
					for (String key2 : dico_rep.get(key).keySet()){
						series.getData().add(new XYChart.Data<String, Number>(key2, dico_rep.get(key).get(key2)));
					}
					battons.getData().add(series);
				}		
				Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
					@Override
					public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
						return o1.getData().get(0).getYValue().intValue() - o2.getData().get(0).getYValue().intValue();
					}
				});
				return battons;
		}
		else if (rsBase.getString(1).equals("u")){
			ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.texteVal,idTr,valDebut,valFin,REPONDRE.valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idTr order by REPONDRE.valeur;");
			String val = "";
			XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
			while(rs.next()){
				if (!val.equals(rs.getString(2))){
					if (!val.equals("")){
						battons.getData().add(series);
					}
					val = rs.getString(2);
					series = new XYChart.Series<String, Number>();
					series.setName(rs.getString(2));
				}
					series.getData().add(new XYChart.Data<String, Number>(rs.getString(4) + "-" + rs.getString(5), rs.getInt(1)));				
			}
			battons.getData().add(series);
			Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
				@Override
				public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
					return o1.getData().get(0).getYValue().intValue() - o2.getData().get(0).getYValue().intValue();
				}
			});
			return battons;
		}
		else if (rsBase.getString(1).equals("n") || rsBase.getString(1).equals("l")){
			ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur,idTr,valDebut,valFin from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN TRANCHE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idTr order by REPONDRE.valeur;");
			String val = "";
			XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
			while(rs.next()){
				if (rsBase.getString(1).equals("l")){
					if (!val.equals(rs.getString(2))){
						if (!val.equals("")){
							battons.getData().add(series);
						}
						val = rs.getString(2);
						series = new XYChart.Series<String, Number>();
						series.setName(rs.getString(2));
					}
						series.getData().add(new XYChart.Data<String, Number>(rs.getString(4) + "-" + rs.getString(5), rs.getInt(1)));
				}
				else if (rsBase.getString(1).equals("n")){
					if (!val.equals(rs.getInt(2)+"")){
						if (!val.equals("")){
							battons.getData().add(series);
						}
						val = rs.getInt(2) + "";
						series = new XYChart.Series<String, Number>();
						series.setName(rs.getInt(2)+"");
					}
						series.getData().add(new XYChart.Data<String, Number>(rs.getString(4) + "-" + rs.getString(5), rs.getInt(1)));
				}
				
			}
			battons.getData().add(series);
			if (rsBase.getString(1).equals("n")){
				Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
					@Override
					public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
						//compare int of string
						return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
					}
				});
			}
			return battons;
		}}
		else if (typeTri.equals("Socio")){
			if (rsBase.getString(1).equals("c")){
				HashMap<String,HashMap<String,Float>> dico_rep = new HashMap<>();
				ResultSet rs = st.executeQuery("Select valeur,idCat,intituleCat from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where idQ = " + this.sondageAct.getId() + " and numQ = " + this.questionAct.getNumQ()+ " group by valeur,idCat order by idCat");
				//je met en place un systeme de points 1ere place =1 2e=0.5 3e=0.25
				while(rs.next()){
					String[] vals = rs.getString(1).split(";");
					for(int i=0;i<vals.length;i++){
						ResultSet rsval = st.executeQuery("Select texteVal from VALPOSSIBLE where idV = " + vals[i]);
						rsval.next();
						String val = rsval.getString(1);
						if(!dico_rep.containsKey(val)){
							HashMap<String,Float> dico = new HashMap<>();
							dico_rep.put(val, dico);
						}
						String cat = rs.getString(3);
						String finalS = "";
						for (String s : cat.split(" ")){
							finalS += s+"\n";
						}
						if(i==0){
							HashMap<String,Float> dico = dico_rep.get(val);
							if (dico.containsKey(finalS)){
								dico.put(finalS, dico.get(finalS)+1f);
							}
							else{
								dico.put(finalS, 1f);
							}
						}
						else if(i==1){
							HashMap<String,Float> dico = dico_rep.get(val);
							if (dico.containsKey(finalS)){
								dico.put(finalS, dico.get(finalS)+0.5f);
							}
							else{
								dico.put(finalS, 0.5f);
							}
						}
						else if(i==2){
							HashMap<String,Float> dico = dico_rep.get(val);
							if (dico.containsKey(finalS)){
								dico.put(finalS, dico.get(finalS)+0.25f);
							}
							else{
								dico.put(finalS, 0.25f);
							}
						}
					}	
				}
					for (String key : dico_rep.keySet()){
						XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
						series.setName(key);
						for (String key2 : dico_rep.get(key).keySet()){
							series.getData().add(new XYChart.Data<String, Number>(key2, dico_rep.get(key).get(key2)));
						}
						battons.getData().add(series);
					}		
					Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
						@Override
						public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
							return o1.getData().get(0).getYValue().intValue() - o2.getData().get(0).getYValue().intValue();
						}
					});
					return battons;
			}
			else if (rsBase.getString(1).equals("u")){
				ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , VALPOSSIBLE.texteVal,idCat,intituleCat,REPONDRE.valeur from REPONDRE JOIN VALPOSSIBLE ON REPONDRE.valeur=VALPOSSIBLE.idV NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idCat order by REPONDRE.valeur;");
				String val = "";
				XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
				while(rs.next()){
					if (!val.equals(rs.getString(2))){
						if (!val.equals("")){
							battons.getData().add(series);
						}
						val = rs.getString(2);
						series = new XYChart.Series<String, Number>();
						series.setName(rs.getString(2));
					}
					String finalS = "";
					for (String s : rs.getString(4).split(" ")){
						finalS += s+"\n";
					}
						series.getData().add(new XYChart.Data<String, Number>(finalS, rs.getInt(1)));		
				}
				battons.getData().add(series);
				Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
					@Override
					public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
						return o1.getData().get(0).getYValue().intValue() - o2.getData().get(0).getYValue().intValue();
					}
				});
				return battons;
			}
			else if (rsBase.getString(1).equals("n") || rsBase.getString(1).equals("l")){
				ResultSet rs = st.executeQuery("Select count(REPONDRE.valeur) , REPONDRE.valeur,idCat,intituleCat from REPONDRE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE where REPONDRE.idQ = " + this.sondageAct.getId() + " and REPONDRE.numQ = " + this.questionAct.getNumQ() + " group by REPONDRE.valeur,idCat order by REPONDRE.valeur;");
				String val = "";
				XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
				while(rs.next()){
					if (rsBase.getString(1).equals("l")){
						if (!val.equals(rs.getString(2))){
							if (!val.equals("")){
								battons.getData().add(series);
							}
							val = rs.getString(2);
							series = new XYChart.Series<String, Number>();
							series.setName(rs.getString(2));
						}
						String finalS = "";
						for (String s : rs.getString(4).split(" ")){
							finalS += s+"\n";
						}
							series.getData().add(new XYChart.Data<String, Number>(finalS, rs.getInt(1)));
					}
					else if (rsBase.getString(1).equals("n")){
						if (!val.equals(rs.getInt(2)+"")){
							if (!val.equals("")){
								battons.getData().add(series);
							}
							val = rs.getInt(2) + "";
							series = new XYChart.Series<String, Number>();
							series.setName(rs.getInt(2)+"");
						}
						String finalS = "";
						for (String s : rs.getString(4).split(" ")){
							finalS += s+"\n";
						}
							series.getData().add(new XYChart.Data<String, Number>(finalS, rs.getInt(1)));
					}
					
				}
				battons.getData().add(series);
				if (rsBase.getString(1).equals("n")){
					Collections.sort(battons.getData(), new Comparator<XYChart.Series<String, Number>>() {
						@Override
						public int compare(XYChart.Series<String, Number> o1, XYChart.Series<String, Number> o2) {
							//compare int of string
							return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
						}
					});
				}
				return battons;
			}
		}
		
		
		return null;
		
	}
	/**
	 * recupere les infos du panel pour la popup du menu
	 * @return
	 */
	public String getInfosPanel(){
		return "Nom du Panel: "+this.sondageAct.getPanel().getNom();
	}
	public int getReponsesComplettes() throws SQLException {
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select count(*) from REPONDRE where idQ = " + this.sondageAct.getId() + " group by numQ;");
		rs.next();
		return rs.getInt(1);
	}
	public int getInterroges() throws SQLException {
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select count(*) from INTERROGER where idQ = " + this.sondageAct.getId() );
		rs.next();
		return rs.getInt(1);
	}

	public String getEntreprise() throws SQLException {
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select raisonSoc from CLIENT NATURAL JOIN QUESTIONNAIRE where idQ = " + this.sondageAct.getId());
		rs.next();
		return rs.getString(1);
	}

	public void sauvegarderCommentaire(String commentaire,int idQ,int numQ) throws SQLException {
		st = laConnexion.createStatement();
		ResultSet rsnumAna = st.executeQuery("Select max(idC) from ANALYSE");
		int numAna = 1;
		if (rsnumAna.next()){
			numAna = rsnumAna.getInt(1) + 1;
		}
		ResultSet rs = st.executeQuery("Select * from ANALYSE where idQ = " + idQ + " and numQ = " + numQ);
		if (rs.next()){
			st.executeUpdate("Update ANALYSE set commentaire = '" + commentaire + "' where idQ = " + idQ + " and numQ = " + numQ);
		}
		else{
			st.executeUpdate("Insert into ANALYSE values (" + idQ + "," + numQ + "," +numAna + ",'" + commentaire + "')");
		}
	}

	public String getCommentaire(int idQ,int numQ) throws SQLException {
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select commentaire from ANALYSE where idQ = " + idQ + " and numQ = " + numQ);
		if (rs.next()){
			return rs.getString(1);
		}
		return "";
	}


}

