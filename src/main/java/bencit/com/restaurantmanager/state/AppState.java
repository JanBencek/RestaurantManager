package bencit.com.restaurantmanager.state;

import bencit.com.restaurantmanager.entities.Employee;
import bencit.com.restaurantmanager.entities.Item;
import bencit.com.restaurantmanager.entities.Order;
import bencit.com.restaurantmanager.entities.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class AppState {
    private static final ObservableList<Item> availableItems = FXCollections.observableArrayList();
    private static final ObservableList<Order> availableOrders = FXCollections.observableArrayList();
    private static final ObservableList<Table> availableTables = FXCollections.observableArrayList();
    private static final ObservableList<Employee> availableEmployees = FXCollections.observableArrayList();
    private static final ObservableList<Item> selectedItems = FXCollections.observableArrayList();

    private AppState() {}
    public static ObservableList<Item> availableItems() { return availableItems; }
    public static ObservableList<Order> availableOrders() { return availableOrders; }
    public static ObservableList<Table> availableTables(){return availableTables;}
    public static ObservableList<Employee> availableEmployees(){return availableEmployees;}
    public static ObservableList<Item> selectedItems(){return selectedItems;}
}
