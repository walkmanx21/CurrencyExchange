package service;

import dao.CurrencyDao;
import dto.CurrencyDto;
import entity.Currency;

public class OneCurrencyService {
    private static final OneCurrencyService INSTANCE = new OneCurrencyService();

    public static OneCurrencyService getInstance() {
        return INSTANCE;
    }

    public Currency createCurrency (CurrencyDto currencyDto) {
        Currency currency = new Currency(
                null,
                currencyDto.getCode(),
                currencyDto.getFullName(),
                currencyDto.getSign()
        );
        CurrencyDao currencyDao = CurrencyDao.getInstance();
        return currencyDao.findEntity(currency);
    }

    private OneCurrencyService(){
    }
}
