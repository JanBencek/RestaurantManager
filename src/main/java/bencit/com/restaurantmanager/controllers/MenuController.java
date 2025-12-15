package bencit.com.restaurantmanager.controllers;

import bencit.com.restaurantmanager.Program;
import bencit.com.restaurantmanager.utils.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
    public void showEmployeesScreen() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("employees-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Program.getStage().setTitle("Zaposlenici");
            Program.getStage().setScene(scene);
            Program.getStage().show();

        }
        catch (Exception e){
            Dialog.showErrorDialog(e);
        }
    }
    public void showOrdersScreen() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("orders-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Program.getStage().setTitle("Narudžbe");
            Program.getStage().setScene(scene);
            Program.getStage().show();

        }
        catch (Exception e){
            Dialog.showErrorDialog(e);
        }
    }
    public void showTablesScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("tables-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Program.getStage().setTitle("Stolovi");
            Program.getStage().setScene(scene);
            Program.getStage().show();

        }
        catch (Exception e){
            Dialog.showErrorDialog(e);
        }
    }
    public void showItemsScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("items-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Program.getStage().setTitle("Proizvodi");
            Program.getStage().setScene(scene);
            Program.getStage().show();

        }
        catch (Exception e){
            Dialog.showErrorDialog(e);
        }
    }
}
