package Dao;

import Entity.Currency;
import lombok.NoArgsConstructor;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class CurrencyDao implements Dao {

    private static final String FIND_CURRENCY_BY_CODE = """
            SELECT ID, Code, FullName, Sign
            FROM Currencies
            WHERE Code = ?
            """;

    @Override
    public List findAllEntities() {
        return List.of();
    }

    @Override
    public Optional<Currency> findEntity(Object code) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_CODE)) {
            preparedStatement.setString(1, (String)code);
            ResultSet resultSet = preparedStatement.executeQuery();
            List <Currency> list = new ArrayList<>();
            Currency currency = null;
            if (resultSet.next()) {
                currency = new Currency(
                        resultSet.getInt("ID"),
                        resultSet.getString("Code"),
                        resultSet.getString("FullName"),
                        resultSet.getString("Sign")
                );
            }
            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object create(Object entity) {
        return null;
    }

    @Override
    public void update(Object entity) {

    }
}
