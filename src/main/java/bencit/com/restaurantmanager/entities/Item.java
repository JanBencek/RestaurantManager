package bencit.com.restaurantmanager.entities;
import bencit.com.restaurantmanager.enums.Currencies;
import bencit.com.restaurantmanager.enums.ItemSizes;
import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {
    private Integer id;
    private String itemName;
    private String itemDescription;
    private BigDecimal itemPrice;
    private Boolean available;
    private Currencies currency;
    private ItemSizes itemSize;
    private Integer quantity=1;
    public Item(Integer id, String name,String itemDescription, BigDecimal price, ItemSizes itemSize, Boolean available, Currencies currency){
        this.id = id;
        this.itemName = name;
        this.itemDescription = itemDescription;
        this.itemPrice = price;
        this.itemSize = itemSize;
        this.available = available;
        this.currency = currency;
    }
    public Item(){}
    public ItemSizes getItemSize() {
        return itemSize;
    }
    public void setItemSize(ItemSizes itemSize) {
        this.itemSize = itemSize;
    }
    public Currencies getCurrency() {
        return currency;
    }
    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }
    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public BigDecimal getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
