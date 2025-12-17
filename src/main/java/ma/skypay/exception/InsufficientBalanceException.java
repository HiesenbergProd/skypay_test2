package ma.skypay.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(int userId) {
        super("Insufficient balance for user: " + userId);
    }
}
