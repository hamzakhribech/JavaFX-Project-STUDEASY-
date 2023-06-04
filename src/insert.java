
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class insert {

    private static Connection conn;

    public static Scene insertscene() {
        VBox maincontainer = new VBox();
        maincontainer.setStyle("-fx-background-color: white");
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
        applogo.setTranslateY(20);

        HBox header = new HBox(umplogo, applogo, ensaologo);
        header.setTranslateY(-50);
        header.setAlignment(Pos.CENTER);
        header.setSpacing(120);

        GridPane grid = new GridPane();
        grid.setTranslateY(-50);

        grid.setStyle("-fx-border:  black;");
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setVgap(10);
        grid.setHgap(10);
        Label name = new Label("Full name");
        Label cne = new Label("CNE");
        Label birthday = new Label("Date of Birth");
        Label gender = new Label("Gender");
        Label email = new Label("Email Address");
        Label phone = new Label("Phone Number");
        Label city = new Label("City");
        

        TextField inputname = new TextField();
        inputname.setPromptText("Enter full name");
        TextField inputcne = new TextField();
        inputcne.setPromptText("example:F1634532...");
        DatePicker inputdate = new DatePicker();
        ChoiceBox<String> inputgender = new ChoiceBox<>(
                FXCollections.observableArrayList("Male", "Female"));
        TextField inputmail = new TextField();
        inputmail.setPromptText("Example@gmail.com");
        TextField inputphone = new TextField();
        TextField inputcity = new TextField();
        // image inserting
        VBox root = new VBox();
        root.setSpacing(10);
        Label label = new Label("Choose Image:");
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true); // enable text wrapping
        Button chooseButton = new Button("Select image");
        Image icon = new Image("Assets/icondownload.png"); // Set the icon image path
        ImageView imageView = new ImageView(icon);

        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        imageView.setScaleX(2.5);
        imageView.setScaleY(2.5);
        chooseButton.setGraphic(imageView);
        chooseButton.setStyle("-fx-background-color:lightblue;-fx-border-color:transparent;-fx-background-radius:100");

        // imge diplay after choosing
        Image img = new Image("Assets/Unknown_person.jpg");
        Rectangle circle = new Rectangle(80, 80, 80, 80);
        circle.setFill(new ImagePattern(img));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        circle.setStyle("-fx-stroke-width: 2; -fx-arc-width: 20; -fx-arc-height: 20;");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(5);
        circle.setEffect(dropShadow);

        chooseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(main.getstStage());
            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                textArea.setText(imagePath);
                Image img2 = new Image(selectedFile.toURI().toString());
                circle.setFill(new ImagePattern(img2));
            }

        });

        root.getChildren().addAll(label, chooseButton, textArea);
        // ------------------------
        ButtonBase btn = new Button("submit");
        StackPane btncontainer = new StackPane();

        btncontainer.getChildren().add(btn);
        btn.setStyle(
                "-fx-background-color: #fb8500; -fx-text-fill:#001d3d;  -fx-padding: 10;-fx-font-size: 15; -fx-border-radius:5;-fx-background-radius:5;-fx-font-weight:bold; -fx-border-color:#14213d");

        btn.setPrefSize(120, 80);
        grid.add(name, 0, 0);
        grid.add(inputname, 1, 0);
        grid.add(cne, 0, 1);
        grid.add(inputcne, 1, 1);
        grid.add(birthday, 0, 2);
        grid.add(inputdate, 1, 2);
        grid.add(gender, 0, 3);
        grid.add(inputgender, 1, 3);
        grid.add(email, 0, 4);
        grid.add(inputmail, 1, 4);
        grid.add(phone, 0, 5);
        grid.add(inputphone, 1, 5);
        grid.add(city, 0, 6);
        grid.add(inputcity, 1, 6);
        grid.add(label, 0, 7);
        grid.add(chooseButton, 1, 7);
        grid.add(textArea, 1, 8);
        grid.add(circle, 2, 7, 1, 2);
        grid.add(btncontainer, 1, 9);

        String inputstyle = "-fx-font-size:15;-fx-background-radius: 2; -fx-focus-color: #fb8500;-fx-faint-focus-color: transparent;";
        inputname.setStyle(inputstyle);
        inputcne.setStyle(inputstyle);
        inputdate.setStyle(inputstyle);
        inputgender.setStyle(inputstyle);
        inputmail.setStyle(inputstyle);
        inputphone.setStyle(inputstyle);
        inputcity.setStyle(inputstyle);
        String labelstyle = "-fx-text-fill:#14213d;-fx-font-family: 'Roboto'; -fx-font-size: 15px;";
        name.setStyle(labelstyle);
        cne.setStyle(labelstyle);
        birthday.setStyle(labelstyle);
        gender.setStyle(labelstyle);
        email.setStyle(labelstyle);
        phone.setStyle(labelstyle);
        city.setStyle(labelstyle);
        label.setStyle(labelstyle);

        double inputwidth = 350; // desired width
        double inputheight = 35; // desired height
        inputname.setPrefSize(inputwidth, inputheight);
        inputcne.setPrefSize(inputwidth, inputheight);
        inputdate.setPrefSize(inputwidth, inputheight);
        inputgender.setPrefSize(inputwidth, inputheight);
        inputmail.setPrefSize(inputwidth, inputheight);
        inputphone.setPrefSize(inputwidth, inputheight);
        inputcity.setPrefSize(inputwidth, inputheight);
        textArea.setPrefSize(inputwidth, inputheight);
        grid.setAlignment(Pos.CENTER);
        btn.setPrefSize(150, 40);
        btn.setAlignment(Pos.CENTER);
        // btn.setEffect(dropShadow);
        btn.setOnAction(e -> {
            System.out.println("username:" + inputname.getText());
            System.out.println("password:" + inputmail.getText());
        });

        btn.setOnAction(event -> {
            initializeDatabase();

            Boolean var;
            String names = inputname.getText();
            String cnes = inputcne.getText();
            LocalDate birthdays = inputdate.getValue();
            String genders = inputgender.getValue();
            String emails = inputmail.getText();
            String phones = inputphone.getText();
            String citys = inputcity.getText();
            String imgpath = uploadImage(textArea.getText());

            if (names.equals("") || cnes.equals("") || emails.equals("") || phones.equals("") || citys.equals("")) {
                alert.display(false, "Please fill out all the required fields.", "red", "Okay");
            }
            else if (!emails.contains("@")) {
                alert.display(false, "Please Enter avalid Email", "red", "Okay");
                
            } else {

                var = insertstudent(names, cnes, emails, phones, citys, birthdays, genders, imgpath);

                if (var == true) {
                    clearFields(inputname, inputcne, inputmail, inputphone, inputcity, inputdate, inputgender,
                            textArea);
                    alert.display(true, "Student registered successfully!", "green", "Okay");
                    Image img2 = new Image("Assets/Unknown_person.jpg");
                    circle.setFill(new ImagePattern(img2));
                } else
                    alert.display(false, "Student registration failed", "red", "Okay");
            }

        });

        HBox footer = new HBox(14);
        footer.setPadding(new Insets(10, 10, 30, 10));
        Button btn1 = new Button("Log out");
        Button btn2 = new Button("Student List");
        String btnstyle = new String(
                "-fx-background-color: #023047; -fx-text-fill: white;-fx-font-size: 14pt;-fx-font-family: Arial;-fx-padding: 10 20;-fx-background-radius: 5;-fx-border-width: 2px;");
        btn1.setStyle(btnstyle);
        btn2.setStyle(btnstyle);
        btn1.setEffect(dropShadow);
        btn2.setEffect(dropShadow);
        btn1.setPrefWidth(160);
        btn2.setPrefWidth(160);
        footer.getChildren().addAll(btn1, btn2);
        footer.setAlignment(Pos.CENTER);
        grid.add(footer, 1, 11);
        maincontainer.setSpacing(0);
        maincontainer.getChildren().addAll(header, grid);
        btn1.setOnAction(e -> {
            main.switchScene(home.homescene());
        });
        btn2.setOnAction(e -> {
            main.switchScene(view.viewscene());
        });
        Scene scene = new Scene(maincontainer, 800, 700);
        scene.getStylesheets().add("css/inputs.css");
		scene.getStylesheets().add("css/buttonshover.css");
        return scene;
    }

    private static void initializeDatabase() {
        String url = "jdbc:mysql://localhost:3306/ensao";
        String username = "root";
        String password = "hamza";
        try {
            conn = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection error
        }
    }

    private static void clearFields(TextField inputName, TextField inputCNE, TextField inputMail, TextField inputPhone,
            TextField inputcity, DatePicker inputDate, ChoiceBox<String> inputGender, TextArea text) {
        inputName.clear();
        inputCNE.clear();
        inputMail.clear();
        inputPhone.clear();
        inputcity.clear();
        inputDate.setValue(null);
        inputGender.getSelectionModel().clearSelection();
        text.setText("");
    }

    private static Boolean insertstudent(String name, String cne, String email, String phone, String city,
            LocalDate date,
            String gender, String imgpath) {
        initializeDatabase();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO students (name, cne, date, gender, email, phone, city,imgpath) VALUES (?, ?, ?, ?, ?, ?, ?,?)");
            pstmt.setString(1, name);
            pstmt.setString(2, cne);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.setString(4, gender);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.setString(7, city);
            pstmt.setString(8, imgpath);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private static final String IMAGE_FOLDER = "C:/Users/Spectre Gamer/Desktop/GI3_S2/JavaFXproject/src/Assets/uploads";

    private static String uploadImage(String imagePath) {
        System.out.println(imagePath);
        File sourceFile = new File(imagePath);
        String fileName = sourceFile.getName();
        System.out.println(fileName);
        Path destinationPath = Paths.get(IMAGE_FOLDER).resolve(fileName);
        // System.out.println(destinationPath);

        try {
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");

        String finalpath = "Assets/uploads/" + fileName;
        // System.out.println(finalpath);

        return finalpath;
    }

}