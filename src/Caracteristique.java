public class Caracteristique{
    /**
     * sexe
     */
    private String sexe;
    /**
     * tranche de la car
     */
    private Tranche tranche;
    /**
     * categorie de la car
     */
    private Categorie categorie;
    /**
     * id caracteristique
     */
    private String idC;
    /**
     * constructeur
     */
    public Caracteristique(String sexe , Tranche tranche, Categorie categorie , String idC){
        this.categorie = categorie;
        this.tranche = tranche;
        this.sexe = sexe;
        this.idC = idC;
    }
    //getteurs et setteurs
    public String getIdC(){
        return this.idC;
    }
    public void setSexe(String se){
        this.sexe = se;
    }

    public String getSexe(){
        return this.sexe;
    }


    public void setTranche(Tranche tra){
        this.tranche = tra;
    }

    public Tranche gettranche(){
        return this.tranche;
    }


    public void setCategorie(Categorie cate){
        this.categorie = cate;
    }

    public Categorie getCategorie(){
        return this.categorie;
    }
}   
