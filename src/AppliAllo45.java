import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppliAllo45 extends Application {
    /**scene de l app */
    private Scene scene;
    /**les sondages de la session en cours */
    private List<Sondage> sondages;
    /**utilisateur en cours */
    private Utilisateur util;
    /**vue connexion */
    private Login fenLog;
    /**modele analyste */
    private AnalysteJDBC modAna;
    /**modele sondeur */
    private SondeurJDBC modSond;
    /**modele general */
    private GeneralJDBC modGen;
    /**connexion sql */
    private ConnexionMySQL conn; 
    /**vue sondeur */
    private FenetreSondeur fenSond;
    /**vue acceuil analyste*/
    private FenetreAccueilAnalyste fenAcAna;
    /** vue analyste */
    private FenetreAnalyste fenAna;
    /**bouton de deconnexion */
    private Button decoB;

    

    public void init() throws ClassNotFoundException{
     
        this.conn=new ConnexionMySQL();
        this.modGen=new GeneralJDBC(this.conn);
        this.decoB = new Button("Déconnexion");
        this.decoB.setOnAction(new ControleurDeconnexion(this, this.conn));//init bouton de deco car commun a tout
        //init de la vue connexion car première vue       
        Button bConn = new Button("Connexion");
        this.fenLog= new Login(bConn);
        bConn.setOnAction(new ControleurConnexion(this,this.modGen,this.fenLog,this.conn));
        
    }
    /**
     * set les sondages de la session
     * @param s
     */
    public void setSondages(List<Sondage> s){
        this.sondages=s;
    }
    /**
     * mise a jour de l affichage
     * @throws SQLException
     * @throws ParseException
     */
    public void majAffichage() throws SQLException, ParseException{
        //si on est dans le module analyste 
        if (this.modAna !=null){
            this.fenAna.center(this.modAna.gQuestion());
            this.fenAna.bottom();
            this.afficheAna();
        }
        //si on est dans le module sondeur
        else if(this.modSond !=null){
            Question q = this.modSond.getQuestion();
            this.fenSond.bottom();
            this.fenSond.top();
            this.fenSond.left();
            this.fenSond.right();
            if(q.getType().equals("u")){            
                this.fenSond.modeChoixS(this.modSond.getQuestion());
                this.afficheSondeur1();
            }
            else if(q.getType().equals("n")){
                this.fenSond.modeChoixN(this.modSond.getQuestion());
                this.afficheSondeur1();
            } 
            else if(q.getType().equals("l")){
                this.fenSond.modeChoixL(this.modSond.getQuestion());
                this.afficheSondeur1();
            }
            else if(q.getType().equals("c")){
                this.fenSond.modeChoixC(this.modSond.getQuestion());
                this.afficheSondeur1();
            }
            else{this.fenSond.modeChoixM(this.modSond.getQuestion());
                this.afficheSondeur1();
            }

        }
    }
    /**
     * met en place le modele sondeur
     * @throws SQLException
     * @throws ParseException
    */
    public void modesondeur() throws SQLException, ParseException{
        List<Sondage> s =this.modGen.getSondageSondeur();
        this.setSondages(s);
        this.modSond= new SondeurJDBC(this.sondages.get(0), this.sondages.get(0).getQuestions().get(0),this.conn,this.sondages);
        Button btnPrec = new Button();
        btnPrec.setOnAction(new ControleurBoutonPrec(this, this.modSond,null));
        Button btnSuiv = new Button();
        btnSuiv.setOnAction(new ControleurBoutonSuiv(this, this.modSond, null));
        Button btnAnnuler = new Button("Annuler");
        Button next = new Button("Suivant");
        btnAnnuler.setOnAction(new ControleurBoutonSuivUtil(this, this.modSond));
        next.setOnAction(new ControleurBoutonSuivUtil(this, this.modSond));
        Question q = this.sondages.get(0).getQuestion(0);
        this.modSond.setQuestion(q);
        this.modSond.setSondage(s.get(0));
        this.modSond.setSonde(s.get(0).getPanel().getListSonde().get(0));
        this.fenSond= new FenetreSondeur(btnPrec,btnSuiv,next,btnAnnuler,this.decoB,this.modSond);
        this.modSond.setVue(this.fenSond);
        if(q.getType().equals("u")){            
            this.fenSond.modeChoixS(this.modSond.getQuestion());
            this.afficheSondeur1();
        }
        else if(q.getType().equals("n")){
            this.fenSond.modeChoixN(this.modSond.getQuestion());
            this.afficheSondeur1();
        } 
        else if(q.getType().equals("l")){
            this.fenSond.modeChoixL(this.modSond.getQuestion());
            this.afficheSondeur1();
        }
        else if(q.getType().equals("c")){
            this.fenSond.modeChoixC(this.modSond.getQuestion());
            this.afficheSondeur1();
        }
        else{this.fenSond.modeChoixM(this.modSond.getQuestion());
            this.afficheSondeur1();
        }

    }
    /**
     * met en place l accueil de l analyste
     * @throws SQLException
     * @throws ParseException
     */
    public void modeAcAnalyste() throws SQLException, ParseException{
        List<Sondage> s =this.modGen.getSondageAnalyste();
        this.setSondages(s);
        this.modAna= new AnalysteJDBC(this.conn, this.sondages.get(0).getQuestions().get(0),this.sondages.get(0));
        Button decoB = new Button("Déconnexion");
        decoB.setOnAction(new ControleurDeconnexion(this, this.conn));
        this.fenAcAna= new FenetreAccueilAnalyste(decoB,this.modGen,this,this.modAna);
        this.afficheAcceuilAna();
        

    }
    /**
     * met en place le module analyste
     * @param nomSond
     * @throws SQLException
     * @throws ParseException
     */
    public void modeanalyste(String nomSond) throws SQLException, ParseException{
        Sondage sondage = null;
        for (Sondage s:this.sondages){
            if (s.getTitreS().equals(nomSond)){
                sondage=s;
            }
        }
        this.modAna= new AnalysteJDBC(this.conn,sondage.getQuestion(0) ,sondage);
        Button btnPrec = new Button();
        btnPrec.setOnAction(new ControleurBoutonPrec(this, this.modSond, this.modAna));
        Button btnSuiv = new Button();
        btnSuiv.setOnAction(new ControleurBoutonSuiv(this, this.modSond, this.modAna));
        Button decoB = new Button("Déconnexion");
        decoB.setOnAction(new ControleurDeconnexion(this, this.conn));
        Button accueil = new Button("Accueil");
        accueil.setOnAction(new ControleurAcceuil(this));
        this.modAna.setReponses(this.modAna.gQuestion(), this.modAna.getSondage());
        this.fenAna= new FenetreAnalyste(btnPrec,btnSuiv,accueil ,decoB, this.modAna,this);
        this.fenAna.center(this.modAna.gQuestion());
        this.afficheAna();

    }
    /**
     * affiche l accueil de l analyste
     * @throws SQLException
     * @throws ParseException
     */
    public void afficheAcceuilAna() throws SQLException, ParseException{
        this.scene.setRoot(this.fenAcAna);
    }
    /**
     * affiche le sondeur
     */
    public void afficheSondeur1(){
        this.scene.setRoot(this.fenSond);

    }
    /**
     * affiche l analyste
     */
    public void afficheAna(){
        this.scene.setRoot(this.fenAna);
    }
    /**
     * affiche la fin pour le module analyste
     */
    public void afficheFinanalyste(){
        Button accueil = new Button("Accueil");
        accueil.setOnAction(new ControleurAcceuil(this));
        Pane root = new FenetreFinAnalyste(this.modAna,accueil);
        this.scene.setRoot(root);
    }
    
    /**
     * methode de start de l appli
     */
    @Override
    public void start(Stage primaryStage){
        Button bConn = new Button("Connexion");
        bConn.setOnAction(new ControleurConnexion(this,this.modGen,this.fenLog,this.conn));
        Pane root = this.fenLog;
        this.scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Allo45");
        primaryStage.show();
    }
    /**
     * set l utilisateur actuel
     * @param u
     */
    public void setUtilisateur( Utilisateur u){
        this.util=u;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void quitte(){
        Platform.exit();
    }

}
