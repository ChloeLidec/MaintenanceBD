import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//controleur bouton suivant
public class ControleurBoutonSuiv implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private SondeurJDBC mod;
    private AnalysteJDBC moda;



    public ControleurBoutonSuiv(AppliAllo45 app,SondeurJDBC mod,AnalysteJDBC modana){
        this.app=app;
        this.mod=mod;
        this.moda=modana;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (this.moda!=null && (this.moda.derniereQuest())){

                this.app.afficheFinanalyste();
            }
        else{
        if(this.moda!=null){
            this.moda.questionSuiv();
            try {
                this.moda.setReponses(this.moda.gQuestion(), this.moda.getSondage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }}
            
        else if(this.mod !=null){
                try {
                    this.mod.enregistrerReponse();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                this.mod.questionSuiv();
            }
            
        
        try {
            this.app.majAffichage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
}