package ua.kazmirchuk;

import java.io.IOException;

public class PaymentInfrastructureException extends IOException {
    public PaymentInfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}