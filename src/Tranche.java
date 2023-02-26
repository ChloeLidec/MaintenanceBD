//classe objet pour tranche
public class Tranche {
    private String idTr;
    private int intTrancheD;
    private int intTrancheF;
    public Tranche(String idTr, int intTrancheD,int intTrancheF){
        this.idTr = idTr;
        this.intTrancheD = intTrancheD;
        this.intTrancheF=intTrancheF;
    }
    public String getIdTr(){return this.idTr;}
    public int getIntTrancheD(){return this.intTrancheD;}
    public int getIntTrancheF(){return this.intTrancheF;}
    @Override
    public String toString(){
        return this.intTrancheD+"-"+this.intTrancheF+" ans";
    }
}
