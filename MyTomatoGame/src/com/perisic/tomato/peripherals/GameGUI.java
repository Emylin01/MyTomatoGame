package com.perisic.tomato.peripherals;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import com.perisic.tomato.engine.GameEngine;

public class GameGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -107785653906635L;
	private Timer timer;
	private int timeRemaining = 20; //Initial time in seconds
	
	private String difficultyLevel; // Store the selected difficulty level

	private JLabel questArea = null;
	private GameEngine myGame = null;
	private BufferedImage currentGame = null;
	private JTextArea infoArea = null;
	int totalSeconds = getTotalSeconds(); // Get total seconds from your timer logic

	// Calculate minutes and seconds
	int minutes = totalSeconds / 60;
	int seconds = totalSeconds % 60;

	// Update your GUI to display minutes and seconds
	String timerText = String.format("%02d:%02d", minutes, seconds);
//	timerLabel.setText(timerText); // Assuming timerLabel is your JLabel for displaying the timer


// * Initializes the game. 
	private void initGame(String player) {
		setSize(813, 671);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("What is the missing value?");
		JPanel panel = new JPanel();

		myGame = new GameEngine(player);
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
	    ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Pics/tomatobg.jpg"));
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
                    infoArea.setText("     Score:" + myGame.getScore() +"    Time remaining: " + timeRemaining + " seconds");
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
	public GameGUI(String player,String difficulty) {
		super();
		this.difficultyLevel = difficulty;
		initGame(player);
		setTimerDuration(); // Set the timer duration based on the selected difficulty level
	}
	
    // Method to set the timer duration based on the selected difficulty level
    private void setTimerDuration() {
        switch (difficultyLevel) {
            case "Easy":
                timeRemaining = 60; // 60 seconds for Easy
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