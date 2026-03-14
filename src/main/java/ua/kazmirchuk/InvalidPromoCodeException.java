package ua.kazmirchuk;

public class InvalidPromoCodeException extends AppException {
    public InvalidPromoCodeException(String code) {
        super("Invalid promo code: " + code);
    }
}
