import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//controleur question precedente
public class ControleurBoutonPrec implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private AnalysteDAO moda;



    public ControleurBoutonPrec(AppliAllo45 app,AnalysteDAO modana){
        this.app=app;
        this.moda=modana;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (this.moda!=null){
            this.moda.questionPrec();
        }
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}