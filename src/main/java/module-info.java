module bencit.com.restaurantmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens bencit.com.restaurantmanager to javafx.fxml;
    exports bencit.com.restaurantmanager;
    exports bencit.com.restaurantmanager.controllers;
    opens bencit.com.restaurantmanager.controllers to javafx.fxml;
}