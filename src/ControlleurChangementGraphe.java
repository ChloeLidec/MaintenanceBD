import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlleurChangementGraphe implements EventHandler<ActionEvent>{
    private FenetreAnalyste vue;
    private String type;

    public ControlleurChangementGraphe(FenetreAnalyste vue, String type){
        this.vue=vue;
        this.type=type;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.vue.changerGraphe(this.type);
        }
}
