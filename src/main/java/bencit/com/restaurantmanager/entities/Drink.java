package bencit.com.restaurantmanager.entities;

import bencit.com.restaurantmanager.enums.Currencies;
import bencit.com.restaurantmanager.enums.DrinkSizes;
import bencit.com.restaurantmanager.enums.ItemTypes;

import java.math.BigDecimal;

public class Drink extends Item{
    private DrinkSizes size;
    public Drink(int id, String drinkName,String drinkDescription, BigDecimal drinkPrice, ItemTypes type, DrinkSizes size, Boolean available, Currencies currency){
        super(id, drinkName,drinkDescription, drinkPrice, type, available, currency);
        this.size = size;
    }
    public DrinkSizes getSize() {
        return size;
    }
    public void setSize(DrinkSizes size) {
        this.size = size;
    }
}
