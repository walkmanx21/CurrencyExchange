package currencyExchange;

import currency.Currency;
import currencyExchange.dto.ExchangeRequestDto;
import rate.Rate;
import util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeDao {

    private static final ExchangeDao INSTANCE = new ExchangeDao();

    private ExchangeDao (){
    }

    public static ExchangeDao getInstance() {
        return INSTANCE;
    }

    private static final String GET_ONLY_RATE_FIELD = """
            SELECT ExchangeRates.Rate
            FROM ExchangeRates
            WHERE BaseCurrencyId = (SELECT id
                                    FROM Currencies
                                    WHERE Code = ?)
              AND TargetCurrencyId = (SELECT id
                                      FROM Currencies
                                      WHERE Code = ?)
            """;

    private static final String FIND_CURRENCY_BY_CODE_SQL = """
            SELECT ID, Code, FullName, Sign
            FROM Currencies
            WHERE Code = ?
            """;

    private static final String FIND_ONE_EXCHANGE_RATE_SQL = """
            SELECT exchangeRate_id, baseCurrencyCode, Currencies.Code as targetCurrencyCode, first_join_table.Rate
            FROM (SELECT ExchangeRates.id as exchangeRate_id, Currencies.Code as baseCurrencyCode, TargetCurrencyId, Rate
                    FROM ExchangeRates
                    JOIN Currencies
                    ON ExchangeRates.BaseCurrencyId = Currencies.ID) AS first_join_table
            JOIN Currencies
            ON first_join_table.TargetCurrencyId = Currencies.ID
            WHERE baseCurrencyCode = ? AND targetCurrencyCode = ?;
            """;

    public BigDecimal getOnlyRateField (String baseCurrencyCode, String targetCurrencyCode) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ONLY_RATE_FIELD)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            BigDecimal rate = null;
            if (resultSet.next()) {
                rate = resultSet.getBigDecimal(1);
            }
            return rate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency findCurrency (String code) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_CODE_SQL)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currency currencyFull = null;
            if (resultSet.next()) {
                currencyFull = buildCurrency(resultSet);
            }
            return currencyFull;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findOneRate(String baseCurrencyCode, String targetCurrencyCode) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ONE_EXCHANGE_RATE_SQL)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            int rateId = 0;
            if (resultSet.next()) {
                rateId = resultSet.getInt("exchangeRate_id");
            }
            return rateId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Currency buildCurrency (ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getInt("ID"),
                resultSet.getString("Code"),
                resultSet.getString("FullName"),
                resultSet.getString("Sign")
        );
    }

}
