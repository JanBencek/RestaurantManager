package bencit.com.restaurantmanager.exceptions;

public class ExistingValueException extends RuntimeException {
    public ExistingValueException(String message) {
        String msg=message+" s ovim imenom/ID-em već postoji u bazi podataka.";
        super(msg);
    }
}
