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
    private AnalysteDAO modAna;
    /**modele general */
    private GeneralJDBC modGen;
    /**connexion sql */
    private ConnexionMySQL conn; 
    /**vue accueil analyste*/
    private FenetreAccueilAnalyste fenAcAna;
    /** vue analyste */
    private FenetreAnalyste fenAna;
    /**bouton de deconnexion */
    private Button decoB;

    

    public void init() throws ClassNotFoundException{
     
        this.conn=new ConnexionMySQL();
        this.modGen=new GeneralJDBC(this.conn);
        this.decoB = new Button("Déconnexion");
        this.decoB.setOnAction(new ControlleurDeconnexion(this, this.conn));//init bouton de deco car commun a tout
        //init de la vue connexion car première vue       
        Button bConn = new Button("Connexion");
        this.fenLog= new Login(bConn);
        bConn.setOnAction(new ControlleurConnexion(this,this.modGen,this.fenLog,this.conn));
        
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
        
    }
    /**
     * met en place le modele sondeur
     * @throws SQLException
     * @throws ParseException
    */
    
    /**
     * met en place l accueil de l analyste
     * @throws SQLException
     * @throws ParseException
     */
    public void modeAcAnalyste() throws SQLException, ParseException{
        List<Sondage> s =this.modGen.getSondageAnalyste();
        this.setSondages(s);
        this.modAna= new AnalysteDAO(this.conn, this.sondages.get(0).getQuestions().get(0),this.sondages.get(0));
        Button decoB = new Button("Déconnexion");
        decoB.setOnAction(new ControlleurDeconnexion(this, this.conn));
        this.fenAcAna= new FenetreAccueilAnalyste(decoB,this.modGen,this,this.modAna);
        this.afficheAccueilAna();
        

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
        this.modAna= new AnalysteDAO(this.conn,sondage.getQuestion(0) ,sondage);
        Button btnPrec = new Button();
        Button btnSuiv = new Button();
        Button decoB = new Button("Déconnexion");
        decoB.setOnAction(new ControlleurDeconnexion(this, this.conn));
        Button accueil = new Button("Accueil");
        accueil.setOnAction(new ControlleurAccueil(this));
        this.modAna.setReponses(this.modAna.gQuestion(), this.modAna.getSondage());
        this.fenAna= new FenetreAnalyste(btnPrec,btnSuiv,accueil ,decoB, this.modAna,this);
        btnPrec.setOnAction(new ControlleurBoutonPrec(this, this.modAna, this.fenAna));
        btnSuiv.setOnAction(new ControlleurBoutonSuiv(this, this.modAna, this.fenAna));               
        this.fenAna.center(this.modAna.gQuestion());
        this.afficheAna();

    }
    /**
     * affiche l accueil de l analyste
     * @throws SQLException
     * @throws ParseException
     */
    public void afficheAccueilAna() throws SQLException, ParseException{
        this.scene.setRoot(this.fenAcAna);
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
        accueil.setOnAction(new ControlleurAccueil(this));
        Pane root = new FenetreFinAnalyste(this.modAna,accueil,this.fenAna,this);
        this.scene.setRoot(root);
    }
    
    /**
     * methode de start de l appli
     */
    @Override
    public void start(Stage primaryStage){
        Button bConn = new Button("Connexion");
        bConn.setOnAction(new ControlleurConnexion(this,this.modGen,this.fenLog,this.conn));
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
