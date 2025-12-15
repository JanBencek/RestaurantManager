package bencit.com.restaurantmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {
    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("employees-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Zaposlenici");
        stage.setScene(scene);
        stage.show();
    }
    public static Stage getStage() {
        return mainStage;
    }
}
