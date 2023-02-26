public class Categorie {
    /**
     * id
     */
    private String idCat;
    /**
     * intitule
     */
    private String intCat;
    /**
     * constructeur
     * @param idCat
     * @param intCat
     */
    public Categorie(String idCat,String intCat){
        this.idCat = idCat;
        this.intCat = intCat;
    }
    //getteurs et setteurs
    public String getIdCat(){return this.idCat;}
    public String getIntCat(){return this.intCat;}
}
