import javafx.event.EventHandler;
import java.sql.SQLException;
import javafx.event.ActionEvent ;
import javafx.scene.control.Alert;

//controleur pour la connexion
public class ControleurConnexion implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private GeneralJDBC mod;
    private Login fenlog;
    private ConnexionMySQL conn;



    public ControleurConnexion(AppliAllo45 app, GeneralJDBC mod,Login log,ConnexionMySQL co){
        this.app=app;
        this.mod=mod;
        this.fenlog=log;
        this.conn=co;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        try {
            this.conn.connecter();
	    if (this.conn.isConnecte()){
            Utilisateur u =this.mod.getUtilisateur(this.fenlog.getID(), this.fenlog.getMdp());
            //recup l utilisateur
            if (u.getRole().equals("Sondeur")){
                this.app.setUtilisateur(u);
                this.app.modesondeur();
            }
            else if(u.getRole().equals("Analyste")){
                this.app.setUtilisateur(u);
                this.app.modeAcAnalyste();
            }
        }
	    else{this.fenlog.popupErreur();}
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Echec !!!! ");
			alert.setResizable(true);
			alert.setWidth(500);
			alert.setHeaderText("Echec de la connexion au serveur");
			alert.setContentText("Voici le message envoyé par le serveur\n"+e.getMessage());
			alert.showAndWait();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Echec !!!! ");
			alert.setResizable(true);
			alert.setWidth(500);
			alert.setHeaderText("Problème SQL");
			alert.setContentText("Voici le message envoyé par le serveur\n"+e.getMessage());
			alert.showAndWait();
        }

    }
}
