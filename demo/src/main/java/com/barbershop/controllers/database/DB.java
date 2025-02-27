package com.barbershop.controllers.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niema Alaoui Mdaghri
 * This class contains only static public methods for database management, it can be called from anywhere!
 */
public class DB {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * CREATE a DATABASE.
     *
     * @param dbName Database name
     */
    public static void createDB(String dbName) {
        try {
            Connection connection = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "CREATE DATABASE IF NOT EXISTS " + dbName.toLowerCase() + ";";
            statement.executeUpdate(query);
            System.out.println("Database " + dbName.toLowerCase() + " created successfully.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
        }
    }

    /**
     * CREATE a TABLE in a database
     *
     * @param dbName     database name
     * @param tableName  table name
     * @param attributes table attributes
     */
    public static void createTable(String dbName, String tableName, String attributes) {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName.toLowerCase() + " (" + attributes + ");";
        try {
            String jdbcUrl = MYSQL_URL + dbName.toLowerCase();
            Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table " + tableName.toLowerCase() + " created successfully.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
        }
    }

    /**
     * INSERT a ROW in a specific table in a database.
     *
     * @param dbName    database name
     * @param tableName table name
     * @param values    values to be inserted
     * @return last inserted id
     */
    public static int insertRow(String dbName, String tableName, String values) {
        String query = "INSERT INTO " + tableName.toLowerCase() + " VALUES " + values + ";";
        int id = 0;
        try {
            String jdbcUrl = MYSQL_URL + dbName.toLowerCase();
            Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connection.close();
            return id;
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
            return -1;
        }
    }

    /**
     * UPDATE a ROW in a specific table in a database.
     *
     * @param dbName     database name
     * @param tableName  table name
     * @param setClause  new values
     * @param conditions where the values will be updated condition
     */
    public static void updateRow(String dbName, String tableName, String setClause, String conditions) {
        String query = "UPDATE " + tableName.toLowerCase() + " SET " + setClause + " WHERE " + conditions + ";";
        try {
            String jdbcUrl = MYSQL_URL + dbName.toLowerCase();
            Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
        }
    }

    /**
     * DELETE a ROW from a specific table in a database.
     *
     * @param dbName     database name
     * @param tableName  table name
     * @param conditions conditions to identify the row(s) to delete
     */
    public static void deleteRow(String dbName, String tableName, String conditions) {
        String query = "DELETE FROM " + tableName.toLowerCase() + " WHERE " + conditions + ";";
        try {
            String jdbcUrl = MYSQL_URL + dbName.toLowerCase();
            Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
        }
    }

    /**
     * SELECT values from a specific table in a database.
     *
     * @param dbName     database name
     * @param tableName  table name
     * @param attributes attributes to be selected from the table
     * @param conditions where condition
     * @return List of values selected
     */
    public static List<List<String>> selectRow(String dbName, String tableName, String attributes, String conditions) {
        List<List<String>> resultList = new ArrayList<>();
        String query = "SELECT " + attributes + " FROM " + tableName.toLowerCase() + " " + conditions + ";";
        try {
            String jdbcUrl = MYSQL_URL + dbName.toLowerCase();
            Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                resultList.add(row);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
        }
        return resultList;
    }
}