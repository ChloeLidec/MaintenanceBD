public class ReponseClassement extends Reponse {

    private String valeurReponseClassement;

    public ReponseClassement(Sondage sondage, Question question, Sonde sonde,String valeurReponseClassement) {
        super(sondage, question, sonde);
        this.valeurReponseClassement =  valeurReponseClassement;
    }

    public String getReponseClassement(){
        return this.valeurReponseClassement;
    }

}
