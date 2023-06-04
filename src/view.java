import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.sql.*;
import java.time.LocalDate;

public class view {

    private static TableView<Student> tableView;
    private static ObservableList<Student> students;

    public static Scene viewscene() {
        // Create the TableView
        tableView = new TableView<>();
        tableView.setPrefHeight(390);

        fetchStudentsFromDatabase();

        // Create table columns

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(130);

        TableColumn<Student, String> cneColumn = new TableColumn<>("CNE");
        cneColumn.setCellValueFactory(new PropertyValueFactory<>("cne"));
        cneColumn.setPrefWidth(110);

        TableColumn<Student, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setPrefWidth(50);

        TableColumn<Student, String> dateColumn = new TableColumn<>("Birth Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(120);

        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(150);

        TableColumn<Student, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setPrefWidth(140);

        TableColumn<Student, String> cityColumn = new TableColumn<>("city");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityColumn.setPrefWidth(120);

        // Add columns to the TableView
        tableView.getColumns().addAll(cneColumn, nameColumn, genderColumn, dateColumn, emailColumn, phoneColumn,  cityColumn);
        fetchStudentsFromDatabase();//load table data from student class
        tableView.getSelectionModel().selectFirst();//we select by default the first row
        // -------------------------------
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(5);

        // Create buttons for delete and update
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        Button printButton = new Button("Print Informations");

        deleteButton.setPrefWidth(150);
        updateButton.setPrefWidth(150);
        printButton.setPrefWidth(150);

        String btnstyle = new String(
                "-fx-background-color: #fb8500; -fx-text-fill:#001d3d;-fx-font-size: 13px;-fx-font-family: Arial;-fx-font-weight:bold;-fx-padding: 5 10;-fx-background-radius: 5;-fx-border-radius: 5; -fx-border-color:white;");
        deleteButton.setStyle(btnstyle);
        updateButton.setStyle(btnstyle);
        printButton.setStyle(btnstyle);

        printButton.setOnAction(event -> printSelectedStudent());
        VBox buttons = new VBox(deleteButton, updateButton, printButton);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Image img = new Image(students.get(0).getimg());
        Circle circle = new Circle(80);
        circle.setFill(new ImagePattern(img));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);

        circle.setEffect(dropShadow);

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
        Text nameText = new Text(students.get(0).getName());
        Text emailText = new Text(students.get(0).getEmail());
        Text cneText = new Text(students.get(0).getCne());
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

        // load informatons
        tableView.setOnMousePressed(event -> {
            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 1) {
                Image img2 = new Image(selectedStudent.getimg());
                circle.setFill(new ImagePattern(img2));
                nameText.setText(selectedStudent.getName());
                emailText.setText(selectedStudent.getEmail());
                cneText.setText(selectedStudent.getCne());

            }
        });

        deleteButton.setOnAction(event -> {
            try {
                deleteSelectedStudent();// delete the selected student
            } catch (SQLException e) {

                e.printStackTrace();
            }
            //identify the selected student
            SelectionModel<Student> selectionModel = tableView.getSelectionModel();
            int selectedIndex = selectionModel.getSelectedIndex();//identify selected row index
            fetchStudentsFromDatabase();//make sure that our data are UPdated after deleing the student
            if (selectedIndex > 0) {  //the new selected student will be the previous one
                selectionModel.select(selectedIndex - 1);
            } else  { //but if the deleted one was the first here the new selected will be the next one
                selectionModel.select(selectedIndex + 1);
               } 

            
            fetchStudentsFromDatabase();
            
            selectionModel.select(selectedIndex);
            Image img1 = new Image(students.get(selectedIndex).getimg());
            circle.setFill((new ImagePattern(img1)));
            nameText.setText(students.get(selectedIndex).getName());
            emailText.setText(students.get(selectedIndex).getEmail());
            cneText.setText(students.get(selectedIndex).getCne());
        });

        updateButton.setOnAction(event -> {

            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
            int id = selectedStudent.getId();
            SelectionModel<Student> selectionModel = tableView.getSelectionModel();
            int selectedIndex = selectionModel.getSelectedIndex();
            if (selectedStudent != null) {
                update.display(id);
                fetchStudentsFromDatabase();

                selectionModel.select(selectedIndex);
                //to load data after updating to be changed smouthly after the update process
                Image img1 = new Image(students.get(selectedIndex).getimg());
                circle.setFill((new ImagePattern(img1)));
                nameText.setText(students.get(selectedIndex).getName());
                emailText.setText(students.get(selectedIndex).getEmail());
                cneText.setText(students.get(selectedIndex).getCne());

            }
        });

        // Create an HBox to hold the image, infoBox, and buttons
        HBox studentInfoBox = new HBox();
        studentInfoBox.setSpacing(20);
        studentInfoBox.setPadding(new Insets(10));
        studentInfoBox.setAlignment(Pos.CENTER_LEFT);
        studentInfoBox.getChildren().addAll(circle, infobox, buttons);

        // Create a VBox to hold the tableView and buttons
        ImageView applogo = new ImageView(new Image("Assets/applogo.png"));
        applogo.setPreserveRatio(true);
        applogo.setFitHeight(100);
        applogo.setFitWidth(100);
        applogo.setScaleX(3);
        applogo.setScaleY(3);
        applogo.setTranslateY(10);
        StackPane logo = new StackPane(applogo);
        HBox footer = new HBox(8);
        footer.setPadding(new Insets(10, 10, 30, 10));
        Button btn1 = new Button("Log out");
        Button btn2 = new Button("Insert New student");
        btn1.setOnAction(e -> {
            main.switchScene(home.homescene());
        });
        btn2.setOnAction(e -> {
            main.switchScene(insert.insertscene());
        });
        String btnstyle2 = new String(
                "-fx-background-color: #023047; -fx-text-fill: white;-fx-font-size: 10pt;-fx-font-family: Arial;-fx-padding: 10 20;-fx-background-radius: 5;-fx-border-width: 2px;");
        btn1.setStyle(btnstyle2);
        btn2.setStyle(btnstyle2);
        btn1.setEffect(dropShadow);
        btn2.setEffect(dropShadow);
        btn1.setPrefWidth(170);
        btn2.setPrefWidth(170);
        footer.getChildren().addAll(btn1, btn2);
        footer.setAlignment(Pos.CENTER);
        VBox root = new VBox(logo, tableView, studentInfoBox, footer);

        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add("css/tableview.css");
		scene.getStylesheets().add("css/buttonshover.css");
        return scene;

    }

    private static void fetchStudentsFromDatabase() {
        // Initialize database connection
        initializeDatabase();

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students");
            ResultSet rs = pstmt.executeQuery();

            students = FXCollections.observableArrayList();

            // Iterate over the result set and create Student objects
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String cne = rs.getString("cne");
                String gender = rs.getString("gender");
                LocalDate date = rs.getDate("date").toLocalDate();
                String dates = date.toString();
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String city = rs.getString("city");
                String imgpath = rs.getString("imgpath");

                // Retrieve other columns as needed
                Student stu = new Student(id, name, cne, email, phone, gender, city, dates, imgpath);

                students.add(stu);
            }

            // Set the data in the TableView
            tableView.setItems(students);

            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void deleteSelectedStudent() throws SQLException {
        initializeDatabase();
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        int id = selectedStudent.getId();

        if (selectedStudent != null) {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE id = " + id);
            pstmt.executeUpdate();

        }
    }

    private static void printSelectedStudent() {

        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        int id = selectedStudent.getId();
        SelectionModel<Student> selectionModel = tableView.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        if (selectedStudent != null) {
            print.display(id);
            fetchStudentsFromDatabase();
            selectionModel.select(selectedIndex);

        }

    }

    // Database connection variables and methods
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

    public static class Student {
        private int id;
        private String date;
        private String name;
        private String cne;
        private String email;
        private String phone;
        private String gender;
        private String city;
        private String imgpath;

        public Student(int id, String name, String cne, String email, String phone, String gender, String city,
                String date, String imgpath) {
            this.id = id;
            this.date = date;
            this.name = name;
            this.cne = cne;
            this.email = email;
            this.phone = phone;
            this.gender = gender;
            this.city = city;
            this.imgpath = imgpath;

        }

        public String getimg() {
            return imgpath;
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

        public String getDate() {
            return date;
        }

        public void setDate(String name) {
            this.date = name;
        }

        public String getGender() {
            return gender;
        }

        public void setgender(String name) {
            this.gender = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String name) {
            this.phone = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String name) {
            this.email = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String name) {
            this.city = name;
        }

    }

}
