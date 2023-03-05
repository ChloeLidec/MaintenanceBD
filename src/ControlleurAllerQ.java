import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
public class ControlleurAllerQ implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private AnalysteDAO moda;
    private TextField tfnumQ;
    private FenetreAnalyste fenAna;


    public ControlleurAllerQ(AppliAllo45 app,AnalysteDAO modana,TextField tfnumQ,FenetreAnalyste fenAna){
        this.app=app;
        this.moda=modana;
        this.tfnumQ = tfnumQ;
        this.fenAna=fenAna;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Integer numQ = null;
        if (tfnumQ == null){
            numQ = this.moda.gQuestion().getNumQ();
        }
        else{
        try{
            numQ = Integer.parseInt(tfnumQ.getText());}
        catch(NumberFormatException e){
            numQ = this.moda.gQuestion().getNumQ();}
        String comm = this.fenAna.commentaire.getText();
        try {
            this.moda.sauvegarderCommentaire(comm, this.moda.getSondage().getId(), this.moda.gQuestion().getNumQ());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.fenAna.commentaire.setText("");
        }
        this.moda.allerAQ(numQ);       
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}

