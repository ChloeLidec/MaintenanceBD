import java.sql.SQLException;
import java.text.ParseException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;





public class FenetreAccueilAnalyste extends BorderPane{

    protected Button deconnexion;
    private GeneralJDBC gene;
    private AppliAllo45 app;
    private AnalysteJDBC ana;

    /**
     * constructeur
     * @param b
     * @param gen
     * @param app
     * @param ana
     * @throws SQLException
     * @throws ParseException
     */
    public FenetreAccueilAnalyste(Button b,GeneralJDBC gen,AppliAllo45 app,AnalysteJDBC ana) throws SQLException, ParseException{
        super();
        this.gene=gen;
        this.app=app;
        this.ana=ana;
        this.setStyle(" -fx-base: #ebebeb;");



        //top 
        BorderPane haut = new BorderPane();
        haut.setPadding(new Insets (20,20,20,20));
        Image decone = new Image("deco.png");
        Image allo45 = new Image("logo.png");
        ImageView deco = new ImageView("deco.png");
        ImageView logo = new ImageView("logo.png");


        deco.setImage(decone);
        deco.setFitWidth(45);
        deco.setPreserveRatio(true);
        deco.setSmooth(true);
        deco.setCache(true);


        logo.setImage(allo45);
        logo.setFitWidth(75);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setCache(true);
        
        
        this.deconnexion = b;
        deconnexion.setStyle("-fx-font: 18 arial; -fx-base: #ebebeb;");
        haut.setLeft(logo);
        haut.setRight(this.deconnexion);

        //center
        GridPane mid = new GridPane();
        
        int i =0;
        int l = 0;
        int c = 1;
        for(Sondage sondage: gene.getSondageAnalyste()){
            if(i<7){
                Button sond = new Button(sondage.getTitreS());
                sond.setOnAction(new ControleurAnalyste(this.app));
                sond.setStyle("-fx-font: 38 arial; -fx-base: #c2bbf0;");
                sond.setPadding(new Insets(50,50,50,50));
                i ++ ;
                mid.add(sond, c, l);
                if (c+1<=3){c++;}
                else{c=1;
                    l++;}
            }
        }
       
        mid.setHgap(30);
        mid.setVgap(30);
        mid.setAlignment(Pos.CENTER);
        this.setTop(haut);
        this.setCenter(mid);

    }

}
