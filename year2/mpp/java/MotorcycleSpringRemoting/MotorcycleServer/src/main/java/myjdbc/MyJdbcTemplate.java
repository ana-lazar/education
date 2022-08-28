package myjdbc;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyJdbcTemplate {
    private Connection connection;

    public MyJdbcTemplate(String config) {
        Properties properties = loadProperties(config);
        try {
            System.out.println("creating a db connection ...");
            connection = DriverManager.getConnection(properties.getProperty("jdbc.url"));
        }
        catch (Exception exception) {
            System.out.println("creating the db connection failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    public MyJdbcTemplate() {
    }

    public void setConfig(String config) {
        Properties properties = loadProperties(config);
        try {
            System.out.println("creating a db connection ...");
            connection = DriverManager.getConnection(properties.getProperty("jdbc.url"));
        }
        catch (Exception exception) {
            System.out.println("creating the db connection failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    private Properties loadProperties(String config) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(config));
        } catch (IOException exception) {
            throw new MyJdbcException("db config file corrupted");
        }
        return properties;
    }

    public void close() {
        try {
            System.out.println("closing the DB connection");
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println("closing the DB connection failed " + exception.getMessage());
            throw new MyJdbcException(exception);
        }
    }

    public <T> List<T> query(String sql, ResultSetExtractor<T> extractor) {
        System.out.println("executing query " + sql);
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
        System.out.println("query successful " + list);
        return list;
    }

    public int insert(String sql) {
        System.out.println("insert " + sql);
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
        System.out.println("update " + sql);
        try (Statement statement = connection.createStatement()) {
            int lines = statement.executeUpdate(sql);
            System.out.println("update successful " + lines);
            return lines;
        }
        catch (SQLException e) {
            System.out.println("update failed " + e.getMessage());
            throw new MyJdbcException(e);
        }
    }
}