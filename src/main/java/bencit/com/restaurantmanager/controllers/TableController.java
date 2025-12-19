package bencit.com.restaurantmanager.controllers;
import static bencit.com.restaurantmanager.utils.Dialog.showErrorDialog;
import bencit.com.restaurantmanager.Program;
import bencit.com.restaurantmanager.entities.Table;
import bencit.com.restaurantmanager.enums.TableLocations;
import bencit.com.restaurantmanager.enums.TableTypes;
import bencit.com.restaurantmanager.exceptions.ExistingValueException;
import bencit.com.restaurantmanager.exceptions.NullValueException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TableController {
    ObservableList<Table> availableTables = bencit.com.restaurantmanager.state.AppState.availableTables();
    @FXML private RadioButton availableTableRadioButton;
    @FXML private ComboBox<TableLocations> locationOfTableComboBox;
    @FXML private Spinner<Integer> numberOfSeatsSpinner;
    @FXML private Spinner<Integer> numberOfTableSpinner;
    @FXML private ComboBox<TableTypes> typeOfTableComboBox;
    @FXML private TableView<Table> tablesTable;
    @FXML private TableColumn<Table, String> tableAvailableColumn;
    @FXML private TableColumn<Table, String> typeOfTableColumn;
    @FXML private TableColumn<Table, String> numberOfTableColumn;
    @FXML private TableColumn<Table, String> numberOfSeatsColumn;
    @FXML private TableColumn<Table, String> locationOfTableColumn;
    @FXML private TextField pathToFileTextField;

    @FXML
    void addTable() {
        try {
            
            Boolean tableAvailable = availableTableRadioButton.isSelected();
            TableLocations tableLocation = locationOfTableComboBox.getValue();
            Integer numberOfSeats = numberOfSeatsSpinner.getValue();
            Integer numberOfTable = numberOfTableSpinner.getValue();
            TableTypes typeOfTable = typeOfTableComboBox.getValue();
            if (tableLocation == null || numberOfSeats == null || numberOfTable == null || typeOfTable == null)
                throw new NullValueException("");
            availableTables.stream().filter(table -> Objects.equals(table.getId(), numberOfTable)).findFirst().ifPresent(x->{throw new ExistingValueException("Stol");});
            Table tableToAdd = new Table(numberOfTable, typeOfTable, numberOfSeats, tableAvailable, tableLocation);

            availableTables.add(tableToAdd);
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    public void initialize() {
        SpinnerValueFactory<Integer> numberOfTableValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        SpinnerValueFactory<Integer> numberOfSeatsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        numberOfTableSpinner.setValueFactory(numberOfTableValueFactory);
        numberOfSeatsSpinner.setValueFactory(numberOfSeatsValueFactory);
        locationOfTableComboBox.getItems().addAll(TableLocations.values());
        typeOfTableComboBox.getItems().addAll(TableTypes.values());
        availableTableRadioButton.setSelected(true);
        numberOfTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tableAvailableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTableAvailable().equals(true) ? "Da" : "Ne"));
        typeOfTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().name()));
        locationOfTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTableLocation().name()));
        numberOfSeatsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumberOfSeats().toString()));
        tablesTable.setItems(bencit.com.restaurantmanager.state.AppState.availableTables());
    }

    File file;
    @FXML
    public void findFile() {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(Program.getStage());
        pathToFileTextField.setText(String.valueOf(file));
        readFromFile();
    }
    @FXML
    void saveToFile() {
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
            jsonb.toJson(new ArrayList<>(availableTables), new FileWriter(file, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void readFromFile() {
        if (file == null || !file.exists()) {
            bencit.com.restaurantmanager.utils.Dialog.showErrorDialog(new IOException());
            return;
        }

        try (Jsonb jsonb = JsonbBuilder.create();
             FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            List<Table> loadedTables = jsonb.fromJson(reader, new ArrayList<Table>(){}.getClass().getGenericSuperclass());
            if (loadedTables != null) {
                availableTables.setAll(loadedTables);
                numberOfTableSpinner.getValueFactory().setValue(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearFile() {
        availableTables.clear();
        if (file != null && file.exists()) {
            try (FileWriter writer = new FileWriter(file, false)) {
                writer.write("[]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}


