import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//controleur question precedente
public class ControleurBoutonPrec implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private SondeurJDBC mod;
    private AnalysteJDBC moda;



    public ControleurBoutonPrec(AppliAllo45 app,SondeurJDBC mod,AnalysteJDBC modana){
        this.app=app;
        this.mod=mod;
        this.moda=modana;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (this.moda!=null){
            this.moda.questionPrec();
        }
        else if(this.mod !=null){
            this.mod.questionPrec();
        }
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}