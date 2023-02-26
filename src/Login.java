import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class Login extends BorderPane {

    private TextField tfid;
    private PasswordField tMdp;
    private Button bConn;

    public Login (Button b){
        super();
        this.bConn=b;
        this.tfid=new TextField();
        this.tMdp=new PasswordField();
        Image logo = new Image("logo.png");
        ImageView view1 = new ImageView(logo);
        view1.setFitHeight(50);
        view1.setFitWidth(50);
        this.setTop(view1);
        GridPane gp = gridPane();
        this.setCenter(gp);
        gp.setAlignment(Pos.CENTER);
        // pageCo.setStyle("-fx-background-color: grey;");
    
    }
        
    /**
     * item central
     * @return
     */
    private GridPane gridPane(){
        GridPane pane = new GridPane();
        GridPane centerTest = new GridPane();
        Label lTitre = new Label("Entrez votre identifiant et votre mot de passe");
        Label lid = new Label("Identifiant");
        Label lMdp = new Label("Mot de passe");
        this.bConn.setPrefSize(150, 30);
        this.bConn.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;");
        pane.add(lTitre,0,0,2,1);
        pane.add(lid,0,1);
        pane.add(this.tfid,1,1);
        pane.add(lMdp,0,2);
        pane.add(this.tMdp,1,2);
        pane.add(this.bConn,1,3);
        centerTest.setVgap(10);
        centerTest.setHgap(10);
        centerTest.setPadding(new Insets(10));
        centerTest.getChildren().addAll(lTitre,lid,this.tfid,lMdp,this.tMdp ,this.bConn);
        centerTest.setStyle("-fx-background-color: #FFFFFF;");
        pane.getChildren().addAll(centerTest);
        centerTest.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null)));
        // pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        
        // pane.setStyle("-fx-background-color: #FFFFFF;");
        return pane;
    }
    public String getID(){
        
        return this.tfid.getText();
    }

    public String getMdp(){
        return this.tMdp.getText();
    }


    public void popupErreur() {
        Alert a = new Alert(Alert.AlertType.ERROR,"Problème lors de la connection veuillez réesayer");
        a.showAndWait();
    }


    
    
}
