import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FenetreFinAnalyste  extends BorderPane {
    private Label nomEnt ;
    private Label textent1 ;
    private Label textent2 ;
    private AnalysteDAO ana;
    private Button ba;

    public FenetreFinAnalyste(AnalysteDAO ana,Button ba){
        super();
        this.ana=ana;
        this.ba=ba;
        this.nomEnt = new Label("Nom entreprise");
        try {
            this.textent1 = new Label("Intérogés "+this.ana.getInterroges());
        } catch (SQLException e) {
            this.textent1 = new Label("Nombre d'intérogés introuvable");
        }
        try {
            this.textent2 =new Label("Réponses complètes "+this.ana.getReponsesComplettes());
        } catch (SQLException e) {
            this.textent2 =new Label("Nombre de réponses complètes introuvable");
        }
        this.setStyle(" -fx-base: #ebebeb;");
        this.setTop(this.top());
        this.setCenter(this.gridPane());

    }
    /**met le haut a jour*/
    public BorderPane top(){
        BorderPane top = new BorderPane();
        top.setStyle("-fx-font: 22 arial; -fx-base: #ffffff;");
        Image decone = new Image("deco.png");
        Image allo45 = new Image("logo.png");
        ImageView deco = new ImageView("deco.png");
        ImageView logo = new ImageView("logo.png");
        deco.setImage(decone);
        deco.setFitWidth(20);
        deco.setPreserveRatio(true);
        deco.setSmooth(true);
        deco.setCache(true);
        logo.setImage(allo45);
        logo.setFitWidth(80);
        logo.setFitHeight(80);
        logo.setSmooth(true);
        logo.setCache(true);
        HBox boutonG = new HBox();
        HBox logoG = new HBox();
        Button decoB = new Button("Déconnexion",deco);
        decoB.setStyle("-fx-font: 22 arial; -fx-base: lightgray;-fx-background-radius: 10px;");
       
        this.ba.setStyle("IDLE_BUTTON_STYLE; -fx-background-radius: 10px;");
        Button aide = new Button("Aide");
        aide.setStyle("-fx-background-radius: 10px;");
        HBox centre = new HBox();
        centre.getChildren().addAll(this.ba, aide);
        logoG.getChildren().addAll(logo);
        logoG.setSpacing(20);
        boutonG.getChildren().addAll(decoB);
        logoG.setAlignment(Pos.CENTER);
        boutonG.setAlignment(Pos.CENTER);
        top.setLeft(logoG);
        centre.setSpacing(40);
        centre.setAlignment(Pos.CENTER);
        top.setCenter(centre);
        top.setRight(boutonG);
        return top;
    }
    /**
     * zone des boutons
     * @return
     */
    private GridPane gridPane(){
        GridPane pane = new GridPane();
        VBox zonetxt = new VBox();
        zonetxt.getChildren().add(this.nomEnt);
        nomEnt.setFont(Font.font("Arial",FontWeight.BOLD,32));
        zonetxt.getChildren().add(this.textent1);
        textent1.setFont(Font.font("Arial",FontWeight.BOLD,32));
        zonetxt.getChildren().add(this.textent2);
        textent2.setFont(Font.font("Arial",FontWeight.BOLD,32));
        zonetxt.setAlignment(Pos.CENTER);
        nomEnt.setPadding(new Insets(10, 10, 10, 10));
        textent1.setPadding(new Insets(10, 10, 10, 10));
        textent2.setPadding(new Insets(10, 10, 10, 10));
        HBox centerTest = new HBox();
        centerTest.setAlignment(Pos.CENTER);
        centerTest.setSpacing(30);
        Button bretour = new Button("  Retour \nSondage");
        bretour.setPrefSize(150, 75);
        bretour.setStyle("-fx-font: 22 arial; -fx-base: #c2bbf0;-fx-background-radius: 10px;");  
        Button bclore = new Button("   Clore \nSondage");
        bclore.setPrefSize(150, 75);
        bclore.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");       
        Button bgenerer = new Button("Générer \n    PDF");
        bgenerer.setPrefSize(150, 75);
        bgenerer.setStyle("-fx-font: 22 arial; -fx-base: #f1e3f3;-fx-background-radius: 10px;");
        centerTest.getChildren().add(bretour);
        centerTest.getChildren().add(bclore);
        centerTest.getChildren().add(bgenerer);
        centerTest.setPadding(new Insets(10));
        zonetxt.setStyle("-fx-background-color: #FFFFFF;");
        zonetxt.getChildren().addAll(centerTest);
        pane.setAlignment(Pos.CENTER);
        zonetxt.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null)));
        pane.getChildren().addAll(zonetxt);
        return pane;        
    }
  
}


