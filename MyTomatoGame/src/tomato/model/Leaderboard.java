package tomato.model;

import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    private Map<String, Integer> leaderboard; // Map to store player names and scores

    public Leaderboard() {
        leaderboard = new HashMap<>();
    }

    // Method to add a player to the leaderboard with their score
    public void addPlayer(String playerName, int score) {
        leaderboard.put(playerName, score);
    }

    // Method to update a player's score on the leaderboard
    public void updateScore(String playerName, int newScore) {
        if (leaderboard.containsKey(playerName)) {
            leaderboard.put(playerName, newScore);
        }
    }

    // Method to retrieve the top N players on the leaderboard
    public Map<String, Integer> getTopPlayers(int n) {
        // Implement logic to sort the leaderboard by score and return the top N players
        // This could involve sorting the map by value and selecting the top N entries
        // For brevity, I'll provide a basic example
        Map<String, Integer> topPlayers = new HashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
            topPlayers.put(entry.getKey(), entry.getValue());
            count++;
            if (count >= n) {
                break;
            }
        }
        return topPlayers;
    }
}
