package tomato.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Handles database operations including user authentication and data retrieval.

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mytomatogameuserdata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root@123";
    
    private static Connection connection;
    
    // Establish database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection failure
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection closure failure
        }
    }

    // Method to execute SELECT queries
    public ResultSet executeQuery(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    // Method to execute INSERT, UPDATE, or DELETE queries
    public int executeUpdate(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeUpdate();
    }

    public void registerUser(String username, String email, String password, String confirmPassword) throws SQLException {
        // Check if the username already exists in the database
        String checkQuery = "SELECT COUNT(*) FROM userdata WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setString(1, username);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count > 0) {
                    // Username already exists, throw an error or handle accordingly
                    throw new SQLException("Username already exists");
                } else {
                    // Username does not exist, proceed with registration
                    
                    // Check if password and confirm password match
                    if (!password.equals(confirmPassword)) {
                        throw new SQLException("Passwords do not match");
                    }
                    
                    // Insert user data into the database
                    String insertQuery = "INSERT INTO userdata (username, email, password) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, username);
                        insertStatement.setString(2, email);
                        insertStatement.setString(3, password);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } 
    }


    
 // Authenticate user against the database
    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM userdata WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If result set has next, user is authenticated
            }
        }
    }
}
