public class ReponseSimple extends Reponse {

    private String valeurReponseSimple;

    public ReponseSimple(Sondage sondage, Question question, Sonde sonde, String valeurReponse) {
        super(sondage, question, sonde);
        this.valeurReponseSimple = valeurReponse;
       
    }

    public String getValeurReponseSimple(){
        return this.valeurReponseSimple;

    }


    
}
