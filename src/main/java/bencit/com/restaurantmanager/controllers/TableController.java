package bencit.com.restaurantmanager.controllers;

import bencit.com.restaurantmanager.entities.Table;
import bencit.com.restaurantmanager.enums.TableLocations;
import bencit.com.restaurantmanager.enums.TableTypes;
import bencit.com.restaurantmanager.exceptions.ExistingValueException;
import bencit.com.restaurantmanager.exceptions.NullValueException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.Objects;

import static bencit.com.restaurantmanager.utils.Dialog.showErrorDialog;

public class TableController {

    @FXML
    private Button addTableButton;

    @FXML
    private RadioButton availableTableRadioButton;

    @FXML
    private ComboBox<TableLocations> locationOfTableComboBox;

    @FXML
    private Spinner<Integer> numberOfSeatsSpinner;

    @FXML
    private Spinner<Integer> numberOfTableSpinner;

    @FXML
    private ComboBox<TableTypes> typeOfTableComboBox;

    @FXML
    private TableView<Table> tableTable;

    @FXML
    private TableColumn<Table, String> tableAvailableColumn;

    @FXML
    private TableColumn<Table, String> typeOfTableColumn;

    @FXML
    private TableColumn<Table, String> numberOfTableColumn;

    @FXML
    private TableColumn<Table, String> numberOfSeatsColumn;

    @FXML
    private TableColumn<Table, String> locationOfTableColumn;

    @FXML
    void addTable(ActionEvent event) {
        try {
            ObservableList<Table> tableContents = tableTable.getItems();
            Boolean tableAvailable = availableTableRadioButton.isSelected();
            TableLocations tableLocation = locationOfTableComboBox.getValue();
            Integer numberOfSeats = numberOfSeatsSpinner.getValue();
            Integer numberOfTable = numberOfTableSpinner.getValue();
            TableTypes typeOfTable = typeOfTableComboBox.getValue();
            if (tableLocation == null || numberOfSeats == null || numberOfTable == null || typeOfTable == null)
                throw new NullValueException("");
            tableContents.stream().filter(table -> Objects.equals(table.getId(), numberOfTable)).findFirst().ifPresent(x->{throw new ExistingValueException("Stol");});
            Table tableToAdd = new Table(numberOfTable, typeOfTable, numberOfSeats, tableAvailable, tableLocation);

            tableContents.add(tableToAdd);
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
        tableAvailableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTableAvailable().toString()));
        typeOfTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().name()));
        locationOfTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTableLocation().name()));
        numberOfSeatsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumberOfSeats().toString()));
    }


}


