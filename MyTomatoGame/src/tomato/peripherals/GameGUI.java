package tomato.peripherals;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import tomato.database.DatabaseManager;
import tomato.engine.GameEngine;

public class GameGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -107785653906635L;
	private Timer timer;
	private int timeRemaining = 20; //Initial time in seconds
	private String username;

	
	private String difficultyLevel; // Store the selected difficulty level
	private DatabaseManager databaseManager;
	private JLabel questArea = null;
	private GameEngine myGame = null;
	private BufferedImage currentGame = null;
	private JTextArea infoArea = null;
	int totalSeconds = getTotalSeconds(); // Get total seconds from your timer logic

	// Method to format the timer text as "mm:ss"
	private String formatTimerText(int timeInSeconds) {
	    int minutes = timeInSeconds / 60;
	    int seconds = timeInSeconds % 60;
	    return String.format("%02d:%02d", minutes, seconds);
	}


// * Initializes the game. 
	private void initGame(String username) {
		setSize(813, 671);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("What is the missing value?");
		JPanel panel = new JPanel();

		myGame = new GameEngine(username, difficultyLevel);
		currentGame = myGame.nextGame();
		panel.setLayout(null);
		
		
		//Info Area
		JScrollPane infoPane = new JScrollPane();
		infoPane.setBounds(148, 11, 510, 45);
		panel.add(infoPane);
		
		infoArea = new JTextArea(3, 40);
		infoArea.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 23));
		infoPane.setColumnHeaderView(infoArea);
		infoArea.setEditable(true);

		ImageIcon ii = new ImageIcon(currentGame);
		
		
	    //Game Area
		JScrollPane questPane = new JScrollPane();
		questPane.setBounds(62, 86, 675, 377);
		panel.add(questPane);
		questArea = new JLabel(ii);
		questPane.setViewportView(questArea);
		
		
	    // Button panel
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setSize(510, 45);
	    buttonPanel.setLocation(148, 490);
	    for (int i = 0; i < 10; i++) {
	        JButton btn = new JButton(String.valueOf(i));
	        btn.addActionListener(this);
	        buttonPanel.add(btn);
	    }
	    panel.add(buttonPanel);

	    getContentPane().add(panel);
	    
	 // Create a new JLabel for the background image
	    JLabel backgroundLabel = new JLabel();
	    backgroundLabel.setBounds(0, 0, 853, 650);
	    // Load the image
	    ImageIcon imageIcon = new ImageIcon(getClass().getResource("/tomato/resources/tomatobg.jpg"));
	    // Scale the image to fit the label
	    Image scaledImage = imageIcon.getImage().getScaledInstance(853, 650, Image.SCALE_SMOOTH);
	    imageIcon = new ImageIcon(scaledImage);
	    // Set the image icon to the label
	    backgroundLabel.setIcon(imageIcon);
	    // Add the label to the panel
	    panel.add(backgroundLabel);
	    // Make the label visible
	    backgroundLabel.setVisible(true);


		
        // Create and start the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining < 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(GameGUI.this, "Time's up!");
                    // Close the current game GUI
                    dispose();
                    // Open the end game GUI
                    EndGameGUI endGameGUI = new EndGameGUI(myGame.getScore());
                    endGameGUI.setVisible(true);
                } else {
                    // Update the display to show the remaining time
                	infoArea.setText("Score: " + myGame.getScore() + "    |   Time remaining: " + formatTimerText(timeRemaining));

                }  
            }
        });
        timer.start();

	}
	

private int getTotalSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}


/**
 * Default player is null. 
 */
	public GameGUI() {
		super();
		initGame(null);		
	}

	/**
	 * Use this to start GUI, e.g., after login.
	 * 
	 * @param player
	 */
	public GameGUI(String username,String difficulty) {
		super();
		this.username = username;
		this.difficultyLevel = difficulty;
		initGame(username);
		setTimerDuration(); // Set the timer duration based on the selected difficulty level
	    try {
	        // Open the database connection when the GameGUI instance is created
	        databaseManager = new DatabaseManager();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the SQLException, such as displaying an error message
	        JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
    // Method to set the timer duration based on the selected difficulty level
    private void setTimerDuration() {
        switch (difficultyLevel) {
            case "Easy":
                timeRemaining = 120; // 60 seconds for Easy
                break;
            case "Medium":
                timeRemaining = 40; // 40 seconds for Medium
                break;
            case "Hard":
                timeRemaining = 05; // 30 seconds for Hard
                break;
            default:
                timeRemaining = 20; // Default time
                break;
        }
    }
	
	/**
	 * Method that is called when a button has been pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int solution = Integer.parseInt(e.getActionCommand());
		boolean correct = myGame.checkSolution(solution);
		int score = myGame.getScore(); 
		if (correct) {
			System.out.println("Correct solution entered!");
			currentGame = myGame.nextGame(); 			
			ImageIcon ii = new ImageIcon(currentGame);
			questArea.setIcon(ii);
			infoArea.setText("Good!  Score: "+score);
		} else { 
			System.out.println("Not Correct"); 
			infoArea.setText("Oops. Try again! ");
			// Check if the timer has expired
	        if (timeRemaining <= 0) {
	            // Stop the timer
	            timer.stop();	   
				// Call the gameOver method to update the database
	            String username = "demo"; // Set the username here
	            gameOver(username);
	            // Show the end game GUI
	            EndGameGUI endGameGUI = new EndGameGUI(score);
	            endGameGUI.setVisible(true);
	            // Close the current game GUI
	            dispose();
	        }
		}
	}
	
	public void gameOver(String username) {
	    int score = myGame.getScore(); // Get the player's score

	    try {
	        // Update the player's score in the database based on the difficulty level
	        switch (difficultyLevel) {
	            case "Easy":
	                databaseManager.updateHighScoreEasy(username, score);
	                break;
	            case "Medium":
	                databaseManager.updateHighScoreMedium(username, score);
	                break;
	            case "Hard":
	                databaseManager.updateHighScoreHard(username, score);
	                break;
	            default:
	                // Handle the case where the difficulty level is unknown
	                break;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the SQLException, such as displaying an error message
	    } finally {
	        // Always close the connection
	        databaseManager.closeConnection();
	    }
	}


	/**
	 * Main entry point into the equation game. Can be used without login for testing. 
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		GameGUI myGUI = new GameGUI();
		myGUI.setVisible(true);

	}
}