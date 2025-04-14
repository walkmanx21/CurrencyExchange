package rate;

import currency.Currency;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RateDao {

    public static final RateDao INSTANCE = new RateDao();

    private RateDao() {
    }

    private static final String FIND_ALL_EXCHANGE_RATES_SQL = """
            SELECT id, BaseCurrencyId, TargetCurrencyId, Rate
            FROM ExchangeRates;
            """;

    public List<Currency> findAllExchangeRates() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_EXCHANGE_RATES_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(buildExchangeRate(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static Currency buildExchangeRate (ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getInt("ID"),
                resultSet.getString("Code"),
                resultSet.getString("FullName"),
                resultSet.getString("Sign")
        );
    }






}
