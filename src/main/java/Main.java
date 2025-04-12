import dao.CurrencyDao;
import dto.CurrencyDto;
import service.CurrencyService;
import util.ConnectionManager;

public class Main {
    private static final CurrencyService currencyService = CurrencyService.getInstance();

    public static void main(String[] args) {
        System.out.println(currencyService.findAllCurrencies().toString());
    }
}
