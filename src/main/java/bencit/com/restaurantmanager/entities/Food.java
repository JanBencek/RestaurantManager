package bencit.com.restaurantmanager.entities;

import bencit.com.restaurantmanager.enums.Currencies;
import bencit.com.restaurantmanager.enums.FoodSizes;
import bencit.com.restaurantmanager.enums.ItemTypes;

import java.math.BigDecimal;

public class Food extends Item{
    private FoodSizes size;
    public Food(int id, String foodName,String foodDescription, BigDecimal foodPrice, ItemTypes type, FoodSizes size, Boolean available, Currencies currency){
        super(id, foodName, foodDescription ,foodPrice, type, available, currency);
        this.size = size;
    }


}
