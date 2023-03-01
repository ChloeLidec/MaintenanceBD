import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



public class FenetreAnalyste extends BorderPane {

    private Button bsuiv;
    private Button bprec;
    private Button decoB;
    private Button acceuil;
    private AnalysteJDBC ana;
    private AppliAllo45 app;
        public FenetreAnalyste(Button prec,Button suiv,Button ac,Button decoB,AnalysteJDBC ana,AppliAllo45 app){
            super();
            this.bsuiv=suiv;
            this.bprec=prec;
            this.ana=ana;
            this.decoB = decoB;
            this.acceuil=ac;
            this.app=app;
            this.setStyle(" -fx-base: #ebebeb;");
            this.setTop(this.top());
            this.setLeft(this.left());
            this.bottom();
            this.setCenter(new ImageView("logo.png"));
        }
        //haut = menu et logo et deconnexion
        public BorderPane top(){
            BorderPane top = new BorderPane();
            top.setStyle("-fx-font: 22 arial; -fx-base: #ffffff;");
            Image allo45 = new Image("logo.png");
            ImageView logo = new ImageView("logo.png");
            logo.setImage(allo45);
            logo.setFitWidth(80);
            logo.setFitHeight(80);
            logo.setSmooth(true);
            logo.setCache(true);
            HBox boutonG = new HBox();
            HBox logoG = new HBox();
            decoB.setStyle("-fx-font: 22 arial; -fx-base: #ffffff;-fx-background-radius: 10px;");
            
            this.acceuil.setStyle("IDLE_BUTTON_STYLE; -fx-background-radius: 10px;");
            Button aide = new Button("Aide");
            aide.setStyle("-fx-background-radius: 10px;");
            Menu menuS = new Menu("Sondage");
            HBox centre = new HBox();
            MenuItem sondage1 = new MenuItem("Nom");
            MenuItem sondage2 = new MenuItem("Panel");
            sondage1.setOnAction(new ControleurMenuSondageB(this));
            sondage2.setOnAction(new ControleurMenuSondageA(this));
            Menu menuA = new Menu("Affichage");
            MenuItem affichage1 = new MenuItem("Camembert");
            MenuItem affichage2 = new MenuItem("Histogramme");
            affichage1.setOnAction(new ControlleurChangementGraphe(this,"Pie"));
            affichage2.setOnAction(new ControlleurChangementGraphe(this,"Bar"));
            MenuBar menuBarS = new MenuBar();
            MenuBar menuBarA = new MenuBar();
            menuA.getItems().addAll(affichage1, affichage2);
            menuS.getItems().addAll(sondage1,sondage2);
            menuBarS.getMenus().addAll(menuS);
            menuBarA.getMenus().addAll(menuA);
            centre.getChildren().addAll(this.acceuil,menuBarS, menuBarA, aide);
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
         * met le centre en fonction d'une question en entrée
         * @param q
         * @throws SQLException
         */
        public void center(Question q) throws SQLException{
            VBox center = new VBox();
            center.setAlignment(Pos.CENTER);
            Text ques =new Text(this.ana.gQuestion().getTexte());
            ques.setWrappingWidth(200);
            center.getChildren().addAll(new Text("Question "+this.ana.gQuestion().getNumQ()),ques);
            if(q.getType().equals("u")){          
                center.getChildren().add(this.ana.getHisto());
            }
            else if(q.getType().equals("n")){
                center.getChildren().add(this.ana.getHisto());
            } 
            else if(q.getType().equals("l")){
                center.getChildren().add(this.ana.getHisto());
            }
            else if(q.getType().equals("c")){
                center.getChildren().add(this.ana.getPie());
            }
            else{
                center.getChildren().add(this.ana.getHisto());
            }
     
            this.setCenter(center);
        }
        //gauche menu des questions pas interactifs
        public VBox left(){
            VBox lefty = new VBox();
            VBox left = new VBox();
            HBox allerAQ = new HBox();
            left.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            Text sondage = new Text(this.ana.getSondage().getTitreS());
            sondage.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
            sondage.setFill(Color.valueOf("#f1e3f3"));
            Text allerA = new Text("Aller à la question");
            TextField numAllerA = new TextField();
            numAllerA.setMaxWidth(140);
            Text tAller = new Text("Aller");
            tAller.setFont(Font.font("verdana", null, FontPosture.REGULAR, 20));
            Text tTriParAge = new Text("Tri par âge");
            tTriParAge.setFont(Font.font("verdana", null, FontPosture.REGULAR, 15));
            Text tTriSocio = new Text("Tri par catégorie \n      socio-pro");
            tTriSocio.setFont(Font.font("verdana", null, FontPosture.REGULAR, 13));
            Button aller = new Button("",tAller);
            Button triParAge = new Button("",tTriParAge);
            Button triSocio = new Button("",tTriSocio);
            aller.setPrefSize(140,40);
            triParAge.setPrefSize(140,40);
            triSocio.setPrefSize(140,40);
            allerAQ.getChildren().addAll(numAllerA,aller);
            aller.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
            triParAge.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
            triSocio.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
            left.getChildren().addAll(sondage,allerA,allerAQ,triParAge,triSocio);
            left.setAlignment(Pos.CENTER);
            lefty.setAlignment(Pos.CENTER);
            left.setSpacing(30);
            left.setPrefHeight(450);
            left.setPrefWidth(200);
            lefty.getChildren().addAll(left);


            return lefty;
        }
        
            
        /**
         * met le bas a jour en fonction du modèle(n question)
         */
        public void bottom(){
            BorderPane bottom = new BorderPane();
            HBox bot = new HBox();
            VBox botD = new VBox();
            VBox vide = new VBox();
            vide.setPrefSize(150,150);
            TextArea commentaire = new TextArea();
            botD.getChildren().addAll(commentaire,vide);
            bot.setPrefHeight(200);
            bot.setMaxWidth(600);
            commentaire.setPrefSize(150, 150);
            botD.setPadding(new Insets(20,20,20,20));
            bot.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            ImageView iSuiv = new ImageView("suiv.png");
            iSuiv.setFitHeight(50);
            iSuiv.setPreserveRatio(true);
            this.bsuiv.setGraphic(iSuiv);
            this.bsuiv.setPrefSize(50,50);
            ImageView iPrec = new ImageView("prec.png");
            iPrec.setFitHeight(50);
            iPrec.setPreserveRatio(true);
            this.bprec.setGraphic(iPrec);
            this.bprec.setPrefSize(50,50);
            Text numQuestion = new Text("Question "+ this.ana.gQuestion().getNumQ());
            bot.getChildren().addAll(this.bprec,numQuestion,this.bsuiv,botD);
            bot.setAlignment(Pos.CENTER);
            bot.setSpacing(30);
            bottom.setCenter(bot);
            bottom.setRight(botD);
            bottom.setLeft(vide);
            this.setBottom(bottom);


        }
        /**pop up infos panel */
        public void popupPanel(){
            Alert a = new Alert(Alert.AlertType.INFORMATION,this.ana.getInfosPanel());
            a.showAndWait();
        }
        /**pop up menu nom sondage*/
        public void popupNom(){
            Alert a = new Alert(Alert.AlertType.INFORMATION,this.ana.getSondage().getTitreS());
            a.showAndWait();
        }
        /**reload graphe au milieu*/
        public void changerGraphe(String type) {
            if (type.equals("Bar")) {
                BarChart bar = null;
                try {
                    bar = this.ana.getHisto();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (bar == null) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION,"Représentation impossible");
                    a.showAndWait();
                } else {
                    this.setCenter(bar);
                }
            } else if (type.equals("Pie")) {
                PieChart pie = null;
                try {
                    pie = this.ana.getPie();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (pie == null) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION,"Représentation impossible");
                    a.showAndWait();
                } else {
                    this.setCenter(pie);
                }
            } 
            }
        }
       
   