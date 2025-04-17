package rate;

import currency.Currency;
import exception.AnyErrorException;
import exception.CurrencyNotFoundException;
import exception.ExchangeRateAlreadyExistsException;
import exception.ExchangeRateNotFoundException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateDao {

    private static final RateDao INSTANCE = new RateDao();

    private RateDao() {
    }

    public static RateDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL_EXCHANGE_RATES_SQL = """
            SELECT exchangeRate_id, baseCurrencyCode, Currencies.Code as targetCurrencyCode, first_join_table.Rate
            FROM (SELECT ExchangeRates.id as exchangeRate_id, Currencies.Code as baseCurrencyCode, TargetCurrencyId, Rate
                    FROM ExchangeRates
                    JOIN Currencies
                    ON ExchangeRates.BaseCurrencyId = Currencies.ID) AS first_join_table
            JOIN Currencies
            ON first_join_table.TargetCurrencyId = Currencies.ID;
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

    private static final String INSERT_NEW_EXCHANGE_RATE_SQL = """
            INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
            VALUES (
                    (SELECT id
                     FROM Currencies
                     WHERE Code = ?),
                    (SELECT id
                     FROM Currencies
                     WHERE Code = ?),
                    ?
                   );
            """;

    private static final String UPDATE_EXCHANGE_RATE_SQL = """
            UPDATE ExchangeRates
            SET Rate = ?
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


    public List<Rate> findAllRates() throws AnyErrorException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_EXCHANGE_RATES_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Rate> rates = new ArrayList<>();
            while (resultSet.next()) {
                rates.add(buildRate(resultSet));
            }
            return rates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable throwable) {
            throw new AnyErrorException();
        }
    }

    public Rate findOneRate(Rate rate) throws AnyErrorException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ONE_EXCHANGE_RATE_SQL)) {
            preparedStatement.setString(1, rate.getBaseCurrencyCode());
            preparedStatement.setString(2, rate.getTargetCurrencyCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rate = buildRate(resultSet);
            }
            return rate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable throwable) {
            throw new AnyErrorException();
        }
    }

    public Rate insertNewExchangeRate(Rate rate) throws CurrencyNotFoundException, ExchangeRateAlreadyExistsException, AnyErrorException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_EXCHANGE_RATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, rate.getBaseCurrencyCode());
            preparedStatement.setString(2, rate.getTargetCurrencyCode());
            preparedStatement.setBigDecimal(3, rate.getRate());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                rate.setId(generatedKeys.getInt(1));
            }
            return rate;
        } catch (SQLException e) {
            if (e.getMessage().contains("SQLITE_CONSTRAINT_NOTNULL")) {
                throw new CurrencyNotFoundException(e);
            }
            if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                throw new ExchangeRateAlreadyExistsException(e);
            }
            return null;
        }
        catch (Throwable throwable) {
            throw new AnyErrorException();
        }
    }

    public Rate updateExchangeRate (Rate rate) throws ExchangeRateNotFoundException, AnyErrorException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXCHANGE_RATE_SQL)) {
            preparedStatement.setBigDecimal(1, rate.getRate());
            preparedStatement.setString(2, rate.getBaseCurrencyCode());
            preparedStatement.setString(3, rate.getTargetCurrencyCode());
            preparedStatement.executeUpdate();
            rate = findOneRate(rate);
            if (rate.getId() == null) {
                throw new ExchangeRateNotFoundException();
            }
            return rate;
        } catch (SQLException | ExchangeRateNotFoundException e) {
            throw new ExchangeRateNotFoundException();
        } catch (Throwable throwable) {
            throw new AnyErrorException();
        }
    }

    public Currency findCurrency (String code) throws AnyErrorException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_CODE_SQL)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currency currencyFull = null;
            if (resultSet.next()) {
                currencyFull = buildCurrency(resultSet);
            }
            return currencyFull;
        } catch (Throwable throwable) {
            throw new AnyErrorException();
        }
    }

    private static Rate buildRate (ResultSet resultSet) throws SQLException {
        return new Rate(
                resultSet.getInt("exchangeRate_id"),
                resultSet.getString("baseCurrencyCode"),
                null,
                resultSet.getString("targetCurrencyCode"),
                null,
                resultSet.getBigDecimal("Rate")
        );
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
