package bencit.com.restaurantmanager.entities;
import bencit.com.restaurantmanager.enums.PaymentMethods;
import java.io.Serializable;
import java.util.List;
public class Order implements Serializable {
    private Integer id;
    private Integer tableId;
    private PaymentMethods paymentMethod;
    private List<Item> itemsOfOrder;
    private Boolean orderPaid=false;

    public Order(Integer id, Integer tableId, PaymentMethods paymentMethod, Boolean orderPaid, List<Item> itemsOfOrder) {
        this.id = id;
        this.tableId = tableId;
        this.paymentMethod = paymentMethod;
        this.orderPaid = orderPaid;
        this.itemsOfOrder = itemsOfOrder;
    }
    public Order(){}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getTableId() {
        return tableId;
    }
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public List<Item> getItemsOfOrder() {
        return itemsOfOrder;
    }
    public void setItemsOfOrder(List<Item> itemsOfOrder) {
        this.itemsOfOrder = itemsOfOrder;
    }
    public Boolean getOrderPaid() {
        return orderPaid;
    }
    public void setOrderPaid(Boolean orderPaid) {
        this.orderPaid = orderPaid;
    }
}
