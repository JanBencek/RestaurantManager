package bencit.com.restaurantmanager.controllers;

import bencit.com.restaurantmanager.Program;
import bencit.com.restaurantmanager.entities.Item;
import bencit.com.restaurantmanager.entities.Order;
import bencit.com.restaurantmanager.entities.Table;
import bencit.com.restaurantmanager.enums.PaymentMethods;
import bencit.com.restaurantmanager.exceptions.NullValueException;
import bencit.com.restaurantmanager.state.AppState;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
private final ObservableList<Order> availableOrders = bencit.com.restaurantmanager.state.AppState.availableOrders();
    private int orderIdTracker = 1;
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> numberOfOrderTableColumn;
    @FXML private TableColumn<Order, String> itemsOfOrderColumn;
    @FXML private TableColumn<Order, String> totalPriceOfOrderColumn;
    @FXML private ComboBox<Integer> numberOfOrderTableComboBox;
    @FXML private ComboBox<PaymentMethods> paymentMethodOfOrderComboBox;
    @FXML private RadioButton orderPaidRadioButton;
    @FXML private TextField pathToFileTextField;
    @FXML private Button addItemsToOrderButton;
    @FXML private TableColumn<Order, PaymentMethods> paymentMethodOfOrderColumn;
    @FXML private TableColumn<Order, String> orderPaidColumn;

    @FXML
    public void initialize() {
        ordersTable.setItems(AppState.availableOrders());
        numberOfOrderTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTableId()));
        itemsOfOrderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getItemsOfOrder().stream()
                        .map(i -> i.getItemName() + " " + i.getItemSize() + " | " + i.getQuantity())
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("")
        ));
        totalPriceOfOrderColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItemsOfOrder().stream().map(i -> i.getItemPrice().multiply(BigDecimal.valueOf(i.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add)+cellData.getValue().getItemsOfOrder().getFirst().getCurrency().name()));
        paymentMethodOfOrderColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPaymentMethod()));
        orderPaidColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderPaid().equals(true) ? "Da" : "Ne"));
        numberOfOrderTableComboBox.getItems().setAll(AppState.availableTables().stream().map(Table::getId).toList());
        paymentMethodOfOrderComboBox.getItems().setAll(PaymentMethods.values());

    }

    @FXML void openAddItemsToOrderDialog(){
        try{
            FXMLLoader loader = new FXMLLoader(Program.class.getResource("add-items-to-order-view.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Odaberite proizvode");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(addItemsToOrderButton.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML void addOrder() {
        Integer selectedTableId = numberOfOrderTableComboBox.getValue();
        PaymentMethods selectedPaymentMethod = paymentMethodOfOrderComboBox.getValue();
        Boolean orderPaid = orderPaidRadioButton.isSelected();
        ArrayList<Item> itemsOfOrder = new ArrayList<>(AppState.selectedItems());
        if(selectedTableId == null || selectedPaymentMethod == null) throw new NullValueException("Nešto");
        availableOrders.add(new Order(orderIdTracker, selectedTableId, selectedPaymentMethod, orderPaid, itemsOfOrder));
        ordersTable.setItems(AppState.availableOrders());
        orderIdTracker++;
        AppState.selectedItems().clear();
        ordersTable.refresh();
    }
    File file;
    @FXML private void findFile() {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(Program.getStage());
        pathToFileTextField.setText(String.valueOf(file));
        readFromFile();
    }
    @FXML void saveToFile() {
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
            jsonb.toJson(new ArrayList<>(availableOrders), new FileWriter(file, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void readFromFile() {
        if (file == null || !file.exists()) {
            bencit.com.restaurantmanager.utils.Dialog.showErrorDialog(new IOException());
            return;
        }

        try (Jsonb jsonb = JsonbBuilder.create();
             FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            List<Order> loadedOrders = jsonb.fromJson(reader, new ArrayList<Order>(){}.getClass().getGenericSuperclass());
            if (loadedOrders != null) {
                availableOrders.setAll(loadedOrders);
                orderIdTracker = availableOrders.stream().mapToInt(Order::getId).max().orElse(0) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML void clearFile() {
        availableOrders.clear();
        orderIdTracker = 1;
        if (file != null && file.exists()) {
            try (FileWriter writer = new FileWriter(file, false)) {
                writer.write("[]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
