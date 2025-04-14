package service;

import dao.CurrencyDao;
import dto.CurrencyRequestDto;
import dto.CurrencyResponseDto;
import entity.Currency;
import exception.CurrencyAlreadyExistsException;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private CurrencyService(){
    }

    public CurrencyResponseDto findOneCurrency (CurrencyRequestDto currencyRequestDto) {
        Currency currency = createCurrency(currencyRequestDto);
        currency = currencyDao.findCurrency(currency);
        return buildCurrencyResponseDto(currency);
    }

    public CurrencyResponseDto insertCurrency (CurrencyRequestDto currencyRequestDto) throws CurrencyAlreadyExistsException {
        Currency currency = createCurrency(currencyRequestDto);
        currency = currencyDao.insertNewCurrency(currency);
        return buildCurrencyResponseDto(currency);
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
