package tomato.peripherals;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import tomato.database.DatabaseManager;

public class HomeGUI extends JFrame {

    private static final long serialVersionUID = -8190370823555522092L;
    private String selectedDifficulty = "Easy"; // Default difficulty level
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeGUI homeGUI = new HomeGUI();
            homeGUI.setVisible(true);
        });
    }

	public HomeGUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeGUI.class.getResource("/tomato/resources/Tomato1.png")));
		setType(Type.POPUP);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(680, 580);

		JPanel backgroundPanel = new JPanel() {
			private static final long serialVersionUID = -8054777292399389283L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon imageIcon = new ImageIcon(getClass().getResource("/tomato/resources/tomatobg.jpg"));
				Image image = imageIcon.getImage();
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		backgroundPanel.setBackground(new Color(255, 228, 225));
		setContentPane(backgroundPanel);

		JLabel lblTomato = new JLabel(" ");
		lblTomato.setHorizontalAlignment(SwingConstants.CENTER);
		lblTomato.setBounds(0, 256, 315, 287);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/tomato/resources/leveltomato.png"));
		// Scale down the image to fit the button (adjust the width and height as
		// needed)
		Image scaledImage1 = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(scaledImage1);

		backgroundPanel.setLayout(null);

		lblTomato.setIcon(imageIcon);
		backgroundPanel.add(lblTomato);

		ImageIcon icon = new ImageIcon(HomeGUI.class.getResource("/tomato/resources/settindbtn.png"));
		Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);

		ImageIcon icon1 = new ImageIcon(HomeGUI.class.getResource("/tomato/resources/winning.png"));
		Image img1 = icon1.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);

		JPanel panel = new JPanel();
		panel.setBounds(316, 0, 350, 567);
		backgroundPanel.add(panel);
		panel.setLayout(null);

		JButton btnPlay = new JButton("PLAY");
		btnPlay.setBounds(90, 433, 178, 59);
		panel.add(btnPlay);
		btnPlay.setBackground(new Color(51, 204, 153));
		btnPlay.setFont(new Font("Showcard Gothic", Font.BOLD, 40));

		JLabel lblChooseDifficulty = new JLabel("CHOOSE MODE");
		lblChooseDifficulty.setBounds(-4, 55, 344, 95);
		lblChooseDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblChooseDifficulty);
		lblChooseDifficulty.setForeground(Color.BLACK);
		lblChooseDifficulty.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));

		JRadioButton rdbtnEasy = new JRadioButton("EASY");
		rdbtnEasy.setBounds(90, 157, 157, 60);
		rdbtnEasy.setFont(new Font("Stencil", Font.PLAIN, 30));
		panel.add(rdbtnEasy);

		JRadioButton rdbtnMedium = new JRadioButton("MEDIUM");
		rdbtnMedium.setBounds(90, 231, 178, 59);
		rdbtnMedium.setFont(new Font("Stencil", Font.PLAIN, 30));
		panel.add(rdbtnMedium);

		JRadioButton rdbtnHard = new JRadioButton("HARD");
		rdbtnHard.setBounds(93, 321, 142, 47);
		rdbtnHard.setFont(new Font("Stencil", Font.PLAIN, 30));
		panel.add(rdbtnHard);

		// Group the radio buttons together
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(rdbtnEasy);
		difficultyGroup.add(rdbtnMedium);
		difficultyGroup.add(rdbtnHard);

		JButton btnSettings = new JButton("");
		btnSettings.setBackground(UIManager.getColor("Button.background"));
		btnSettings.setBounds(271, 11, 69, 59);
		panel.add(btnSettings);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnSettings.setIcon(new ImageIcon(img));

		JButton btnScoreBoard = new JButton("");
		btnScoreBoard.setBackground(UIManager.getColor("Button.background"));
		btnScoreBoard.setBounds(108, 11, 83, 79);
		backgroundPanel.add(btnScoreBoard);
		
		btnScoreBoard.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            Connection connection = DatabaseManager.getConnection(); // Get the database connection
		            LeaderboardGUI leaderboardGUI = new LeaderboardGUI(connection); // Pass the connection to the constructor
		            leaderboardGUI.setVisible(true);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            // Handle the SQLException here, such as displaying an error message
		        }
		    }
		});

		btnScoreBoard.setIcon(new ImageIcon(img1));

		// Action listener for radio buttons to capture selected difficulty level
		ActionListener radioButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedDifficulty = ((JRadioButton) e.getSource()).getText();
			}
		};

		rdbtnEasy.addActionListener(radioButtonListener);
		rdbtnMedium.addActionListener(radioButtonListener);
		rdbtnHard.addActionListener(radioButtonListener);

		// Action listener for "PLAY" button
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check which radio button is selected and set the selected difficulty
				// accordingly
				if (rdbtnEasy.isSelected()) {
					selectedDifficulty = "Easy";
				} else if (rdbtnMedium.isSelected()) {
					selectedDifficulty = "Medium";
				} else if (rdbtnHard.isSelected()) {
					selectedDifficulty = "Hard";
				} else {
					// Handle case where no difficulty is selected
					// You can set a default difficulty or display an error message
					// For now, let's set it to Easy as a default
					selectedDifficulty = "Easy";
				}

				// Open the game GUI with the selected difficulty level
				dispose(); // Close the HomeGUI
				GameGUI gameGUI = new GameGUI(null, selectedDifficulty); // Pass selected difficulty to GameGUI
				gameGUI.setVisible(true); // Start the game
			}
		});

	}
}