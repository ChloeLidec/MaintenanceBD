import java.util.List;
//classe pour mettre un panel en objet
public class Panel {
    private int idPan;
    private String nomPan;
    private List<Sonde> sondes;
    public Panel(int id, String nom, List<Sonde> sondes){
        this.idPan=id;
        this.nomPan=nom;
        this.sondes=sondes;
    }
    public int getId(){return this.idPan;}

    public String getNom(){return this.nomPan;}

    public List<Sonde> getListSonde(){return this.sondes;}
    
}
