package bencit.com.restaurantmanager.controllers;
import bencit.com.restaurantmanager.entities.Item;
import bencit.com.restaurantmanager.state.AppState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PickOrderItemsController extends Dialog<ObservableList<Item>> {

    ObservableList<Item> availableItems = AppState.availableItems();
    private final ObservableList<Item> itemsOfOrder = FXCollections.observableArrayList();

    @FXML private Button confirmItemsToOrder;
    @FXML private TableColumn<Item, String> selectNameOfItemColumn;
    @FXML private TableColumn<Item, String> selectPriceOfItemColumn;
    @FXML private TableColumn<Item, String> selectSizeOfItemColumn;
    @FXML private TableView<Item> selectableItemsTable;
    @FXML private TableView<Item> selectedItemsTable;
    @FXML private TableColumn<Item, String> selectedNameOfItemColumn;
    @FXML private TableColumn<Item, String> selectedPriceOfItemColumn;
    @FXML private TableColumn<Item, String> selectedQuantityOfItemColumn;
    @FXML private TableColumn<Item, String> selectedSizeOfItemColumn;

    @FXML public void initialize() {
        selectableItemsTable.setItems(availableItems);
        selectNameOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        selectSizeOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemSize().name()));
        selectPriceOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemPrice().toString()+cellData.getValue().getCurrency().name()));
        selectedNameOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        selectedSizeOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemSize().name()));
        selectedPriceOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemPrice().toString()+cellData.getValue().getCurrency().name()));
        selectedQuantityOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity().toString()));
        selectableItemsTable.setRowFactory(tv ->{
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> addItemOnClick(event, row));
            return row;
        });
        selectedItemsTable.setRowFactory(tv->{
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> removeItemOnClick(event, row));
            return row;
        });
    }



    private void addItemOnClick(MouseEvent event, TableRow<Item> row) {
        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            Item itemToAdd = row.getItem();
            if(selectedItemsTable.getItems().contains(itemToAdd)){
                selectedItemsTable.getItems().filtered(x->x.equals(itemToAdd)).forEach(x->x.setQuantity(x.getQuantity()+1));
            }
            else{
                itemToAdd.setQuantity(1);
                itemsOfOrder.add(itemToAdd);
            }
            selectedItemsTable.getItems().removeAll();
            selectedItemsTable.setItems(itemsOfOrder);
            selectedItemsTable.refresh();
        }
    }

    private void removeItemOnClick(MouseEvent event, TableRow<Item> row){
        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            Item itemToRemove = row.getItem();
            itemsOfOrder.filtered(x->x.equals(itemToRemove)).forEach(x->{
                if(x.getQuantity()>=1){
                    x.setQuantity(x.getQuantity()-1);
                }
            });
            itemsOfOrder.removeAll(itemsOfOrder.filtered(x->x.getQuantity()==0));
            selectedItemsTable.setItems(itemsOfOrder);
            selectedItemsTable.refresh();
        }
    }



    @FXML private void addItemsToOrder() {
        AppState.selectedItems().setAll(itemsOfOrder);
        System.out.println(AppState.selectedItems());
        Stage currentStage = (Stage) confirmItemsToOrder.getScene().getWindow();
        currentStage.close();
    }

    @FXML private void clearItemsInOrder() {
    itemsOfOrder.clear();
    selectedItemsTable.setItems(itemsOfOrder);
    selectedItemsTable.refresh();
    }

}
