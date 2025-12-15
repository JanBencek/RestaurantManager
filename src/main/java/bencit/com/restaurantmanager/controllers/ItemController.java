package bencit.com.restaurantmanager.controllers;
import bencit.com.restaurantmanager.entities.Drink;
import bencit.com.restaurantmanager.entities.Food;
import bencit.com.restaurantmanager.entities.Item;
import bencit.com.restaurantmanager.enums.Currencies;
import bencit.com.restaurantmanager.enums.DrinkSizes;
import bencit.com.restaurantmanager.enums.FoodSizes;
import bencit.com.restaurantmanager.enums.ItemTypes;
import bencit.com.restaurantmanager.exceptions.ExistingValueException;
import bencit.com.restaurantmanager.exceptions.NullValueException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.math.BigDecimal;

import static bencit.com.restaurantmanager.utils.Dialog.showErrorDialog;
import static bencit.com.restaurantmanager.utils.Parsing.parseStringToBigDecimal;

public class ItemController {


    @FXML
    private Button addItemButton;

    @FXML
    private TableColumn<Item, Boolean> availableItemColumn;

    @FXML
    private TableColumn<Item, String> descriptionOfItemColumn;

    @FXML
    private TextField descriptionOfItemTextField;

    @FXML
    private RadioButton itemAvailableRadioButton;

    @FXML
    private TableColumn<Item, String> nameOfItemColumn;

    @FXML
    private TextField nameOfItemTextField;

    @FXML
    private TableColumn<Item, String> typeOfItemColumn;

    @FXML
    private TableColumn<Item, String> idOfItemColumn;

    @FXML
    private ComboBox<String> sizeOfItemComboBox;

    @FXML
    private ComboBox<ItemTypes> typeOfItemComboBox;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TextField priceOfItemTextField;

    @FXML
    private ComboBox<Currencies> currencyOfItemComboBox;

    @FXML
    private TableColumn<Item, String> priceOfItemColumn;

    @FXML
    void fillSizeOfItem(MouseEvent event) {
        if (typeOfItemComboBox.getValue() != null) {
            if (typeOfItemComboBox.getValue().name().equals("HRANA")) {
                sizeOfItemComboBox.getItems().clear();
                for(FoodSizes foodSizes : FoodSizes.values()){
                    sizeOfItemComboBox.getItems().add(foodSizes.name());
                }
            }
            else if(typeOfItemComboBox.getValue().name().equals("PIĆE")){
                sizeOfItemComboBox.getItems().clear();
                for(DrinkSizes drinkSizes : DrinkSizes.values()){
                    sizeOfItemComboBox.getItems().add(drinkSizes.name().replace("_",""));
                }
            }
        }

    }
    public static Integer itemIdTracker=1;
    @FXML
    public void addItem(ActionEvent event) {
        try {
            ObservableList<Item> itemContents = itemTable.getItems();
            ItemTypes typeOfItem = typeOfItemComboBox.getValue();
            String nameOfItem = nameOfItemTextField.getText();
            String descriptionOfItem = descriptionOfItemTextField.getText();
            Boolean availabilityOfItem = itemAvailableRadioButton.isSelected();
            BigDecimal priceOfItem = parseStringToBigDecimal(priceOfItemTextField.getText());
            Currencies currencyOfItem = currencyOfItemComboBox.getValue();
            if(typeOfItem == null || nameOfItem == null || descriptionOfItem == null) throw new NullValueException("");
            itemContents.stream().filter(item -> item.getItemName().equals(nameOfItem)).findFirst().ifPresent(x->{throw new ExistingValueException("Proizvod");});
            if(typeOfItem.name().equals("HRANA")){
                FoodSizes sizeOfItem = FoodSizes.valueOf(sizeOfItemComboBox.getValue());
                itemContents.add(new Food(itemIdTracker, nameOfItem,descriptionOfItem,priceOfItem,typeOfItem, sizeOfItem,availabilityOfItem,currencyOfItem));
                itemIdTracker++;
            }
            else if(typeOfItem.name().equals("PIĆE")){
                DrinkSizes sizeOfItem=DrinkSizes.valueOf(sizeOfItemComboBox.getValue().replace("_",""));
                itemContents.add(new Drink(itemIdTracker,nameOfItem,descriptionOfItem,priceOfItem,typeOfItem, sizeOfItem,availabilityOfItem,currencyOfItem));
                itemIdTracker++;
            }

        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    public void initialize() {
        typeOfItemComboBox.getItems().addAll(ItemTypes.values());
        typeOfItemComboBox.getSelectionModel().selectFirst();
        nameOfItemTextField.requestFocus();
        itemAvailableRadioButton.setSelected(true);
        sizeOfItemComboBox.getItems().clear();
        for(FoodSizes foodSizes : FoodSizes.values()){
            sizeOfItemComboBox.getItems().add(foodSizes.name());
        }
        for(Currencies currency : Currencies.values()){
            currencyOfItemComboBox.getItems().add(currency);
        }
        idOfItemColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getId().toString()));
        sizeOfItemComboBox.getSelectionModel().selectFirst();
        currencyOfItemComboBox.getSelectionModel().selectFirst();
        typeOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemType().name()));
        nameOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        descriptionOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemDescription()));
        availableItemColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getAvailable()));
        priceOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemPrice().toString()+cellData.getValue().getCurrency().name()));
    }

}
