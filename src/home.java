
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
public class home {

  
    public static Scene homescene() {

        BorderPane borderpane = new BorderPane();

        // Load the image
        double Width = 800;
        double Height = 600;
        Image image = new Image("Assets/ensaopic3.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(Width, Height, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(
                image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);

        Background background = new Background(backgroundImage);

        borderpane.setBackground(background);

        // borderpane.setStyle("-fx-background-color: linear-gradient(to bottom, #870000, #190a05);");
        ImageView ensaologo = new ImageView(new Image("Assets/ensaologo.png"));
        ImageView umplogo = new ImageView(new Image("Assets/umplogo.png"));
        ImageView applogo = new ImageView(new Image("Assets/applogo.png"));

        double width = 130; // desired width
        double height = 130; // desired height
        ensaologo.setPreserveRatio(true);
        umplogo.setPreserveRatio(true);
        applogo.setPreserveRatio(true);

        ensaologo.setFitWidth(width);
        ensaologo.setFitHeight(height);
        umplogo.setFitWidth(width);
        umplogo.setFitHeight(height);
        applogo.setFitWidth(220);
        applogo.setFitHeight(200);
        applogo.setScaleX(1.5);
        applogo.setScaleY(1.5);
        applogo.setTranslateY(-10);

        //   -------------------------
     

        HBox header = new HBox(umplogo, applogo, ensaologo);
        header.setAlignment(Pos.TOP_CENTER);
        header.setSpacing(150);

        header.setTranslateY(-20);

        borderpane.setTop(header);

        // Text message = new Text("welcome to studomap!");
        // message.setFont(Font.font("Arial", 40));
        // message.setFill(Color.BLACK);
        // borderpane.setCenter(message);
      DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(5);

        ImageView titlelogo = new ImageView(new Image("Assets/studotitle1.png"));
        titlelogo.setFitWidth(200);
        titlelogo.setFitHeight(50);
        titlelogo.setScaleX(7);
        titlelogo.setScaleY(7);
        titlelogo.setPreserveRatio(true);


        HBox footer = new HBox(30);
        footer.setPadding(new Insets(10, 10, 30, 10));
        Button btn1 = new Button("Loging as Admin");
        Button btn2 = new Button("Loging as Student");
        
         btn2.setOnAction(e -> {
            main.switchScene(studentlogin.studentscene());
        });
        btn1.setPrefWidth(200);
        btn2.setPrefWidth(200);
        //  Button btn3 = new Button("");
        // Button btn4 = new Button("HOME");

        String btnstyle = new String(
                "-fx-background-color: #023047; -fx-text-fill: white;-fx-font-size: 14;-fx-font-family:-fx-font-weight:bold; Arial;-fx-padding: 10 20;-fx-background-radius: 5;-fx-border-width: 2px;");
        btn1.setStyle(btnstyle);
        btn2.setStyle(btnstyle);
        btn1.setEffect(dropShadow);
        btn2.setEffect(dropShadow);
        footer.getChildren().addAll(btn1, btn2);
        footer.setAlignment(Pos.CENTER);
        VBox Footer = new VBox(titlelogo, footer);
        Footer.setAlignment(Pos.CENTER);

        borderpane.setBottom(Footer);
        Scene scene = new Scene(borderpane, 800, 700);
		scene.getStylesheets().add("css/buttonshover.css");
        btn1.setOnAction(e -> {
            main.switchScene(adminlogin.admincScene());
        });
        return scene;
    }
}