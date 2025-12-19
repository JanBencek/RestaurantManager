module bencit.com.restaurantmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jakarta.json.bind;
    opens bencit.com.restaurantmanager to javafx.fxml;
    exports bencit.com.restaurantmanager;
    exports bencit.com.restaurantmanager.controllers;
    exports bencit.com.restaurantmanager.entities;
    exports bencit.com.restaurantmanager.enums;
    exports bencit.com.restaurantmanager.records;
    exports bencit.com.restaurantmanager.utils;
    exports bencit.com.restaurantmanager.state;
    opens bencit.com.restaurantmanager.controllers to javafx.fxml;
}