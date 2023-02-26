import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//controleur pour passer à l'utilisateur suivant, aussi appliqué sur le bouton annuler
public class ControleurBoutonSuivUtil implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private SondeurJDBC mod;



    public ControleurBoutonSuivUtil(AppliAllo45 app,SondeurJDBC mod){
        this.app=app;
        this.mod=mod;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.mod.sondeSuiv();
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}