import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class main extends Application {
    public static Stage window;

    @Override
    public void start(Stage Window) throws Exception {
        window = Window;
        // we use it when we want fix a scene ,directly by making it as default scene
        Scene s = home.homescene(); 
        // Scene s = insert.insertscene();

        Image icon = new Image("Assets/applogoicon.png");
        window.getIcons().add(icon); // set the stage icon
        window.setScene(s);
        window.setTitle("STUDEASY");
        window.setIconified(false);
        window.show();
    }

    public static Stage getstStage() {
        return window;
    }

    public static void switchScene(Scene scene) {
        if (window != null) {
            window.setScene(scene);
        } else {
            System.out.println("Main stage is null");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
