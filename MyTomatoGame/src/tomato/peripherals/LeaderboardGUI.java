package tomato.peripherals;

import javax.swing.*;

import tomato.database.DatabaseManager;
import tomato.model.Leaderboard;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class LeaderboardGUI extends JFrame {

	private Leaderboard leaderboard;
	private Connection connection;

    private static final long serialVersionUID = 1L;

	public LeaderboardGUI(Connection connection) {
		this.connection = connection;
		this.leaderboard = new Leaderboard(connection);
        initialize();
    }

    private void initialize() {
        setTitle("Leaderboard");
        setSize(796, 447);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel to hold the leaderboard data
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BorderLayout());

        // Add a label for the title
        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardPanel.add(titleLabel, BorderLayout.NORTH);

        // Add a JTextArea to display leaderboard data
        JTextArea leaderboardTextArea = new JTextArea();
        leaderboardTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardTextArea);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);

        // Populate the leaderboard data
        populateLeaderboard(leaderboardTextArea);

        // Add the leaderboard panel to the frame
        getContentPane().add(leaderboardPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    private void populateLeaderboard(JTextArea leaderboardTextArea) {
    	 Map<String, Integer> topPlayersEasy = leaderboard.getTopPlayers(5, "hs_easy");
         Map<String, Integer> topPlayersMedium = leaderboard.getTopPlayers(5, "hs_medium");
         Map<String, Integer> topPlayersHard = leaderboard.getTopPlayers(5, "hs_hard");
  
         StringBuilder leaderboardText = new StringBuilder();
         leaderboardText.append("Easy Mode:\n");
         appendTopPlayersToText(topPlayersEasy, leaderboardText);
         leaderboardText.append("\nMedium Mode:\n");
         appendTopPlayersToText(topPlayersMedium, leaderboardText);
         leaderboardText.append("\nHard Mode:\n");
         appendTopPlayersToText(topPlayersHard, leaderboardText);

         leaderboardTextArea.setText(leaderboardText.toString());
    
    }
    private void appendTopPlayersToText(Map<String, Integer> topPlayers, StringBuilder text) {
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topPlayers.entrySet()) {
            text.append(rank++).append(". ").append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException here, such as displaying an error message
            return; // Exit the method if there's an exception
        }
        
        final Connection finalConnection = connection; // effectively final variable

        EventQueue.invokeLater(() -> {
            LeaderboardGUI leaderboardGUI = new LeaderboardGUI(finalConnection);
            leaderboardGUI.setVisible(true);
        });
    }
    


}