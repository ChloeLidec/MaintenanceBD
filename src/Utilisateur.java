public class Utilisateur {
    private int idU;
    private String nomU;
    private String prenomU;
    private String login;
    private String motDePasse;
    private String role;

    public Utilisateur(int id, String nom,String pren,String login,String mdp,String role){
        this.idU = id;
        this.nomU = nom;
        this.prenomU = pren;
        this.login = login;
        this.motDePasse = mdp;
        this.role = role;

    }
    public String getRole(){return this.role;}
    public String getlogin(){return this.login;}
    public String getMdp(){return this.motDePasse;}
    public int getId(){return this.idU;}
    public String getNom(){return this.nomU;}
    public String getPrenom(){return this.prenomU;}
}
