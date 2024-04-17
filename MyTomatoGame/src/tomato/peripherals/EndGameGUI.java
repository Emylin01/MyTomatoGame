package tomato.peripherals;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;

public class EndGameGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndGameGUI(int score) {
        setTitle("Game Over");
        setSize(756, 549);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setBackground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
        scoreLabel.setBounds(261, 69, 230, 85);
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBackground(new Color(144, 238, 144));
        playAgainButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        playAgainButton.setBounds(272, 369, 219, 42);
        JButton backToHomeButton = new JButton("Back to Home");
        backToHomeButton.setBackground(new Color(135, 206, 250));
        backToHomeButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        backToHomeButton.setBounds(272, 422, 219, 42);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(scoreLabel);
        panel.add(playAgainButton);
        panel.add(backToHomeButton);
        getContentPane().add(panel);
       
	    JLabel tomatoLabel = new JLabel();
	    tomatoLabel.setBounds(272, 125, 219, 233);

	    ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("/tomato/resources/scoretomato.png"));

	    Image scaledImage1 = imageIcon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	    imageIcon1 = new ImageIcon(scaledImage1);

	    tomatoLabel.setIcon(imageIcon1);
	    panel.add(tomatoLabel);
	    tomatoLabel.setVisible(true);

	    ImageIcon imageIcon = new ImageIcon(getClass().getResource("/tomato/resources/tomatobg.jpg"));

	    Image scaledImage = imageIcon.getImage().getScaledInstance(853, 650, Image.SCALE_SMOOTH);
	    imageIcon = new ImageIcon(scaledImage);

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restart the game
                dispose();
                GameGUI gameGUI = new GameGUI();
                gameGUI.setVisible(true);
            }
        });

        backToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	HomeGUI homeGUI = new HomeGUI();
                homeGUI.setVisible(true);
                dispose();

            }
        });
    }
}

