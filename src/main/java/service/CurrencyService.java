package service;

import dao.CurrencyDao;
import dto.CurrencyDto;
import entity.Currency;
import exception.CurrencyAlreadyExistsException;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    public Currency findOneCurrency (CurrencyDto currencyDto) {
        Currency currency = createCurrency(currencyDto);
        return currencyDao.findEntity(currency);
    }

    public Currency insertCurrency (CurrencyDto currencyDto) throws CurrencyAlreadyExistsException {
        Currency currency = createCurrency(currencyDto);
        return currencyDao.insertNewCurrency(currency);
    }

    private Currency createCurrency (CurrencyDto currencyDto) {
        return new Currency(
                null,
                currencyDto.getCode(),
                currencyDto.getFullName(),
                currencyDto.getSign()
        );
    }

    private CurrencyService(){
    }
}
