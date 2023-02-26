import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class FenetreSondeur extends BorderPane {
    private TextField tfLib ;
    private Button bUts ;
    private Button bAnnuler;
    private Button bQS;
    private Button bQP;
    private Button bDeco;
    private ProgressBar pg;
    private ComboBox<String> cbRLib;
    private VBox questioncenter;
    private SondeurJDBC mod;
    private ComboBox<String> cbRep;

    public FenetreSondeur(Button prec,Button suiv,Button utS,Button an,Button d,SondeurJDBC s){
        super();
        this.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.tfLib = new TextField();
        this.bUts = utS;
        this.bAnnuler = an;
        this.mod=s;
        this.bQS =suiv;
        this.bQP = prec;
        this.bDeco = d;
        this.pg = new ProgressBar();
        this.cbRLib = new ComboBox<>();
        this.top();
        this.bottom();
        this.right();
        this.left();
        
        
    }
    /**menu a gauche en focntion du modèle */
    public void left(){
        ImageView user = new ImageView();
        HBox top = new HBox();
        VBox center = new VBox();
        HBox right = new HBox();
        right.setPadding(new Insets(50,50,50,200));
        center.setAlignment(Pos.CENTER);
        center.setSpacing(20);
        center.setPadding(new Insets(0,20,20,20));
        top.setPadding(new Insets(0,0,150,0));
        BorderPane bpLeft = new BorderPane();
        Label sondeur = new Label(this.mod.getSondage().getTitreS());
        sondeur.setFont(Font.font("Livvic", 15));
        sondeur.setTextFill(Color.web("#c2bbf0"));
        Label tel = new Label(this.mod.getSonde().getTel());
        Label nom = new Label(this.mod.getSonde().getNom()+" "+this.mod.getSonde().getPrenom());
        Label metier = new Label(this.mod.getSonde().getCaracteristique().getSexe());
        Label jsp = new Label(this.mod.getSonde().getCaracteristique().getCategorie().getIntCat());
        Label nbEnfant = new Label(this.mod.getSonde().getCaracteristique().gettranche().getIntTrancheD()+"-"+this.mod.getSonde().getCaracteristique().gettranche().getIntTrancheF());
        this.bUts.setStyle("-fx-background-color: #5ce1e6");
        this.bUts.setFont(Font.font("Livvic", 23));
        center.getChildren().addAll(sondeur,user,tel,nom,metier,jsp,nbEnfant,this.bUts);
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        center.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bpLeft.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        bpLeft.setTop(top);
        bpLeft.setCenter(center);
        bpLeft.setRight(right);
        this.setLeft(bpLeft);
    }
    /**
     * menu droite 
     */
    public void right(){
        VBox vboxRight = new VBox();
        ProgressBar pg = new ProgressBar();
        pg.getTransforms().addAll(new Translate(0,100),new Rotate(-90,0,0));
        pg.setMinSize(200, 20);
        double numq=this.mod.getQuestion().getNumQ();
        double nbQ=this.mod.getSondage().getQuestions().size();
        pg.setProgress(numq/nbQ);
        Label labelTop = new Label("20");
        Label labelBottom = new Label("0");
        vboxRight.getChildren().add(pg);
        vboxRight.setAlignment(Pos.CENTER_RIGHT);
        this.setRight(vboxRight);
        
    }
    /**menu haut */
        public void top(){
        BorderPane bp = new BorderPane();
            bp.setStyle("-fx-font: 22 arial; -fx-base:  #ebebeb");
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
            this.bDeco.setStyle("-fx-font: 22 arial; -fx-base: #ebebeb;-fx-background-radius: 10px;");
            Button aide = new Button("Aide");
            aide.setStyle("-fx-background-radius: 10px;");
            Menu menuS = new Menu("Panel");
            menuS.setStyle("-fx-font: 22 arial; -fx-base: #ebebeb;-fx-background-radius: 10px;");
            HBox centre = new HBox();
            MenuItem sondage1 = new MenuItem("Caractéristiques");
            MenuItem sondage2 = new MenuItem("Tranche");
            MenuItem sondage3 = new MenuItem("Catégorie");
            MenuBar menuBarS = new MenuBar();
            menuS.getItems().addAll(sondage1,sondage2,sondage3);
            menuBarS.getMenus().addAll(menuS);
            centre.getChildren().addAll(menuBarS, aide);
            logoG.getChildren().addAll(logo);
            logoG.setSpacing(20);
            boutonG.getChildren().addAll(this.bDeco);
            logoG.setAlignment(Pos.CENTER);
            boutonG.setAlignment(Pos.CENTER);
            bp.setLeft(logoG);
            centre.setSpacing(40);
            centre.setAlignment(Pos.CENTER);
            bp.setCenter(centre);
            bp.setRight(boutonG);
            this.setTop(bp);
        }
    
        /**
         * met le bas en fonction du modèle
         */
    public void bottom(){
        BorderPane bpBottom = new BorderPane();
        HBox center = new HBox();
        HBox left = new HBox();
        HBox right = new HBox();
        HBox top = new HBox();
        top.setBackground(new Background(new BackgroundFill(Color.valueOf("#ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("#ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        this.bAnnuler.setStyle("-fx-background-color: #5ce1e6");
        this.bAnnuler.setFont(Font.font("Livvic", 20));
        ImageView suiv = new ImageView("suiv.png");
        suiv.setFitHeight(50);
        suiv.setPreserveRatio(true);
        ImageView prec = new ImageView("prec.png");
        prec.setFitHeight(50);
        prec.setPreserveRatio(true);
        this.bQP.setGraphic(prec);
        this.bQS.setGraphic(suiv);
        this.bQP.setPrefSize(50,50);
        this.bQS.setPrefSize(50,50);
        Label numeroQuestion = new Label("Question n°"+this.mod.getQuestion().getNumQ());
        numeroQuestion.setFont(Font.font("Livvic", 23));
       
        top.setPadding(new Insets(20,20,50,20));
        right.setPadding(new Insets(50,50,50,50));
        center.setPadding(new Insets(0,0,50,0));
        center.setPrefHeight(200);
        center.setMaxWidth(600);
        left.setPadding(new Insets(20,0,50,20));
        left.getChildren().addAll(this.bAnnuler);
        center.setSpacing(15);
        center.setAlignment(Pos.BOTTOM_CENTER);
        center.getChildren().addAll(this.bQP,numeroQuestion,this.bQS);
        center.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bpBottom.setTop(top);
        bpBottom.setLeft(left);
        bpBottom.setCenter(center);
        bpBottom.setRight(right);
        this.setBottom(bpBottom);
    }

    public void modeChoixS(Question q) throws SQLException{
        
        BorderPane bpane = new BorderPane();
        BorderPane center = new BorderPane();
        VBox centerBox = new VBox();
        centerBox.setSpacing(150);
        centerBox.setPadding(new Insets(20,20,20,20));
        VBox questionTop = new VBox();
        questionTop.setSpacing(30);
        questionTop.setPadding(new Insets(20,0,0,0));
        questionTop.setAlignment(Pos.CENTER);
        this.questioncenter = new VBox();
        this.questioncenter.setAlignment(Pos.CENTER);
        ToggleGroup choix = new ToggleGroup();
        HBox top = new HBox();
        HBox right = new HBox();
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(20,20,20,20));
        bottom.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setPadding(new Insets(0,200,200,0));
        top.setPadding(new Insets(0,0,200,0));
        top.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        // TOP
        Label numeroQuestion = new Label("Question "+q.getNumQ());
        questionTop.setAlignment(Pos.BASELINE_CENTER); 
        numeroQuestion.setFont(Font.font ("Livvic", FontWeight.BOLD, 18));
        
        Text intuleQuestion = new Text(q.getTexte());
        intuleQuestion.setWrappingWidth(200);
        this.questioncenter.setAlignment(Pos.BASELINE_CENTER);
        //questionTop.setPadding(new Insets(10,20,20,20));

        questionTop.getChildren().addAll(numeroQuestion,intuleQuestion);

        questionTop.setSpacing(5);


        // CENTER
        //this.questioncenter.setPadding(new Insets(15));
        List<String> valposs=this.mod.getValsPossCM();
        for(String val :valposs){
            RadioButton choixq = new RadioButton(val);
            choixq.setToggleGroup(choix);
            choixq.setAlignment(Pos.CENTER);
            this.questioncenter.getChildren().add(choixq);
        }
        this.questioncenter.setSpacing(5);

        centerBox.getChildren().addAll(questionTop,this.questioncenter);
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        questionTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.questioncenter.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        center.setCenter(centerBox);
        bpane.setCenter(center);
        bpane.setBottom(bottom);
        bpane.setTop(top);
        bpane.setRight(right);
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(bpane);

    }

    public void modeChoixM(Question q) throws SQLException{
        BorderPane bpane = new BorderPane();
        BorderPane center = new BorderPane();
        VBox centerBox = new VBox();
        centerBox.setSpacing(150);
        centerBox.setPadding(new Insets(20,20,20,20));
        VBox questionTop = new VBox();
        questionTop.setSpacing(30);
        questionTop.setPadding(new Insets(20,0,0,0));
        questionTop.setAlignment(Pos.CENTER);
        this.questioncenter = new VBox();
        this.questioncenter.setAlignment(Pos.CENTER);
        HBox top = new HBox();
        HBox right = new HBox();
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(20,20,20,20));
        bottom.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setPadding(new Insets(0,200,200,0));
        top.setPadding(new Insets(0,0,200,0));
        top.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        // TOP
        Label numeroQuestion = new Label("Question "+q.getNumQ());
        questionTop.setAlignment(Pos.BASELINE_CENTER); 
        numeroQuestion.setFont(Font.font ("Livvic", FontWeight.BOLD, 18));
        
        Text intuleQuestion = new Text(q.getTexte());
        intuleQuestion.setWrappingWidth(200);
        this.questioncenter.setAlignment(Pos.BASELINE_CENTER);
        //questionTop.setPadding(new Insets(10,20,20,20));

        questionTop.getChildren().addAll(numeroQuestion,intuleQuestion);

        questionTop.setSpacing(5);


          // CENTER
        this.questioncenter.setPadding(new Insets(15));
       
        List<String> valposs=this.mod.getValsPossCM();
        for(String val :valposs){
            RadioButton choixq = new RadioButton(val);
            choixq.setAlignment(Pos.CENTER);
            this.questioncenter.getChildren().add(choixq);
        }
        this.questioncenter.setSpacing(5);

        centerBox.getChildren().addAll(questionTop,this.questioncenter);
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        questionTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.questioncenter.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        center.setCenter(centerBox);
        bpane.setCenter(center);
        bpane.setBottom(bottom);
        bpane.setTop(top);
        bpane.setRight(right);
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(bpane);
    }
    public void modeChoixN(Question q) throws SQLException{
        
        BorderPane bpane = new BorderPane();
        BorderPane center = new BorderPane();
        VBox centerBox = new VBox();
        centerBox.setSpacing(150);
        centerBox.setPadding(new Insets(20,20,20,20));
        VBox questionTop = new VBox();
        questionTop.setSpacing(30);
        questionTop.setPadding(new Insets(20,0,0,0));
        questionTop.setAlignment(Pos.CENTER);
        this.questioncenter = new VBox();
        this.questioncenter.setAlignment(Pos.CENTER);
        HBox top = new HBox();
        HBox right = new HBox();
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(20,20,20,20));
        bottom.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setPadding(new Insets(0,200,200,0));
        top.setPadding(new Insets(0,0,200,0));
        top.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        // TOP
        Label numeroQuestion = new Label("Question "+q.getNumQ());
        questionTop.setAlignment(Pos.BASELINE_CENTER); 
        numeroQuestion.setFont(Font.font ("Livvic", FontWeight.BOLD, 18));
        
        Text intuleQuestion = new Text(q.getTexte());
        intuleQuestion.setWrappingWidth(200);
        this.questioncenter.setAlignment(Pos.BASELINE_CENTER);
        //questionTop.setPadding(new Insets(10,20,20,20));

        questionTop.getChildren().addAll(numeroQuestion,intuleQuestion);

        questionTop.setSpacing(5);


        // CENTER
        int max = this.mod.getMaxValNC();
        this.cbRep = new ComboBox<>();
        for(int i=0;i<max+1;i++){
            this.cbRep.getItems().add(i+"");
        }
        this.questioncenter.setSpacing(5);
        this.questioncenter.getChildren().add(this.cbRep);
        centerBox.getChildren().addAll(questionTop,this.questioncenter);
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        questionTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.questioncenter.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        center.setCenter(centerBox);
        bpane.setCenter(center);
        bpane.setBottom(bottom);
        bpane.setTop(top);
        bpane.setRight(right);
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(bpane);

    }
    public void modeChoixL(Question q) throws SQLException{
        
        BorderPane bpane = new BorderPane();
        BorderPane center = new BorderPane();
        VBox centerBox = new VBox();
        centerBox.setSpacing(150);
        centerBox.setPadding(new Insets(20,20,20,20));
        VBox questionTop = new VBox();
        questionTop.setSpacing(30);
        questionTop.setPadding(new Insets(20,0,0,0));
        questionTop.setAlignment(Pos.CENTER);
        this.questioncenter = new VBox();
        this.questioncenter.setAlignment(Pos.CENTER);
        ToggleGroup choix = new ToggleGroup();
        HBox top = new HBox();
        HBox right = new HBox();
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(20,20,20,20));
        bottom.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setPadding(new Insets(0,200,200,0));
        top.setPadding(new Insets(0,0,200,0));
        top.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        // TOP
        Label numeroQuestion = new Label("Question "+q.getNumQ());
        questionTop.setAlignment(Pos.BASELINE_CENTER); 
        numeroQuestion.setFont(Font.font ("Livvic", FontWeight.BOLD, 18));
        
        Text intuleQuestion = new Text(q.getTexte());
        intuleQuestion.setWrappingWidth(200);
        this.questioncenter.setAlignment(Pos.BASELINE_CENTER);
        //questionTop.setPadding(new Insets(10,20,20,20));

        questionTop.getChildren().addAll(numeroQuestion,intuleQuestion);

        questionTop.setSpacing(5);


        // CENTER
        this.tfLib=new TextField();
        List<String> valposs=this.mod.getValsPossCM();
        this.cbRLib= new ComboBox<>();
        for(String val :valposs){
            this.cbRLib.getItems().add(val);
        }
        this.tfLib.setAlignment(Pos.CENTER);
        this.questioncenter.setSpacing(5);
        this.questioncenter.getChildren().addAll(this.tfLib,this.cbRLib);
        centerBox.getChildren().addAll(questionTop,this.questioncenter);
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        questionTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.questioncenter.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        center.setCenter(centerBox);
        bpane.setCenter(center);
        bpane.setBottom(bottom);
        bpane.setTop(top);
        bpane.setRight(right);
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(bpane);

    }
    public void modeChoixC(Question q) throws SQLException{
        
        BorderPane bpane = new BorderPane();
        BorderPane center = new BorderPane();
        VBox centerBox = new VBox();
        centerBox.setSpacing(150);
        centerBox.setPadding(new Insets(20,20,20,20));
        VBox questionTop = new VBox();
        questionTop.setSpacing(30);
        questionTop.setPadding(new Insets(20,0,0,0));
        questionTop.setAlignment(Pos.CENTER);
        this.questioncenter = new VBox();
        this.questioncenter.setAlignment(Pos.CENTER);
        ToggleGroup choix = new ToggleGroup();
        HBox top = new HBox();
        HBox right = new HBox();
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(20,20,20,20));
        bottom.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setPadding(new Insets(0,200,200,0));
        top.setPadding(new Insets(0,0,200,0));
        top.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        // TOP
        Label numeroQuestion = new Label("Question "+q.getNumQ());
        questionTop.setAlignment(Pos.BASELINE_CENTER); 
        numeroQuestion.setFont(Font.font ("Livvic", FontWeight.BOLD, 18));
        
        Text intuleQuestion = new Text(q.getTexte());
        intuleQuestion.setWrappingWidth(200);
        this.questioncenter.setAlignment(Pos.BASELINE_CENTER);
        //questionTop.setPadding(new Insets(10,20,20,20));

        questionTop.getChildren().addAll(numeroQuestion,intuleQuestion);

        questionTop.setSpacing(5);


        // CENTER
        
        int max = this.mod.getMaxValNC();
        for(int i=0;i<max;i++){
            Label reponse = new Label("   Classement "+(i+1));
            ComboBox cb =new ComboBox<>();
            cb.getItems().addAll(this.mod.getValsPossCM());
            this.questioncenter.getChildren().addAll(reponse,cb);
        }
        this.questioncenter.setSpacing(5);
        centerBox.getChildren().addAll(questionTop,this.questioncenter);
        right.setBackground(new Background(new BackgroundFill(Color.valueOf("ebebeb"), CornerRadii.EMPTY, Insets.EMPTY)));
        questionTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.questioncenter.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        center.setCenter(centerBox);
        bpane.setCenter(center);
        bpane.setBottom(bottom);
        bpane.setTop(top);
        bpane.setRight(right);
        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(bpane);

    }
    public ComboBox<String> getCBox(){
        return this.cbRLib;
    }

    public String getSelectionnee() {
        return this.cbRep.getValue();
    }

    public String getTexte() {
        if(! this.tfLib.getText().equals("")){
            return this.tfLib.getText();}
        return this.cbRLib.getValue();
    }

    public List<String> getCochee() {
        List<String> resultat = new ArrayList<>();
        //pour les radio button dans 
        for(Node choix : this.questioncenter.getChildren()){
            RadioButton cast = (RadioButton)choix;
            if(cast.isSelected()){
                resultat.add(cast.getText());
            }
        }
        return resultat;
    }

    public List<String> getClassement() {
        List<String> resultat = new ArrayList<>();
        for(Node choix : this.questioncenter.getChildren()){
            if(! (choix instanceof Label)){
            ComboBox<String> cast = (ComboBox<String>)choix;
            resultat.add(cast.getValue());}
            
        }

        return resultat;
    }

    
}




    

