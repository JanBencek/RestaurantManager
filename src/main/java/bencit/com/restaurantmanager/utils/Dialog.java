package bencit.com.restaurantmanager.utils;

import javafx.scene.control.Alert;

public class Dialog {
    public static void showErrorDialog(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Greška");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
