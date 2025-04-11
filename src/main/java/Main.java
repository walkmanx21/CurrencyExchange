import dao.CurrencyDao;
import util.ConnectionManager;

public class Main {
    public static void main(String[] args) {
        try {

        } finally {
            ConnectionManager.closePool();
        }
    }
}
