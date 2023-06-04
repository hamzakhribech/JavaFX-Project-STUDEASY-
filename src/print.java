import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class print {

    public static void display(int id) {
        Student student = fetchStudentsFromDatabase(id);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
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
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(10);

        // Create labels and texts for each information
        Label nameLabel = new Label("Name:");
        Label cneLabel = new Label("CNE:");
        Label emailLabel = new Label("Email:");
        Label phoneLabel = new Label("Phone:");
        Label genderLabel = new Label("Gender:");
        Label cityLabel = new Label("City:");
        Label dateLabel = new Label("Birth Date:");
        String labelstyle = "-fx-text-fill:#14213d;-fx-font-family: 'Roboto'; -fx-font-size: 16px;-fx-font-weight:bold;";
        nameLabel.setStyle(labelstyle);
        cneLabel.setStyle(labelstyle);
        emailLabel.setStyle(labelstyle);
        phoneLabel.setStyle(labelstyle);
        genderLabel.setStyle(labelstyle);
        cityLabel.setStyle(labelstyle);
        dateLabel.setStyle(labelstyle);

        Label nameText = new Label(student.getName());
        Label cneText = new Label(student.getCne());
        Label emailText = new Label(student.getEmail());
        Label phoneText = new Label(student.getPhone());
        Label genderText = new Label(student.getGender());
        Label cityText = new Label(student.getcity());
        Label dateText = new Label(student.getDate().toString());
        String textstyle = "-fx-font-family: 'Roboto'; -fx-font-size: 14px;";
        nameText.setStyle(textstyle);
        cneText.setStyle(textstyle);
        emailText.setStyle(textstyle);
        phoneText.setStyle(textstyle);
        genderText.setStyle(textstyle);
        cityText.setStyle(textstyle);
        dateText.setStyle(textstyle);

        // Add labels and texts to the GridPane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(cneLabel, 0, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(phoneLabel, 0, 3);
        gridPane.add(genderLabel, 0, 4);
        gridPane.add(cityLabel, 0, 5);
        gridPane.add(dateLabel, 0, 6);

        gridPane.add(nameText, 1, 0);
        gridPane.add(cneText, 1, 1);
        gridPane.add(emailText, 1, 2);
        gridPane.add(phoneText, 1, 3);
        gridPane.add(genderText, 1, 4);
        gridPane.add(cityText, 1, 5);
        gridPane.add(dateText, 1, 6);

        HBox footer = new HBox(8);
        footer.setPadding(new Insets(10, 10, 30, 10));
        Button btn1 = new Button("Print");
        // Button btn2 = new Button("Home");
        String btnstyle = new String(
                "-fx-background-color: #023047; -fx-text-fill: white;-fx-font-size: 14pt;-fx-font-family: Arial;-fx-padding: 10 20;-fx-background-radius: 5;-fx-border-width: 2px;");
        btn1.setStyle(btnstyle);
        btn1.setPrefWidth(150);
        footer.setAlignment(Pos.CENTER);
        // btn2.setStyle(btnstyle);
        footer.getChildren().addAll(btn1);
        ImageView applogo = new ImageView(new Image("Assets/applogo.png"));

        applogo.setFitHeight(100);
        applogo.setFitWidth(100);
        applogo.setScaleX(2.5);
        applogo.setScaleY(2.5);
        vbox.getChildren().addAll(circle, gridPane, footer, applogo);

        btn1.setOnAction(E -> printing(vbox));
        //to get A4 sizes for print
        double paperWidth = Paper.A4.getWidth();
        double paperHeight = Paper.A4.getHeight();
        Scene scene = new Scene(vbox, paperWidth, paperHeight-50);
        scene.getStylesheets().add("css/buttonshover.css");
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        Image icon = new Image("Assets/applogoicon.png");
        primaryStage.getIcons().add(icon); // set the stage icon
        primaryStage.setTitle("Print Infos");
        primaryStage.showAndWait();
    }

    private static void printing(VBox content) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(null)) {
            Printer printer = printerJob.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
                    Printer.MarginType.HARDWARE_MINIMUM);
            PrinterAttributes printerAttributes = printer.getPrinterAttributes();
            printerJob.getJobSettings().setPageLayout(pageLayout);

            boolean printed = printerJob.printPage(content);
            if (printed) {
                printerJob.endJob();
            } else {
                printerJob.cancelJob();
            }
        }
    }
    private static Connection conn;

    private static void initializeDatabase() {
        String url = "jdbc:mysql://localhost:3306/ensao";
        String username = "root";
        String password = "hamza";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static print.Student fetchStudentsFromDatabase(int id) {
        // Initialize database connection
        initializeDatabase();

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE id=" + id);
            ResultSet rs = pstmt.executeQuery();

            // Iterate over the result set and create Student objects
            while (rs.next()) {
                String name = rs.getString("name");
                String cne = rs.getString("cne");
                String gender = rs.getString("gender");
                LocalDate date = rs.getDate("date").toLocalDate();
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String city = rs.getString("city");
                String imgpath = rs.getString("imgpath");

                // Retrieve other columns as needed
                Student stu = new Student(id, name, cne, email, phone, gender, city, date, imgpath);
                return stu;
            }

            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;

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
                LocalDate dates, String imgpath) {
            this.id = id;
            this.name = name;
            this.cne = cne;
            this.email = email;
            this.phone = phone;
            this.gender = gender;
            this.city = city;
            this.date = dates;
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
