import java.util.Date;
//classe objet sondé
public class Sonde {
    private int numSonde;
    private String nomSonde;
    private String prenomSonde;
    private Date dateNaiss;
    private String tel;
    private Caracteristique cara;

    // CONSTRUCTOR
    public Sonde(int numSonde,String nomSonde,String prenomSonde, Date dateNaiss,String tel,String sexe,String idcat, String intcat,String idt,int intTrd,int intTrf,String idc){
        this.numSonde = numSonde;
        this.nomSonde = nomSonde;
        this.prenomSonde = prenomSonde;
        this.dateNaiss = dateNaiss;
        this.tel = tel;
        this.cara=new Caracteristique(sexe, new Tranche(idt, intTrd,intTrf), new Categorie(idcat, intcat),idc);

    }

    // GETTER
    public Caracteristique getCaracteristique(){
       return this.cara;

    }

    public int getNum(){
        return this.numSonde;
    }

    public String getNom(){
        return this.nomSonde;
    }

    public String getPrenom(){
        return this.prenomSonde;
    }

    public Date getDDN(){
        return this.dateNaiss;
    }

    public String getTel(){
        return this.tel;
    } 

    //SETTER
    public void setNum(int numSonde){
        this.numSonde = numSonde;
    }

    public void setNom(String nomSonde){
        this.nomSonde = nomSonde;
    }

    public void setPrenom(String prenomSonde){
        this.prenomSonde = prenomSonde;
    }

    public void setDDN(Date ddn){
        this.dateNaiss = ddn;
    }

    public void setTel(String tel){
        this.tel = tel;
    }


}

