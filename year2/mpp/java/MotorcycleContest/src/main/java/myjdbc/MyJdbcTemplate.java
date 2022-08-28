package myjdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyJdbcTemplate {
    private static final Logger LOGGER = LogManager.getLogger(MyJdbcTemplate.class.getName());
    private final Connection connection;

    public MyJdbcTemplate(String config) {
        Properties properties = loadProperties(config);
        try {
            LOGGER.info("creating a DB connection ...");
            connection = DriverManager.getConnection(properties.getProperty("jdbc.url"));
        }
        catch (Exception exception) {
            LOGGER.warn("creating the DB connection failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    private Properties loadProperties(String config) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(config));
        } catch (IOException exception) {
            throw new MyJdbcException("DB config file corrupted");
        }
        return properties;
    }

    public void close() {
        try {
            LOGGER.info("closing the DB connection");
            connection.close();
        }
        catch (SQLException exception) {
            LOGGER.warn("closing the DB connection failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    public <T> List<T> query(String sql, ResultSetExtractor<T> extractor) {
        LOGGER.traceEntry("executing query {}", sql);
        List<T> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    list.add(extractor.extractData(resultSet));
                }
            }
        }
        catch (Exception exception) {
            LOGGER.warn("query failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
        LOGGER.traceExit("query successful {}", list);
        return list;
    }

    public int insert(String sql) {
        LOGGER.traceEntry("insert {}", sql);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet != null && resultSet.next()) {
                    int id = resultSet.getInt(1);
                    LOGGER.traceExit(id);
                    return id;
                }
                return 0;
            }
        }
        catch (SQLException exception) {
            LOGGER.warn("insert failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    public int update(String sql) {
        LOGGER.traceEntry("update {}", sql);
        try (Statement statement = connection.createStatement()) {
            int lines = statement.executeUpdate(sql);
            LOGGER.traceExit("update successful {}", lines);
            return lines;
        }
        catch (SQLException e) {
            LOGGER.warn("update failed " + e.getMessage());
            throw new MyJdbcException(e);
        }
    }
}