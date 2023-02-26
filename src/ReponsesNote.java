public class ReponsesNote extends Reponse {
    private int note;
    public ReponsesNote(Sondage sondage, Question question, Sonde sonde, int note) {
        super(sondage, question, sonde);
        this.note = note;

        
    }
    public int getNote(){return this.note;}
}
