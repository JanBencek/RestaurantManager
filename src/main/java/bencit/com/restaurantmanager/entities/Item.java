package bencit.com.restaurantmanager.entities;

import bencit.com.restaurantmanager.enums.Currencies;
import bencit.com.restaurantmanager.enums.ItemTypes;

import java.math.BigDecimal;

public class Item {
    protected Integer id;
    protected String itemName;
    protected String itemDescription;
    protected BigDecimal itemPrice;
    protected ItemTypes itemType;
    protected Boolean available;
    protected Currencies currency;
    protected Item(Integer id, String name,String itemDescription, BigDecimal price, ItemTypes type, Boolean available, Currencies currency){
        this.id = id;
        this.itemName = name;
        this.itemDescription = itemDescription;
        this.itemPrice = price;
        this.itemType = type;
        this.available = available;
        this.currency = currency;
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

    public ItemTypes getItemType() {
        return itemType;
    }

    public void setItemType(ItemTypes itemType) {
        this.itemType = itemType;
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
}
