import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
            MenuItem affichage1 = new MenuItem("camembert");
            MenuItem affichage2 = new MenuItem("graphe");
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
                center.getChildren().add(this.ana.getPie());
            }
            else if(q.getType().equals("n")){
                center.getChildren().add(this.ana.getPie());
            } 
            else if(q.getType().equals("l")){
                center.getChildren().add(this.ana.getPie());
            }
            else if(q.getType().equals("c")){
                center.getChildren().add(this.ana.getHisto());
            }
            else{
                center.getChildren().add(this.ana.getPie());
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
        //FONCTION FAITE PAR MATHYS MAIS MODIF DE L IHM PLUS TARD
        // public void rightPC(){
        //     VBox right = new VBox();
        //     Text reponses = new Text("Réponses");
        //     Button rep = new Button("",reponses);
        //     rep.setDisable(true);
        //     rep.setPrefSize(140,40);
        //     rep.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
        //     HBox rep1 = new HBox();
        //     HBox rep2 = new HBox();
        //     HBox rep3 = new HBox();
        //     Text reponses1 = new Text("-Réponses 1");
        //     reponses1.setFont(Font.font("verdana", null, FontPosture.REGULAR, 10));
        //     Text numRep1 = new Text("10");
        //     Button bRep1 = new Button("",reponses1);
        //     bRep1.setDisable(true);
        //     bRep1.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
        //     Text reponses2 = new Text("-Réponses 2");
        //     reponses2.setFont(Font.font("verdana", null, FontPosture.REGULAR, 10));
        //     Text numRep2 = new Text("10");
        //     Button bRep2 = new Button("",reponses2);
        //     bRep2.setDisable(true);
        //     bRep2.setStyle("-fx-font: 22 arial; -fx-base: #c2bbf0;-fx-background-radius: 10px;");
        //     Text reponses3 = new Text("-Réponses 3");
        //     reponses3.setFont(Font.font("verdana", null, FontPosture.REGULAR, 10));
        //     Text numRep3 = new Text("10");
        //     Button bRep3 = new Button("",reponses3);
        //     bRep3.setDisable(true);
        //     Label vide = new Label("");
        //     bRep3.setStyle("-fx-font: 22 arial; -fx-base: #f1e3f3;-fx-background-radius: 10px;");
        //     rep1.getChildren().addAll(bRep1,numRep1,vide);
        //     rep2.getChildren().addAll(bRep2,numRep2,vide);
        //     rep3.getChildren().addAll(bRep3,numRep3,vide);
        //     rep1.setSpacing(50);
        //     rep2.setSpacing(50);
        //     rep3.setSpacing(50);
        //     rep1.setAlignment(Pos.CENTER_LEFT);
        //     rep2.setAlignment(Pos.CENTER_LEFT);
        //     rep3.setAlignment(Pos.CENTER_LEFT);
        //     right.getChildren().addAll(rep,rep1,rep2,rep3);
        //     right.setSpacing(30);
        //     right.setAlignment(Pos.CENTER);
            //this.setRight(right);
        //}
        ///FONCTION  FAITE PAR MATHYS ANNULEE CAR CHANGEMENT DE MODELE
        // public void rightDB(){
        //     VBox right = new VBox();
        //     HBox auDessus = new HBox();
        //     auDessus.setPadding(new Insets(20,20,20,20));
        //     BorderPane cate = new BorderPane();
        //     cate.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        //     Label categories = new Label("Catégories");
        //     cate.setTop(categories);
        //     VBox listeCate = new VBox();
        //     HBox cate1 = new HBox();
        //     Label coulCate1 = new Label("rouge");
        //     ComboBox<String> comboBox1 = new ComboBox<String>();
        //     comboBox1.getItems().add("Harry");
        //     comboBox1.getItems().add("COUCOU");
        //     cate1.getChildren().addAll(coulCate1,comboBox1);
        //     HBox cate2 = new HBox();
        //     ComboBox<String> comboBox2 = new ComboBox<String>();
        //     comboBox2.getItems().add("Harry");
        //     comboBox2.getItems().add("COUCOU");
        //     Label coulCate2 = new Label("bleu");
        //     cate2.getChildren().addAll(coulCate2,comboBox2);
        //     HBox cate3 = new HBox();
        //     ComboBox<String> comboBox3 = new ComboBox<String>();
        //     comboBox3.getItems().add("Harry");
        //     comboBox3.getItems().add("COUCOU");
        //     Label coulCate3 = new Label("rose");
        //     cate3.getChildren().addAll(coulCate3,comboBox3);
        //     HBox cate4 = new HBox();
        //     ComboBox<String> comboBox4 = new ComboBox<String>();
        //     comboBox4.getItems().add("Harry");
        //     comboBox4.getItems().add("COUCOU");
        //     Label coulCate4 = new Label("jaune");
        //     HBox cate5 = new HBox();
        //     cate4.getChildren().addAll(coulCate4,comboBox4);
        //     ComboBox<String> comboBox5 = new ComboBox<String>();
        //     comboBox5.getItems().add("Harry");
        //     comboBox5.getItems().add("COUCOU");
        //     Label coulCate5 = new Label("violet");
        //     cate5.getChildren().addAll(coulCate5,comboBox5);
        //     HBox cate6 = new HBox();
        //     ComboBox<String> comboBox6 = new ComboBox<String>();
        //     comboBox6.getItems().add("Harry");
        //     comboBox6.getItems().add("COUCOU");
        //     Label coulCate6 = new Label("marron");
        //     cate6.getChildren().addAll(coulCate6,comboBox6);
        //     HBox cate7 = new HBox();
        //     ComboBox<String> comboBox7 = new ComboBox<String>();
        //     comboBox7.getItems().add("Harry");
        //     comboBox7.getItems().add("COUCOU");
        //     Label coulCate7 = new Label("vert");
        //     cate7.getChildren().addAll(coulCate7,comboBox7);
        //     listeCate.getChildren().addAll(cate1,cate2,cate3,cate4,cate5,cate6,cate7);
        //     cate.setCenter(listeCate);
        //     Text reponses = new Text("Voir réponses");
        //     Button rep = new Button("",reponses);
        //     auDessus.getChildren().add(rep);
        //     rep.setPrefSize(140,40);
        //     rep.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
        //     //Text ajCate = new Text("Ajouter catégories");
        //     //Button ajouterCate = new Button("",ajCate);
        //     HBox bAjouterCate = new HBox();
        //     bAjouterCate.getChildren().addAll(this.bPass);
        //     bAjouterCate.setPadding(new Insets(20,20,20,20));
        //     cate.setBottom(this.bPass);
        //     cate1.setSpacing(10);
        //     cate2.setSpacing(10);
        //     cate3.setSpacing(10);
        //     cate4.setSpacing(10);
        //     cate5.setSpacing(10);
        //     cate6.setSpacing(10);
        //     cate7.setSpacing(10);
        //     cate1.setPadding(new Insets(10,10,20,20));
        //     cate2.setPadding(new Insets(10,10,20,20));
        //     cate3.setPadding(new Insets(10,10,20,20));
        //     cate4.setPadding(new Insets(10,10,20,20));
        //     cate5.setPadding(new Insets(10,10,20,20));
        //     cate6.setPadding(new Insets(10,10,20,20));
        //     cate7.setPadding(new Insets(10,10,20,20));
        //     cate.setPadding(new Insets(20,20,20,20));
        //     this.bPass.setPrefSize(140,40);
        //     this.bPass.setStyle("-fx-font: 22 arial; -fx-base: #5ce1e6;-fx-background-radius: 10px;");
        //     auDessus.setAlignment(Pos.CENTER);
        //     right.getChildren().addAll(auDessus,cate);
        //     this.setRight(right);
        //}
            
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
       
    }
