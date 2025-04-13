package exception;

import java.sql.SQLException;

public class CurrencyExistException extends SQLException {
    public CurrencyExistException(String message) {
        super(message);
    }
}
