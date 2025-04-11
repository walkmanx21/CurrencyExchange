package Exception;

import java.sql.SQLException;

public class ConnectionException extends SQLException {
    public ConnectionException(Throwable throwable) {
        super(throwable);
    }
}
