import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//controleur bouton suivant
public class ControlleurBoutonSuiv implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private AnalysteDAO moda;
    private FenetreAnalyste fenAna;


    public ControlleurBoutonSuiv(AppliAllo45 app,AnalysteDAO modana,FenetreAnalyste fenAna){
        this.app=app;
        this.moda=modana;
        this.fenAna=fenAna;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (this.moda!=null && (this.moda.derniereQuest())){

                this.app.afficheFinanalyste();
            }
        else{
        if(this.moda!=null){
            System.out.println(this.fenAna);
            System.out.println(this.fenAna.commentaire);
            System.out.println(this.fenAna.commentaire.getText());
            String comm = this.fenAna.commentaire.getText();
            try {
                this.moda.sauvegarderCommentaire(comm, this.moda.getSondage().getId(), this.moda.gQuestion().getNumQ());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.fenAna.commentaire.setText("");
            this.moda.questionSuiv();
            try {
                this.moda.setReponses(this.moda.gQuestion(), this.moda.getSondage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }}            
        
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
}