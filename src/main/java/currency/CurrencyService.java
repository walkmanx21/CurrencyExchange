package currency;

import currency.dto.CurrencyRequestDto;
import currency.dto.CurrencyResponseDto;
import exception.CurrencyAlreadyExistsException;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private CurrencyService(){
    }

    public Currency findOneCurrency (CurrencyRequestDto currencyRequestDto) {
        Currency currency = createCurrency(currencyRequestDto);
        return currencyDao.findCurrency(currency);
    }

    public Currency insertCurrency (CurrencyRequestDto currencyRequestDto) throws CurrencyAlreadyExistsException {
        Currency currency = createCurrency(currencyRequestDto);
        return currencyDao.insertNewCurrency(currency);
    }

    private Currency createCurrency (CurrencyRequestDto currencyRequestDto) {
        return new Currency(
                null,
                currencyRequestDto.getCode(),
                currencyRequestDto.getFullName(),
                currencyRequestDto.getSign()
        );
    }



    private CurrencyResponseDto buildCurrencyResponseDto (Currency currency) {
        return new CurrencyResponseDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }
}
