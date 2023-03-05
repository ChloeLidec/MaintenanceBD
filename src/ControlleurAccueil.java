import java.sql.SQLException;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//controleur pour le bouton de retour à l accueil
public class ControlleurAccueil implements EventHandler<ActionEvent>{
    private AppliAllo45 app;



    public ControlleurAccueil(AppliAllo45 app){
        this.app=app;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            this.app.modeAcAnalyste();
        } catch (SQLException | ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       

    }
}