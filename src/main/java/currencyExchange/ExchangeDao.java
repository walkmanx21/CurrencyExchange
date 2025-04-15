package currencyExchange;

import currencyExchange.dto.ExchangeRequestDto;

public class ExchangeDao {

    private static final ExchangeDao INSTANCE = new ExchangeDao();

    private ExchangeDao (){
    }

    public ExchangeDao getInstance() {
        return INSTANCE;
    }

}
