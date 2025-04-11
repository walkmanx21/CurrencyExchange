import Dao.CurrencyDao;
import Entity.Currency;
import util.ConnectionManager;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            CurrencyDao currencyDao = new CurrencyDao();
            System.out.println(currencyDao.findAllEntities());
        } finally {
            ConnectionManager.closePool();
        }
    }
}
