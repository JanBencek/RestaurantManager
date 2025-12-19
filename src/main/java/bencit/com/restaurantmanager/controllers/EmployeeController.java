package bencit.com.restaurantmanager.controllers;
import bencit.com.restaurantmanager.Program;
import bencit.com.restaurantmanager.entities.Employee;
import bencit.com.restaurantmanager.enums.EmployeeRoles;
import bencit.com.restaurantmanager.exceptions.NullValueException;
import bencit.com.restaurantmanager.state.AppState;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    Integer employeeIdTracker=1;
    @FXML private TableView<Employee> employeesTable;
    @FXML private TextField pathToFileTextField;
    @FXML private DatePicker dateOfBirthDatePicker;
    @FXML private TableColumn<Employee, String> dateOfBirthOfEmployeeColumn;
    @FXML private TextField descriptionOfRoleOfEmployeeTextField;
    @FXML private TableColumn<Employee, String> lastNameOfEmployeeColumn;
    @FXML private TextField lastNameOfEmployeeTextField;
    @FXML private TableColumn<Employee, String> nameOfEmployeeColumn;
    @FXML private TextField nameOfEmployeeTextField;
    @FXML private TableColumn<Employee, String> phoneNumberOfEmployeeColumn;
    @FXML private TextField phoneNumberOfEmployeeTextField;
    @FXML private TableColumn<Employee, String> roleOfEmployeeColumn;
    @FXML private ComboBox<EmployeeRoles> roleOfEmployeeComboBox;
    ObservableList<Employee> availableEmployees = AppState.availableEmployees();
    String dateOfBirthFormatted;
    @FXML
    void addEmployee() {
        String name = nameOfEmployeeTextField.getText();
        String lastName = lastNameOfEmployeeTextField.getText();
        String phoneNumber = phoneNumberOfEmployeeTextField.getText();
        String description = descriptionOfRoleOfEmployeeTextField.getText();
        EmployeeRoles role = roleOfEmployeeComboBox.getValue();
        if(name==null || lastName==null || phoneNumber==null || description==null || dateOfBirthFormatted==null || role==null) throw new NullValueException("");
        availableEmployees.add(new Employee(employeeIdTracker, name, lastName, phoneNumber, dateOfBirthFormatted, role, description));
        employeeIdTracker++;
        nameOfEmployeeTextField.clear();
        lastNameOfEmployeeTextField.clear();
        phoneNumberOfEmployeeTextField.clear();
        descriptionOfRoleOfEmployeeTextField.clear();
        dateOfBirthDatePicker.setValue(null);
    }

    public void initialize(){
        roleOfEmployeeComboBox.getItems().addAll(EmployeeRoles.values());
        roleOfEmployeeComboBox.getSelectionModel().selectFirst();
        employeesTable.setItems(AppState.availableEmployees());
        nameOfEmployeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        lastNameOfEmployeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        phoneNumberOfEmployeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        dateOfBirthOfEmployeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        roleOfEmployeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().name()));
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
            jsonb.toJson(new ArrayList<>(availableEmployees), new FileWriter(file, StandardCharsets.UTF_8));
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

            // Define the type for a List of Employee objects
            List<Employee> loadedEmployees = jsonb.fromJson(reader, new ArrayList<Employee>(){}.getClass().getGenericSuperclass());

            if (loadedEmployees != null) {
                availableEmployees.setAll(loadedEmployees);
                employeeIdTracker = availableEmployees.stream().mapToInt(Employee::getId).max().orElse(0) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearFile() {
        availableEmployees.clear();
        employeeIdTracker = 1;
        if (file != null && file.exists()) {
            try (FileWriter writer = new FileWriter(file, false)) {
                writer.write("[]"); // Initialize with an empty JSON array
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML void getDate(){
        LocalDate dateOfBirth = dateOfBirthDatePicker.getValue();
        dateOfBirthFormatted = dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
