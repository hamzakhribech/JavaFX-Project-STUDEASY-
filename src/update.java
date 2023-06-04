import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class update {
    public static void display(int id) {
        Stage window = new Stage();
        initializeDatabase();
        Student student = fetchStudentsFromDatabase(id);
        ImageView applogo = new ImageView(new Image("Assets/applogo.png"));
        applogo.setPreserveRatio(true);
        applogo.setFitWidth(220);
        applogo.setFitHeight(200);
        applogo.setScaleX(1.5);
        applogo.setScaleY(1.5);
        // applogo.setTranslateY(20);
        // applogo.setAlignment(Pos.CENTER_RIGHT);

        Image img = new Image(student.getImgpath());
        Circle circle = new Circle(80);
        circle.setFill(new ImagePattern(img));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(5);

        // Apply the DropShadow effect to the circle
        circle.setEffect(dropShadow);

        // Create a GridPane to hold the labels and texts
        GridPane infobox = new GridPane();
        infobox.setPadding(new Insets(10));
        infobox.setHgap(10);
        infobox.setVgap(10);
        infobox.setAlignment(Pos.CENTER);
        ColumnConstraints column1 = new ColumnConstraints(50);
        ColumnConstraints column2 = new ColumnConstraints(220);
        infobox.getColumnConstraints().addAll(column1, column2);
        Label nameLabel = new Label("Name:");
        Label emailLabel = new Label("Email:");
        Label cneLabel = new Label("CNE:");
        String labelstyle = "-fx-text-fill:#14213d;-fx-font-family: 'Roboto'; -fx-font-size: 16px;-fx-font-weight:bold;";
        nameLabel.setStyle(labelstyle);
        cneLabel.setStyle(labelstyle);
        emailLabel.setStyle(labelstyle);
        Text nameText = new Text(student.getName());
        Text emailText = new Text(student.getEmail());
        Text cneText = new Text(student.getCne());
        String textstyle = "-fx-font-family: 'Roboto'; -fx-font-size: 14px;";
        nameText.setStyle(textstyle);
        cneText.setStyle(textstyle);
        emailText.setStyle(textstyle);

        // Add labels and texts to the GridPane
        infobox.add(nameLabel, 0, 0);
        infobox.add(emailLabel, 0, 1);
        infobox.add(cneLabel, 0, 2);
        infobox.add(nameText, 1, 0);
        infobox.add(emailText, 1, 1);
        infobox.add(cneText, 1, 2);

        HBox studentInfoBox = new HBox();
        studentInfoBox.setSpacing(10);
        studentInfoBox.setPadding(new Insets(10));
        studentInfoBox.setAlignment(Pos.CENTER_LEFT);
        studentInfoBox.getChildren().addAll(circle, infobox, applogo);

        GridPane grid = new GridPane();

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
        Label city = new Label("Previous School");

        TextField inputname = new TextField(student.getName());
        inputname.setPromptText("Enter full name");
        TextField inputcne = new TextField(student.getCne());
        inputcne.setPromptText("example:F1634532...");
        DatePicker inputdate = new DatePicker();
        inputdate.setValue(student.getDate());

        ChoiceBox<String> inputgender = new ChoiceBox<>(
                FXCollections.observableArrayList("Male", "Female"));
        inputgender.setValue(student.getGender());
        TextField inputmail = new TextField(student.getEmail());
        inputmail.setPromptText("Example@gmail.com");
        TextField inputphone = new TextField(student.getPhone());
        TextField inputcity = new TextField(student.getcity());
        // image inserting
        VBox root = new VBox();
        root.setSpacing(10);
        Label label = new Label("Choose Image:");
        TextArea textArea = new TextArea(student.getImgpath());
        textArea.setEditable(false);
        textArea.setWrapText(true); // Enable text wrapping
        Button chooseButton = new Button("Select Image");
        Image icon = new Image("Assets/icondownload.png"); // Set the icon image path
        ImageView imageView = new ImageView(icon);

        imageView.setFitWidth(20); // Set the width of the icon
        imageView.setFitHeight(20); // Set the height of the icon
        imageView.setScaleX(2.5);
        imageView.setScaleY(2.5);
        chooseButton.setGraphic(imageView);
        chooseButton.setStyle("-fx-background-color:lightblue;-fx-border-color:transparent;-fx-background-radius:100");

        chooseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(window);
            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                textArea.setText(imagePath);
                System.out.println("before inserrt to img");
                System.out.println(imagePath);
                Image img2 = new Image(selectedFile.toURI().toString());
                circle.setFill(new ImagePattern(img2));
                System.out.println("after");
            }

        });

        root.getChildren().addAll(label, chooseButton, textArea);
        // ------------------------
        ButtonBase btn = new Button("submit");
        StackPane btncontainer = new StackPane();

        btncontainer.getChildren().add(btn);
        btn.setStyle(
                "  -fx-background-color: #fb8500; -fx-text-fill:#001d3d;-fx-font-size: 14px;-fx-font-family: Arial;-fx-padding: 5 10;-fx-background-radius: 5;-fx-border-radius: 5; -fx-font-weight:bold; -fx-border-color:#14213d");

        btn.setPrefSize(100, 40);
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

        grid.add(btncontainer, 1, 9);

        String inputstyle = "-fx-font-size:15;-fx-background-radius: 2; -fx-focus-color: #fb8500;-fx-faint-focus-color: transparent;";
        inputname.setStyle(inputstyle);
        inputcne.setStyle(inputstyle);
        inputdate.setStyle(inputstyle);
        inputgender.setStyle(inputstyle);
        inputmail.setStyle(inputstyle);
        inputphone.setStyle(inputstyle);
        inputcity.setStyle(inputstyle);
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
        // btn.setEffect(dropShadow);
        btn.setAlignment(Pos.CENTER);
        inputname.setOnKeyReleased(e -> {
            nameText.setText(inputname.getText());
        });
        inputcne.setOnKeyReleased(e -> {
            cneText.setText(inputcne.getText());
        });
        inputmail.setOnKeyReleased(e -> {
            emailText.setText(inputmail.getText());
        });

        btn.setOnAction(e -> {
            String names = inputname.getText();
            String cnes = inputcne.getText();
            LocalDate dates = inputdate.getValue();
            String genders = inputgender.getValue();
            String emails = inputmail.getText();
            String phones = inputphone.getText();
            String citys = inputcity.getText();
            String imagePaths = uploadImage(textArea.getText());
            if (names.equals("") || cnes.equals("") || emails.equals("") || phones.equals("") || citys.equals("")) {
                alert.display(false, "Please fill out all the required fields.", "red", "Ok");
                inputcne.setText(student.getCne());
                inputname.setText(student.getName());
                inputdate.setValue(student.getDate());
                inputgender.setValue(student.getGender());
                inputmail.setText(student.getEmail());
                inputphone.setText(student.getPhone());
                inputcity.setText(student.getcity());
                nameText.setText(student.getName());
                cneText.setText(student.getCne());
                emailText.setText(student.getEmail());
            }
            else if (!emails.contains("@")) {
                alert.display(false, "Please Enter avalid Email", "red", "Okay");
                inputcne.setText(student.getCne());
                inputname.setText(student.getName());
                inputdate.setValue(student.getDate());
                inputgender.setValue(student.getGender());
                inputmail.setText(student.getEmail());
                inputphone.setText(student.getPhone());
                inputcity.setText(student.getcity());
                nameText.setText(student.getName());
                cneText.setText(student.getCne());
                emailText.setText(student.getEmail());
            } else {
                updateStudent(id, names, cnes, dates, genders, emails, phones, citys, imagePaths);
                window.close();
            }

        });
        VBox vbox = new VBox(studentInfoBox, grid);
        window.setTitle("Update informations");
        Scene scene = new Scene(vbox, 800, 700);
        scene.getStylesheets().add("css/inputs.css");
		scene.getStylesheets().add("css/buttonshover.css");
        window.setScene(scene);
        window.showAndWait();
    }

    public static void updateStudent(int id, String name, String cne, LocalDate date, String gender, String email,
            String phone, String prevSchool, String imagePath) {
        String query = "UPDATE students SET name = ?, cne = ?, date = ?, gender = ?, email = ?, phone = ?, city = ?, imgpath = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, cne);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.setString(4, gender);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.setString(7, prevSchool);
            pstmt.setString(8, imagePath);
            pstmt.setInt(9, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {

                alert.display(true, "Update Done successfuly", "green", "OK");
            } else {
                alert.display(false, "UPDATE ERROR!", "red", "Okay");

            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Student fetchStudentsFromDatabase(int id) {
        initializeDatabase();
        Student student = null;

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("id");
                String name = rs.getString("name");
                String cne = rs.getString("cne");
                String gender = rs.getString("gender");
                LocalDate date = rs.getDate("date").toLocalDate();
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String city = rs.getString("city");
                String imgpath = rs.getString("imgpath");

                student = new Student(studentId, name, cne, email, phone, gender, city, date, imgpath);
            }

            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public static Connection conn;

    public static void initializeDatabase() {
        String url = "jdbc:mysql://localhost:3306/ensao";
        String username = "root";
        String password = "hamza";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String uploadImage(String imagePath) {
        System.out.println(imagePath);
        File sourceFile = new File(imagePath);
        String fileName = sourceFile.getName();
        System.out.println(fileName);
        java.nio.file.Path destinationPath = Paths
                .get("C:/Users/Spectre Gamer/Desktop/GI3_S2/JavaFXproject/src/Assets/uploads").resolve(fileName);
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

    public static class Student {
        private int id;
        private String name;
        private String cne;
        private String email;
        private String phone;
        private String gender;
        private String city;
        private LocalDate date;
        private String imgpath;

        public Student(int id, String name, String cne, String email, String phone, String gender, String city,
                LocalDate date, String imgpath) {
            this.id = id;
            this.name = name;
            this.cne = cne;
            this.email = email;
            this.phone = phone;
            this.gender = gender;
            this.city = city;
            this.date = date;
            this.imgpath = imgpath;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCne() {
            return cne;
        }

        public void setCne(String cne) {
            this.cne = cne;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getcity() {
            return city;
        }

        public void setcity(String city) {
            this.city = city;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }
    }
}
