package currency;

import exception.CurrencyAlreadyExistsException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private CurrencyDao() {
    }

    public static final String FIND_ALL_CURRENCIES_SQL = """
            SELECT id, code, fullname, sign
            FROM Currencies
            """;

    private static final String FIND_CURRENCY_BY_CODE_SQL = """
            SELECT ID, Code, FullName, Sign
            FROM Currencies
            WHERE Code = ?
            """;

    public static final String INSERT_NEW_CURRENCY_SQL = """
            INSERT INTO Currencies (Code, FullName, Sign) 
            VALUES (?, ?, ?)
            """;


    public List<Currency> findAllCurrencies() {
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


    public Currency findCurrency(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_CODE_SQL)) {
            preparedStatement.setString(1, currency.getCode());
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


    public Currency insertNewCurrency(Currency currency) throws CurrencyAlreadyExistsException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_CURRENCY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                currency.setId(generatedKeys.getInt(1));
            }
            return currency;
        } catch (SQLException e) {
            throw new CurrencyAlreadyExistsException(e);
        }
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
