
import java.sql.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
 import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class studentlogin {
	static Connection conn;
	static PreparedStatement ps;
	static ResultSet rs;
	static String url = "jdbc:mysql://localhost:3306/ensao";
	static String user = "root";
	static String pw = "hamza";

	public static Scene studentscene() {

		Text loggin_result = new Text();
		Text text1 = new Text("Email");
		Text text2 = new Text("CNE");
		TextField inputemail = new TextField();
		PasswordField inputcne = new PasswordField();
		double inputwidth = 350; // desired width
		double inputheight = 40; // desired height
		inputemail.setPrefSize(inputwidth, inputheight);
		inputcne.setPrefSize(inputwidth, inputheight);
		String inputstyle = "-fx-font-size:15;-fx-background-radius: 2; -fx-focus-color: #fb8500;-fx-faint-focus-color: transparent;";
		inputemail.setStyle(inputstyle);
		inputcne.setStyle(inputstyle);

		Button button1 = new Button("Log In");
		StackPane btn = new StackPane(button1);
		button1.setPrefSize(130, 40);
		button1.setStyle(
				" -fx-background-color: #fb8500; -fx-text-fill:#001d3d;-fx-font-size: 14px;-fx-font-family: Arial;-fx-padding: 5 10;-fx-background-radius: 5;-fx-border-radius: 5; -fx-font-weight:bold; -fx-border-color:#14213d");
		GridPane gridPane = new GridPane();

		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(20);
		gridPane.setHgap(8);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(text1, 0, 1);
		gridPane.add(inputemail, 1, 1);
		gridPane.add(text2, 0, 2);
		gridPane.add(inputcne, 1, 2);
		gridPane.add(btn, 1, 3);
		gridPane.add(new StackPane(loggin_result), 1, 4, 2, 1);
		gridPane.setAlignment(Pos.CENTER);
//-----
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.GRAY);
		dropShadow.setRadius(10);
		dropShadow.setOffsetX(1);
		dropShadow.setOffsetY(5);
//------
		
		String labelstyle = "-fx-text-fill:#14213d;-fx-font-family: 'Roboto'; -fx-font-weight:bold; -fx-font-size: 16px;";
		text1.setStyle(labelstyle);
		text2.setStyle(labelstyle);
		loggin_result.setStyle(labelstyle);
		String imagePath = "Assets/applogo.png";

		ImageView applogo = new ImageView(imagePath);
		applogo.setFitHeight(100);
		applogo.setFitWidth(100);
		applogo.setScaleX(3.5);
		applogo.setScaleY(3.5);
		applogo.setTranslateY(60);
		applogo.setTranslateX(260);

		Button btn1 = new Button("Home");
String btnstyle = new String(
		"-fx-background-color: #14213d; -fx-text-fill:white ;-fx-font-size: 12pt;-fx-font-family: Arial;-fx-padding: 10 20;-fx-border-radius: 5;-fx-background-radius: 5;-fx-border-width: 2px;");

		btn1.setStyle(btnstyle);
		// btn1.setEffect(dropShadow);
		btn1.setPrefWidth(100);
		btn1.setPrefHeight(25);
		btn1.setTranslateX(6);
      
	  //switch scene to go back
  btn1.setOnAction(e -> {
            main.switchScene(home.homescene());
	});
	
	BorderPane root = new BorderPane();
	HBox header = new HBox(btn1, applogo);
	
	header.setAlignment(Pos.CENTER_LEFT);
		root.setTop(header);
		root.setCenter(gridPane);

		button1.setOnAction(event -> {
			//check if all inputs are full;
			if (inputemail.getText().equals("") || inputcne.getText().equals("")) {
				alert.display(false, "Please fill out all the required fields.", "red", "Okay");
			} else {
				try {
					String sql = "SELECT * FROM students where email='" + inputemail.getText() + "' and cne='"
							+ inputcne.getText() + "'";
					conn = DriverManager.getConnection(url, user, pw);
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();

					if (rs.next()) {
						loggin_result.setText("login success");
						loggin_result.setFill(Color.GREEN);
						int id = rs.getInt("id");
						print.display(id);
					} else {
						alert.display(false, "EMAIL OR Password not found.", "red", "Okay");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});



		Scene scene = new Scene(root, 800, 700);
		scene.getStylesheets().add("css/inputs.css");
		scene.getStylesheets().add("css/buttonshover.css");
		
      return scene;
	}

}