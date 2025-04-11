package service;

import dao.CurrencyDao;
import dto.CurrencyDto;

import java.util.List;

import static java.util.stream.Collectors.*;

public class CurrencyService {

    private static final CurrencyService INSTANCE = new CurrencyService();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    public CurrencyService getInstance() {
        return INSTANCE;
    }

    public List<CurrencyDto> findAllCurrencies() {
        return currencyDao.findAllEntities().stream().
                map(currency -> new CurrencyDto(
                        currency.getCode()
                )).collect(toList());
    }

    private CurrencyService() {
    }


}
