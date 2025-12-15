package bencit.com.restaurantmanager.exceptions;

public class NullValueException extends RuntimeException {

    public NullValueException(String message) {
        String msg="Niste unijeli važeću vrijednost: "+ message;
        super(msg);
    }
}
