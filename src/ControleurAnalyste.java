import javafx.event.EventHandler;
import javafx.event.ActionEvent ;
import javafx.scene.control.Button;

//controleur pour acceder à un sondage vis à vis du nom du sondage
public class ControleurAnalyste implements EventHandler<ActionEvent>{
    private AppliAllo45 app;


    public ControleurAnalyste(AppliAllo45 app){
        this.app=app;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        
        Button button = (Button) (actionEvent.getSource());
        String nomSond = button.getText();
        try {
            this.app.modeanalyste(nomSond);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
