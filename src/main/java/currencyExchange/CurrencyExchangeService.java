package currencyExchange;

public class CurrencyExchangeService {
    private static final CurrencyExchangeService INSTANCE = new CurrencyExchangeService();

    private CurrencyExchangeService(){
    }

    public CurrencyExchangeService getInstance() {
        return INSTANCE;
    }
}
