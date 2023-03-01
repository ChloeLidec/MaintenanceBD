import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
public class ControlleurAllerQ implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private SondeurJDBC mod;
    private AnalysteJDBC moda;
    private TextField tfnumQ;


    public ControlleurAllerQ(AppliAllo45 app,SondeurJDBC mod,AnalysteJDBC modana,TextField tfnumQ){
        this.app=app;
        this.mod=mod;
        this.moda=modana;
        this.tfnumQ = tfnumQ;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Integer numQ = null;
        try{
            numQ = Integer.parseInt(tfnumQ.getText());}
        catch(NumberFormatException e){
            numQ = this.moda.gQuestion().getNumQ();}
        this.moda.allerAQ(numQ);       
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}

