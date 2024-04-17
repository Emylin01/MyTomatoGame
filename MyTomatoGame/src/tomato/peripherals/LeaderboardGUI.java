package tomato.peripherals;

import javax.swing.*;
import java.awt.*;

public class LeaderboardGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeaderboardGUI() {
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
        // Here you would retrieve the leaderboard data from the Leaderboard class
        // and populate the JTextArea with the data
        // For example:
        leaderboardTextArea.setText("1. Player1 - 100\n2. Player2 - 90\n3. Player3 - 80");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LeaderboardGUI leaderboardGUI = new LeaderboardGUI();
            leaderboardGUI.setVisible(true);
        });
    }
}
