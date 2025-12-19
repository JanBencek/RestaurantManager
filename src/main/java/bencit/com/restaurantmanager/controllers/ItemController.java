package bencit.com.restaurantmanager.controllers;

import bencit.com.restaurantmanager.Program;
import bencit.com.restaurantmanager.entities.Item;
import bencit.com.restaurantmanager.enums.Currencies;
import bencit.com.restaurantmanager.enums.ItemSizes;
import bencit.com.restaurantmanager.enums.ItemTypes;
import bencit.com.restaurantmanager.exceptions.ExistingValueException;
import bencit.com.restaurantmanager.exceptions.NullValueException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static bencit.com.restaurantmanager.utils.Dialog.showErrorDialog;
import static bencit.com.restaurantmanager.utils.Parsing.parseStringToBigDecimal;

public class ItemController {
    ObservableList<Item> availableItems = bencit.com.restaurantmanager.state.AppState.availableItems();
    private Integer itemIdTracker = 1;
    @FXML private TextField pathToFileTextField;
    @FXML private TableColumn<Item, Boolean> availableItemColumn;
    @FXML private TableColumn<Item, String> descriptionOfItemColumn;
    @FXML private TextField descriptionOfItemTextField;
    @FXML private RadioButton itemAvailableRadioButton;
    @FXML private TableColumn<Item, String> nameOfItemColumn;
    @FXML private TextField nameOfItemTextField;
    @FXML private TableColumn<Item, String> idOfItemColumn;
    @FXML private ComboBox<String> sizeOfItemComboBox;
    @FXML private ComboBox<ItemTypes> typeOfItemComboBox;
    @FXML private TableView<Item> itemsTable;
    @FXML private TextField priceOfItemTextField;
    @FXML private ComboBox<Currencies> currencyOfItemComboBox;
    @FXML private TableColumn<Item, String> priceOfItemColumn;

    @FXML
    private void addItem() {
        try {
            ItemTypes typeOfItem = typeOfItemComboBox.getValue();
            String nameOfItem = nameOfItemTextField.getText();
            String descriptionOfItem = descriptionOfItemTextField.getText();
            Boolean availabilityOfItem = itemAvailableRadioButton.isSelected();
            BigDecimal priceOfItem = parseStringToBigDecimal(priceOfItemTextField.getText());
            Currencies currencyOfItem = currencyOfItemComboBox.getValue();
            ItemSizes sizeOfItem = ItemSizes.valueOf(sizeOfItemComboBox.getValue());
            if (typeOfItem == null || nameOfItem == null || descriptionOfItem == null) throw new NullValueException("");
            availableItems.stream().filter(item -> item.getItemName().equals(nameOfItem)).findFirst().ifPresent(x -> {throw new ExistingValueException("Proizvod");});
            availableItems.add(new Item(itemIdTracker, nameOfItem, descriptionOfItem, priceOfItem, sizeOfItem, availabilityOfItem, currencyOfItem));
            nameOfItemTextField.clear();
            descriptionOfItemTextField.clear();
            priceOfItemTextField.clear();

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
        for (ItemSizes itemSizes : ItemSizes.values()) {
            sizeOfItemComboBox.getItems().add(itemSizes.name());
        }
        for (Currencies currency : Currencies.values()) {
            currencyOfItemComboBox.getItems().add(currency);
        }
        idOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        sizeOfItemComboBox.getSelectionModel().selectFirst();
        currencyOfItemComboBox.getSelectionModel().selectFirst();
        nameOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        descriptionOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemDescription()));
        availableItemColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getAvailable()));
        priceOfItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemPrice().toString() + cellData.getValue().getCurrency().name()));
        itemsTable.setItems(bencit.com.restaurantmanager.state.AppState.availableItems());

    }


    File file;
    @FXML private void findFile() {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(Program.getStage());
        pathToFileTextField.setText(String.valueOf(file));
        readFromFile();
    }

    @FXML private void saveToFile() {
        if (file == null) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save File");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            file = fc.showSaveDialog(Program.getStage());
            if (file == null) {
                return;
            }
            pathToFileTextField.setText(String.valueOf(file));
        }

        JsonbConfig config = new JsonbConfig().withFormatting(true);
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            jsonb.toJson(new ArrayList<>(availableItems), new FileWriter(file, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void readFromFile() {
        if (file == null || !file.exists()) {
            bencit.com.restaurantmanager.utils.Dialog.showErrorDialog(new IOException());
            return;
        }

        try (Jsonb jsonb = JsonbBuilder.create();
             FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            List<Item> loadedItems = jsonb.fromJson(reader, new ArrayList<Item>() {}.getClass().getGenericSuperclass());
            if (loadedItems != null) {
                availableItems.setAll(loadedItems);
                itemIdTracker = availableItems.stream().mapToInt(Item::getId).max().orElse(0) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML private void clearFile() {
        availableItems.clear();
        itemIdTracker = 1;

        if (file != null && file.exists()) {
            try (FileWriter writer = new FileWriter(file, false)) {
                writer.write("[]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
