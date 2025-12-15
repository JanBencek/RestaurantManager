package bencit.com.restaurantmanager.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class EmployeeController {

    @FXML
    private Button addEmployeeButton;

    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private TableColumn<?, ?> dateOfBirthOfEmployeeColumn;

    @FXML
    private TextField descriptionOfRoleOfEmployeeTextField;

    @FXML
    private TableColumn<?, ?> lastNameOfEmployeeColumn;

    @FXML
    private TextField lastNameOfEmployeeTextField;

    @FXML
    private TableColumn<?, ?> nameOfEmployeeColumn;

    @FXML
    private TextField nameOfEmployeeTextField;

    @FXML
    private TableColumn<?, ?> phoneNumberOfEmployeeColumn;

    @FXML
    private TextField phoneNumberOfEmployeeTextField;

    @FXML
    private TableColumn<?, ?> roleOfEmployeeColumn;

    @FXML
    private ComboBox<?> roleOfEmployeeComboBox;

    @FXML
    void addEmployee(ActionEvent event) {

    }

}
