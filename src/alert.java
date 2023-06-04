import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Random;

public class alert {
   public static void display(Boolean alert, String message, String color, String buttonmessage) {

      Stage window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      Image icon;
      Scene s = home.homescene();
      if (alert) {
         icon = new Image("Assets/validicon.png");
      } else {
         icon = new Image("Assets/alerticon.png");

      }
      window.getIcons().add(icon);
      VBox vbox = new VBox(10);
      // grid.setStyle("-fx-border: black;");
      vbox.setPadding(new Insets(10, 10, 10, 10));
      StackPane stackPane = new StackPane();
      stackPane.setAlignment(Pos.CENTER);
      Label alertmsg = new Label(message);
      alertmsg.setAlignment(Pos.CENTER);
      alertmsg.setStyle(
            "-fx-font-family: 'Roboto';-fx-font-weight: bold;-fx-font-size: 15px;-fx-text-fill:" + color + ";");

      Button btn = new Button(buttonmessage);
      btn.setPrefWidth(150);
      btn.setOnAction(e -> {
         window.close();
      });
      btn.setStyle(
            "-fx-background-color: #023047; -fx-text-fill: white;-fx-font-size: 14pt;-fx-font-family: Arial;-fx-padding: 5 10;-fx-background-radius: 5;-fx-border-radius: 5; -fx-border-width: 2px;");
      stackPane.getChildren().add(btn);

      vbox.getChildren().addAll(alertmsg, stackPane);
      vbox.setAlignment(Pos.CENTER);
      stackPane.setAlignment(Pos.CENTER);
      btn.setAlignment(Pos.CENTER);
      Scene scene = new Scene(vbox, 400, 200);
      scene.getStylesheets().add("css/buttonshover.css");
      window.centerOnScreen();
      window.setScene(scene);
      window.showAndWait();
   }
}