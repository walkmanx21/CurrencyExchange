package service;

import dao.CurrencyDao;
import dto.RequestCurrencyDto;
import entity.Currency;
import servlet.OneCurrencyServlet;

public class OneCurrencyService {
    private static final OneCurrencyService INSTANCE = new OneCurrencyService();

    public static OneCurrencyService getInstance() {
        return INSTANCE;
    }

    public void createCurrency (RequestCurrencyDto requestCurrencyDto) {
        Currency currency = new Currency(
                null,
                requestCurrencyDto.getCode(),
                null,
                null
        );
        CurrencyDao currencyDao = CurrencyDao.getInstance();
        currencyDao.findEntity(currency);
    }

    private OneCurrencyService(){
    }
}
