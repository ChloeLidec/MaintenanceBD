//classe objet abstraite pour la réponse
public abstract class Reponse {
    
    private Sondage sondage;
    private Question question;
    private Sonde sonde;

    // CONSTRUCTOR
    public Reponse(Sondage sondage, Question question, Sonde sonde){
        this.sondage = sondage;
        this.question = question;
        this.sonde = sonde;
    }

    // GETTERS

    public Sondage getSondage(){
        return this.sondage;
    }

    public Question getQuestion(){
        return this.question;
    }

    public Sonde getSonde(){
        return this.sonde;
    }

    // SETTERS

    public void setSondage(Sondage sondage){
        this.sondage = sondage;
    }

    public void setQuestion(Question question){
        this.question = question;
    }

    public void setSonde(Sonde sonde){
        this.sonde = sonde;
    }

    public abstract String getReponse();
}
