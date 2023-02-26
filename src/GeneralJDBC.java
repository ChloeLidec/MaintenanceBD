import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneralJDBC {
	ConnexionMySQL laConnexion;
	Statement st;
	public GeneralJDBC(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}
	/**recup la liste d'utilisateurs */
	public List<Utilisateur> getUtilisateurs() throws SQLException{
		st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT idU ,nomU, prenomU,login , motDePasse, role from Utilisateur");
		List<Utilisateur> user = new ArrayList<>();
		while(rs.next()){
            String role="";
            if (rs.getInt(6)==1){
                role="Concepteur";
            }
            else if(rs.getInt(6)==2){
                role="Sondeur";
            }
            else{role="Analyste";}
            Utilisateur u = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),role);
			user.add(u);
		}
		rs = st.executeQuery("SELECT noms from SERVICE natural left join EMPLOYE group by noms");
		
		return user;
	}
    /**
     * recup les sondages pour le sondeur
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public List<Sondage> getSondageSondeur() throws SQLException, ParseException{
        st = laConnexion.createStatement();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int idp=0;
        String nomp="";
        ResultSet rs = st.executeQuery("Select idQ, Titre From QUESTIONNAIRE where Etat = \"S\"");
        List<Sondage> sondages = new ArrayList<Sondage>();
        while(rs.next()){
            ResultSet rsq = st.executeQuery("Select numQ, texteQ, IFNULL(maxVal,0),idt,idQ From QUESTIONNAIRE NATURAL JOIN QUESTION WHERE idQ="+rs.getInt(1));
            ResultSet rsp = st.executeQuery("Select numSond,nomSond,prenomSond,dateNaisSond,telephoneSond,sexe,idCat,intituleCat,idTr,valDebut,valFin,idPan,nomPan,idC From QUESTIONNAIRE NATURAL JOIN PANEL NATURAL JOIN SONDE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE NATURAL JOIN TRANCHE WHERE idQ="+rs.getInt(1));
            Sondage sond = new Sondage(rs.getInt(1), rs.getString(2), "S");
            List<Sonde> sondes = new ArrayList<>();
            while (rsp.next()){
                idp = rsp.getInt(12);
                nomp= rsp.getString(13);
                Sonde s =new Sonde(rsp.getInt(1), rsp.getString(2), rsp.getString(3), formatter.parse(rsp.getString(4)), rsp.getString(5), rsp.getString(6), rsp.getString(7), rsp.getString(8), rsp.getString(9),rsp.getInt(10),rsp.getInt(11),rsp.getString(14));
                sondes.add(s);
            }
            Panel pan = new Panel(idp, nomp, sondes);
            List<Question> questions = new ArrayList<>();
            while (rsq.next()){
                Question q = new Question(rsq.getInt(5),rsq.getInt(1), rsq.getString(2), rsq.getInt(3), rsq.getString(4));
                questions.add(q);
            }
            sond.setPanel(pan);
            sond.setQuestions(questions);
            sondages.add(sond);
        }
        return sondages;
    } 

        /**
         * recup les sondages pour l analyste
         * @return
         * @throws SQLException
         * @throws ParseException
         */
        public List<Sondage> getSondageAnalyste() throws SQLException, ParseException{
        st = laConnexion.createStatement();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int idp=0;
        String nomp="";
        ResultSet rs = st.executeQuery("Select idQ, Titre,Etat From QUESTIONNAIRE where Etat = \"A\"");
        List<Sondage> sondages = new ArrayList<Sondage>();
        while(rs.next()){
            ResultSet rsq = st.executeQuery("Select numQ, texteQ, IFNULL(maxVal,0),idt,idQ From QUESTIONNAIRE NATURAL JOIN QUESTION WHERE idQ="+rs.getInt(1));
            ResultSet rsp = st.executeQuery("Select numSond,nomSond,prenomSond,dateNaisSond,telephoneSond,sexe,idCat,intituleCat,idTr,valDebut,valFin,idPan,nomPan,idC From QUESTIONNAIRE NATURAL JOIN PANEL NATURAL JOIN SONDE NATURAL JOIN CARACTERISTIQUE NATURAL JOIN CATEGORIE NATURAL JOIN TRANCHE WHERE idQ="+rs.getInt(1));
            Sondage sond = new Sondage(rs.getInt(1), rs.getString(2), "A");
            List<Sonde> sondes = new ArrayList<>();
            while (rsp.next()){
                idp = rsp.getInt(12);
                nomp= rsp.getString(13);
                Sonde s = new Sonde(rsp.getInt(1), rsp.getString(2), rsp.getString(3), format.parse(rsp.getString(4)), rsp.getString(5), rsp.getString(6), rsp.getString(7), rsp.getString(8), rsp.getString(9),rsp.getInt(10),rsp.getInt(11),rsp.getString(14));
                sondes.add(s);
            }
            Panel pan = new Panel(idp, nomp, sondes);
            List<Question> questions = new ArrayList<>();
            while (rsq.next()){
                Question q = new Question(rsq.getInt(5),rsq.getInt(1), rsq.getString(2), rsq.getInt(3), rsq.getString(4));
                questions.add(q);
            }
            sond.setPanel(pan);
            sond.setQuestions(questions);
            sondages.add(sond);
        }
        return sondages;
    } 

    /**recup l utilisateur en fonction d un login et d un mdp */
    public Utilisateur getUtilisateur(String login , String mdp) throws SQLException{
        st = laConnexion.createStatement();
		ResultSet rs = st.executeQuery("Select idU,nomU,prenomU,nomR From UTILISATEUR natural join ROLEUTIL where login=\""+login+"\" and motDePasse=\""+mdp+"\"");
        rs.next();
        Utilisateur unUser = new Utilisateur(rs.getInt(1), rs.getString(2),rs.getString(3),login, mdp,rs.getString(4));
        return unUser;
    }

}
