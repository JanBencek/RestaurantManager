package bencit.com.restaurantmanager.controllers;
import bencit.com.restaurantmanager.entities.Item;
import bencit.com.restaurantmanager.entities.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderContoller {

    @FXML
    private Button addItemsToOrderButton;

    @FXML
    private TableColumn<Order, ArrayList<Item>> itemsOfOrderColumn;

    @FXML
    private TableColumn<Order, Integer> numberOfOrderTableColumn;

    @FXML
    private ComboBox<?> numberOfOrderTableComboBox;

    @FXML
    private Button orderAddButton;

    @FXML
    private TableColumn<Order, Boolean> orderPaidColumn;

    @FXML
    private RadioButton orderPaidRadioButton;

    @FXML
    private TableColumn<Order, ?> paymentMethodOfOrderColumn;

    @FXML
    private ComboBox<?> paymentMethodOfOrderComboBox;

    @FXML
    private TableColumn<Order, BigDecimal> totalPriceOfOrderColumn;

}

