import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//controleur menu analyste
public class ControleurMenuSondageA implements EventHandler<ActionEvent>{
    private FenetreAnalyste vue;



    public ControleurMenuSondageA(FenetreAnalyste vue){
        this.vue=vue;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.vue.popupPanel();
        }
    }