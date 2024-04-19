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

    public DatabaseManager()throws SQLException {
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

    public void registerUser(String username, String email, String password, String confirmPassword ) throws SQLException {
        // Check if the username already exists in the database
        String checkQuery = "SELECT COUNT(*) FROM userdata WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setString(1, username);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count > 0) {

                } else {
                    // Username does not exist, proceed with registration
                    
                    // Check if password and confirm password match
                    if (!password.equals(confirmPassword)) {
                        throw new SQLException("Passwords do not match");
                    }
                    
                    // Insert user data into the database
                    String insertQuery = "INSERT INTO userdata (username, email, password, hs_easy, hs_medium, hs_hard) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, username);
                        insertStatement.setString(2, email);
                        insertStatement.setString(3, password);
                        // Set initial high scores to 0 for all difficulty levels
                        insertStatement.setInt(4, 0);
                        insertStatement.setInt(5, 0);
                        insertStatement.setInt(6, 0);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } 
    }
    
    public void updateHighScore(String username, int score, String difficultyLevel) throws SQLException {
        // Update the high score for the user based on the difficulty level
        String updateQuery = "UPDATE userdata SET " + getHighScoreColumn(difficultyLevel) + " = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, score);
            updateStatement.setString(2, username);
            updateStatement.executeUpdate();
        }
    }

    private String getHighScoreColumn(String difficultyLevel) {
        switch (difficultyLevel) {
            case "Easy":
                return "hs_easy";
            case "Medium":
                return "hs_medium";
            case "Hard":
                return "hs_hard";
            default:
                throw new IllegalArgumentException("Invalid difficulty level");
        }
    }

    public void updateLeaderboard() {
        try {
            // Retrieve high scores of all players from the database for each difficulty level
            int[][] highScores = new int[3][2]; // Array to store high scores for each difficulty level [difficulty][playerIndex]
            String[] players = {"A", "B"}; // List of players (you can retrieve this dynamically from the database)

            for (int i = 0; i < players.length; i++) {
                String player = players[i];
                // Retrieve high scores for each difficulty level for the current player
                int[] playerHighScores = getPlayerHighScores(player);
                for (int j = 0; j < playerHighScores.length; j++) {
                    // Update the high score if it's higher than the current high score for the corresponding difficulty level
                    if (playerHighScores[j] > highScores[j][1]) {
                        highScores[j][0] = i; // Store the index of the player
                        highScores[j][1] = playerHighScores[j]; // Store the high score
                    }
                }
            }

            // Display the leaderboard
            System.out.println("Easy Mode:");
            displayLeaderboard("Easy", highScores[0]);
            System.out.println("\nMedium Mode:");
            displayLeaderboard("Medium", highScores[1]);
            System.out.println("\nHard Mode:");
            displayLeaderboard("Hard", highScores[2]);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException, such as displaying an error message
        }
    }

    // Method to retrieve high scores for a specific player in each difficulty level
    public int[] getPlayerHighScores(String player) throws SQLException {
    	 int[] highScores = new int[3]; 
    	try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT hs_easy, hs_medium, hs_hard FROM userdata WHERE username = ?")) {
               statement.setString(1, player);
               try (ResultSet resultSet = statement.executeQuery()) {
                   if (resultSet.next()) {
                       highScores[0] = resultSet.getInt("hs_easy");
                       highScores[1] = resultSet.getInt("hs_medium");
                       highScores[2] = resultSet.getInt("hs_hard");
                   }
               }
           }
    	return highScores;
        // Implementation to retrieve high scores from the database for the specified player
    }

    // Method to display the leaderboard for a specific difficulty level
    private void displayLeaderboard(String difficulty, int[] leaderboardEntry) {
        // Implementation to display the leaderboard entry for the specified difficulty level
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
    
    // Method to update the high score for a user in the 'hs_easy' column
    public void updateHighScoreEasy(String username, int score) throws SQLException {
        String query = "UPDATE userdata SET hs_easy = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, score);
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }

    // Method to update the high score for a user in the 'hs_medium' column
    public void updateHighScoreMedium(String username, int score) throws SQLException {
        String query = "UPDATE userdata SET hs_medium = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, score);
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }
   

    // Method to update the high score for a user in the 'hs_hard' column
    public void updateHighScoreHard(String username, int score) throws SQLException {
        String query = "UPDATE userdata SET hs_hard = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, score);
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }
    
    
}