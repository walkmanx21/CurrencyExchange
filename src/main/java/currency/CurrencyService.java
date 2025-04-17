package currency;

import currency.dto.CurrencyRequestDto;
import currency.dto.CurrencyResponseDto;
import exception.AnyErrorException;
import exception.CurrencyAlreadyExistsException;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private CurrencyService(){
    }

    public Currency findOneCurrency (CurrencyRequestDto currencyRequestDto) throws AnyErrorException {
        Currency currency = buildCurrency(currencyRequestDto);
        return currencyDao.findCurrency(currency);
    }

    public Currency insertCurrency (CurrencyRequestDto currencyRequestDto) throws CurrencyAlreadyExistsException, AnyErrorException {
        Currency currency = buildCurrency(currencyRequestDto);
        return currencyDao.insertNewCurrency(currency);
    }

    private Currency buildCurrency(CurrencyRequestDto currencyRequestDto) {
        return new Currency(
                currencyRequestDto.getCode(),
                currencyRequestDto.getFullName(),
                currencyRequestDto.getSign()
        );
    }

}
