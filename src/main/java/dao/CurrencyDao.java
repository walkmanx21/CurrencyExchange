package dao;

import entity.Currency;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    public static final String FIND_ALL_CURRENCIES_SQL = """
            SELECT id, code, fullname, sign
            FROM Currencies
            """;

    private static final String FIND_CURRENCY_BY_CODE_SQL = """
            SELECT ID, Code, FullName, Sign
            FROM Currencies
            WHERE Code = ?
            """;

    private CurrencyDao() {
    }


    public List<Currency> findAllEntities() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CURRENCIES_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Currency> findEntity(Currency entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_CODE_SQL)) {
            preparedStatement.setString(1, entity.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            List <Currency> list = new ArrayList<>();
            Currency currencyFull = null;
            if (resultSet.next()) {
                currencyFull = buildCurrency(resultSet);
            }
            return Optional.ofNullable(currencyFull);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Object create(Object entity) {
        return null;
    }


    public void update(Object entity) {

    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
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
