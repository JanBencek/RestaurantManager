package bencit.com.restaurantmanager.records;
import bencit.com.restaurantmanager.interfaces.Payable;

public record CardPayment() implements Payable {
    @Override
    public void processPayment() {

    }
}
