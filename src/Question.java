import java.util.ArrayList;
import java.util.List;
//classe objet question
public class Question {
    private int idQ;//id questionnaire
    private int numQ;
    private String texte;
    private int maxVal;
    private String type;
    /**
     * seuleument pour les réponses libres
     */
    private List<String> replibresposs;

    //CONSTRUCTOR
    public Question(int idQ , int numQ,String texte,int maxVal,String type){
        this.numQ = numQ;
        this.texte = texte;
        this.maxVal = maxVal;
        this.type = type;
        this.idQ = idQ;
        this.replibresposs= new ArrayList<>();
    }

    // GETTER
    public int getIdQ(){
        return this.idQ;
    }

    public int getNumQ(){
        return this.numQ;
    }

    public String getTexte(){
        return this.texte;
    }

    public int getMaxVal(){
        return this.maxVal;
    }

    public String getType(){
        return this.type;
    }
    public List<String> getRepPoss(){
        return this.replibresposs;
    }

    //SETTER

    public void setNumQ(int numQ){
        this.numQ = numQ;
    }

    public void setTexte(String texte){
        this.texte = texte;
    }

    public void setMaxVal(int maxVal){
        this.maxVal = maxVal;
    }

    public void setType(String type){
        this.type = type;
    }

   
}
