import java.util.ArrayList;
import java.util.List;

public class ReponseMultiple extends Reponse {
    private String reponses;
    public ReponseMultiple(Sondage sondage, Question question, Sonde sonde,String val) {
        super(sondage, question, sonde);
        this.reponses=val;

    }
    public String getReponse(){
        return this.reponses;
    }
    
}
