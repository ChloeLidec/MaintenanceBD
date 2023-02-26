import java.util.ArrayList;
import java.util.List;

//classe objet sondage
public class Sondage {
    private int idSond;
    private String titreS; 
    private String etat;
    private List<Question> questions;
    private Panel panel;
    public Sondage(int idSond, String titreS,String etat){
        this.idSond = idSond;
        this.titreS = titreS;
        this.etat = etat;
        this.questions = new ArrayList<>();
        
    }
    public String getEtat(){return this.etat;}
    public String getTitreS(){return this.titreS;}
    public int getId(){return this.idSond;}
    public List<Question> getQuestions(){
        return questions;
    }
    public void setPanel(Panel pan){this.panel=pan;}
    public void setQuestions(List<Question> qu){this.questions=qu;}
    public Question getQuestion(int ind){return questions.get(ind);}
    public Panel getPanel(){return this.panel;}

    

    public List<List<String>> getInfosPanel(Sondage sond){
        List<List<String>> info = new ArrayList<>();
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        info.add(new ArrayList<>());
        info.get(0).add(this.panel.getNom());
        for(Sonde sondes:this.panel.getListSonde()){
            if(!(info.get(1).contains(sondes.getCaracteristique().getSexe()))){
                info.get(1).add(sondes.getCaracteristique().getSexe());
            }
            if(!(info.get(2).contains(sondes.getCaracteristique().gettranche().toString()))){
                info.get(2).add(sondes.getCaracteristique().gettranche().toString());
            }
            if(!(info.get(3).contains(sondes.getCaracteristique().getCategorie().getIntCat()))){
                info.get(3).add(sondes.getCaracteristique().getCategorie().getIntCat());
            }
        }
        return info;
    }
}
