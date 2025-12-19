package bencit.com.restaurantmanager.records;

import bencit.com.restaurantmanager.enums.PaymentMethods;

import java.io.Serializable;
import java.math.BigDecimal;

public record Payment(PaymentMethods paymentMethod, BigDecimal amount) implements Serializable {

}
