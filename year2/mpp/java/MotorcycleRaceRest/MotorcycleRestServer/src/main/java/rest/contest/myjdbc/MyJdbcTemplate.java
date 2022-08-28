package rest.contest.myjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyJdbcTemplate {
    @Autowired
    private DatabaseProperties properties;

    private final Connection connection;

    public MyJdbcTemplate() {
        try {
            connection = DriverManager.getConnection(properties.url);
        }
        catch (Exception exception) {
            System.out.println("creating the db connection failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    public <T> List<T> query(String sql, ResultSetExtractor<T> extractor) {
        List<T> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    list.add(extractor.extractData(resultSet));
                }
            }
        }
        catch (Exception exception) {
            System.out.println("query failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
        return list;
    }

    public int insert(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet != null && resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println(id);
                    return id;
                }
                return 0;
            }
        }
        catch (SQLException exception) {
            System.out.println("insert failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    public int update(String sql) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("update failed " + e.getMessage());
            throw new MyJdbcException(e);
        }
    }
}