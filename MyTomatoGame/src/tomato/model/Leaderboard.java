package tomato.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
	private Connection connection; 

    public Leaderboard(Connection connection) {
    	this.connection = connection;
    }


    // Method to retrieve the top N players for a specific difficulty level
    public Map<String, Integer> getTopPlayers(int n, String difficulty) {
        Map<String, Integer> topPlayers = new HashMap<>();
        String query = "SELECT username, " + difficulty + " FROM userdata ORDER BY " + difficulty + " DESC LIMIT ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, n);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt(difficulty);
                topPlayers.put(username, score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topPlayers;
    }
       
}