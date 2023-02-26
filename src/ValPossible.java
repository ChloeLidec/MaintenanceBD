import java.util.HashSet;

public class ValPossible {
    private int nb;
    private HashSet<String> vals;

    // CONSTRUCTOR
    public ValPossible(int nb, HashSet<String> vals){
        this.nb = nb;
        this.vals = new HashSet<String>();
    }

    //GETTER
    public int getNb(){
        return this.nb;
    }

    public  HashSet<String> getVals(){
        return this.vals;
    }
}
