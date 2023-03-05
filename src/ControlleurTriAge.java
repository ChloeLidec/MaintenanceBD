import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class ControlleurTriAge implements EventHandler<ActionEvent>{
    private FenetreAnalyste vue;
    private String type;

    public ControlleurTriAge(FenetreAnalyste vue, String type){
        this.vue=vue;
        this.type=type;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String chart = this.vue.typeGraphe;
        this.vue.changerGrapheTri(chart, "Age");
    }
}
