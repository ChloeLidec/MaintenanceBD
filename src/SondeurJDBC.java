import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SondeurJDBC {
    private FenetreSondeur vueSondeur;
    private Statement st;
    private Sondage sondageAct;
    private Question questionAct;
    private ConnexionMySQL laConnexion;
    private Sonde sondeact;
    private Panel pan;
    private List<Sondage> sonds;
    

    public SondeurJDBC(Sondage sondage, Question question,ConnexionMySQL conn,List<Sondage> sonds){
        this.sondageAct = sondage;
        this.questionAct = question;
        this.laConnexion=conn;
        this.pan=sondage.getPanel();
        this.sondeact=this.pan.getListSonde().get(0);
        this.sonds=sonds;
    }

    //getteurs setteurs
    public Sondage getSondage(){
        return this.sondageAct;
    }
    public Sonde getSonde(){return this.sondeact;}
    public void setSonde(Sonde s){this.sondeact=s;}
    public Question getQuestion(){
        return this.questionAct;
    }
    public void setSondage(Sondage s){
        this.sondageAct=s;
    }
    public void setQuestion(Question q){
        this.questionAct=q;
    }
    public void setVue(FenetreSondeur vue){
        this.vueSondeur=vue;}

    public void sondeSuiv(){
		if(!this.dernierSond()){
            this.questionAct=this.sondageAct.getQuestion(0);
		    this.sondeact= this.pan.getListSonde().get(this.pan.getListSonde().indexOf(this.sondeact)+1);}
	    else{this.sondageAct=this.sonds.get(this.sonds.indexOf(this.sondageAct)+1);}
    }
        
	

	public boolean dernierSond(){
		return this.pan.getListSonde().indexOf(this.sondeact)==this.pan.getListSonde().size();
		
	}

    public void questionSuiv(){
		if(!this.derniereQuest()){
		this.questionAct= this.sondageAct.getQuestion(this.sondageAct.getQuestions().indexOf(this.questionAct)+1);}
	    else{this.sondeSuiv();}}
        
	public void questionPrec(){
		if(!this.premiereQuestion()){
		this.questionAct= this.sondageAct.getQuestion(this.sondageAct.getQuestions().indexOf(this.questionAct)-1);}
	}

	public boolean derniereQuest(){
		if(this.sondageAct.getQuestions().indexOf(this.questionAct) == this.sondageAct.getQuestions().size() -1){
				return true;
			}
		
		return false;
		
	}

	public boolean premiereQuestion(){
	
		if(this.sondageAct.getQuestions().indexOf(this.questionAct) == 0){
				return true;	
		}
		return false;
	}
    //recup les valeurs possibles de la question(choix et libres)
    public List<String> getValsPossCM() throws SQLException{
        st = laConnexion.createStatement();
        List<String> res = new ArrayList<>();
        ResultSet rs = st.executeQuery("Select Valeur from QUESTION natural join VALPOSSIBLE where numQ = " + this.questionAct.getNumQ());
        while(rs.next()){
            res.add(rs.getString(1));}
		return res;
    }
    //recup la valeur max de la question (note et classement)
    public int getMaxValNC() throws SQLException{
        st = laConnexion.createStatement();
        ResultSet rs = st.executeQuery("Select MaxVal from QUESTION where numQ = " + this.questionAct.getNumQ());  
        rs.next();
		return rs.getInt(1);
    }
    //fonction pour enregister une réponse
    public void enregistrerReponse() throws SQLException{
        String res="";
       
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into REPONDRE (idQ , numQ, idC , valeur) values (?,?,?,?) ");
		ps.setInt(1, this.questionAct.getIdQ());
		ps.setInt(2 , this.questionAct.getNumQ());
		ps.setString(3, this.sondeact.getCaracteristique().getIdC());

        if(this.questionAct.getType().equals("u")){
            for(String val:this.vueSondeur.getCochee()){
            ResultSet rs = st.executeQuery("Select idV from VALPOSSIBLE NATURAL JOIN QUESTION where numQ = " + this.questionAct.getNumQ()+" and Valeur=\""+val+"\""); 
            rs.next();
            ps.setString(4, ""+rs.getInt(1));}
        }
        else if( this.questionAct.getType().equals("c")){
            int taille =this.vueSondeur.getClassement().size();
            int cpt=0;
            for(String val:this.vueSondeur.getClassement()){
                ResultSet rs = st.executeQuery("Select idV from VALPOSSIBLE NATURAL JOIN QUESTION where numQ = " + this.questionAct.getNumQ()+" and Valeur=\""+val+"\""); 
                while(rs.next()){
                    if (cpt<taille-1){
                        res+=rs.getInt(1)+";";
                    }
                    else{
                        res+=rs.getInt(1)+"";
                    }
                }
            }
            ps.setString(4, res);
        }
        else if(this.questionAct.getType().equals("m")){
            int taille =this.vueSondeur.getCochee().size();
            int cpt=0;
            for(String val:this.vueSondeur.getCochee()){
                ResultSet rs = st.executeQuery("Select idV from VALPOSSIBLE NATURAL JOIN QUESTION where numQ = " + this.questionAct.getNumQ()+" and Valeur=\""+val+"\""); 
                while(rs.next()){
                    if (cpt<taille-1){
                        res+=rs.getInt(1)+";";
                    }
                    else{
                        res+=rs.getInt(1)+"";
                    }
                }
            }
            ps.setString(4, res);
        }
        if(this.questionAct.getType().equals("n")){
            ps.setString(4 , ""+this.vueSondeur.getSelectionnee());
        }
        else if(this.questionAct.getType().equals("l")){
           
            ps.setString(4, this.vueSondeur.getTexte());
            if (!(this.questionAct.getRepPoss().contains(this.vueSondeur.getTexte()))){
                //insert dans valpossible de la valeur
                PreparedStatement psi = this.laConnexion.prepareStatement("insert into VALPOSSIBLE(idQ, numQ, idV, Valeur) values (?,?,?,?) ");
                psi.setInt(1, this.questionAct.getIdQ());
                psi.setInt(2 , this.questionAct.getNumQ());
                ResultSet rsi= st.executeQuery("Select IFNULL(Max(idV),0) from VALPOSSIBLE NATURAL JOIN QUESTION where numQ = " + this.questionAct.getNumQ()); 
                rsi.next();
                psi.setInt(3, rsi.getInt(1)+1);
                psi.setString(4,this.vueSondeur.getTexte());
                this.questionAct.getRepPoss().add(this.vueSondeur.getTexte());
                psi.executeUpdate();
        }
        }
        ps.executeUpdate();
    }
    //definir le sondage comme fini
    public void setSondageFini() throws SQLException{
        PreparedStatement ps = laConnexion.prepareStatement("UPDATE QUESTIONNAIRE SET idQ=?, Titre=?, Etat=?;");
        ps.setInt(1,this.sondageAct.getId());
        ps.setString(2,this.sondageAct.getTitreS());
        ps.setBoolean(3,true);
        ps.executeUpdate();
    }

} 
