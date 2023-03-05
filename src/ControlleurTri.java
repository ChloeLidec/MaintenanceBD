import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class ControlleurTri implements EventHandler<ActionEvent>{
    private FenetreAnalyste vue;
    private String type;

    public ControlleurTri(FenetreAnalyste vue, String type){
        this.vue=vue;
        this.type=type;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String chart = this.vue.typeGraphe;
        this.vue.changerGrapheTri(chart,this.type);
    }
}
