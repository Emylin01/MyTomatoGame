package com.perisic.tomato.peripherals;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;

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
    	setIconImage(Toolkit.getDefaultToolkit().getImage(HomeGUI.class.getResource("/Pics/Tomato1.png")));
    	setType(Type.POPUP);
    	setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(685, 615);

        JPanel backgroundPanel = new JPanel() {
            private static final long serialVersionUID = -8054777292399389283L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Pics/tomatobg.jpg"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setBackground(new Color(255, 228, 225));
        setContentPane(backgroundPanel);
        
        JLabel lblTomato = new JLabel(" ");
        lblTomato.setHorizontalAlignment(SwingConstants.CENTER);
        lblTomato.setBounds(10, 157, 306, 274);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Pics/leveltomato.png"));
      // Scale down the image to fit the button (adjust the width and height as needed)
        Image scaledImage1 = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage1);
        
        backgroundPanel.setLayout(null);
        
        lblTomato.setIcon(imageIcon);
        backgroundPanel.add(lblTomato);
        
        JLabel lblNewLabel = new JLabel("Tomatoo");
        lblNewLabel.setBounds(43, 22, 273, 94);
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 50));
        backgroundPanel.add(lblNewLabel);
        
        ImageIcon icon = new ImageIcon(HomeGUI.class.getResource("/Pics/settindbtn.png"));
        Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        
        JPanel panel = new JPanel();
        panel.setBounds(335, 0, 336, 567);
        backgroundPanel.add(panel);
                panel.setLayout(null);
        
        JButton btnPlay = new JButton("PLAY");
              
        btnPlay.setBounds(90, 477, 178, 59);               
        panel.add(btnPlay);               
        btnPlay.setBackground(new Color(51, 204, 153));               
        btnPlay.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
                
        JButton btnSettings = new JButton("");        
        btnSettings.setBounds(257, 11, 56, 59);                               
        panel.add(btnSettings);              
        btnSettings.addActionListener(new ActionListener() {                         	
        	public void actionPerformed(ActionEvent e) {

                    }
                });
                btnSettings.setIcon(new ImageIcon(img));
                
                JLabel lblChooseDifficulty = new JLabel("CHOOSE YOUR LEVEL");
                lblChooseDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
                lblChooseDifficulty.setBounds(0, 60, 344, 95);
                panel.add(lblChooseDifficulty);
                lblChooseDifficulty.setForeground(Color.BLACK);
                lblChooseDifficulty.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
                
                JRadioButton rdbtnEasy = new JRadioButton("EASY");
                rdbtnEasy.setFont(new Font("Stencil", Font.PLAIN, 30));
                rdbtnEasy.setBounds(90, 157, 157, 60);
                panel.add(rdbtnEasy);
                
                JRadioButton rdbtnMedium = new JRadioButton("MEDIUM");
                rdbtnMedium.setFont(new Font("Stencil", Font.PLAIN, 30));
                rdbtnMedium.setBounds(90, 231, 178, 59);
                panel.add(rdbtnMedium);
                
                JRadioButton rdbtnHard = new JRadioButton("HARD");
                rdbtnHard.setFont(new Font("Stencil", Font.PLAIN, 30));
                rdbtnHard.setBounds(93, 321, 142, 47);
                panel.add(rdbtnHard);
                
                // Group the radio buttons together
                ButtonGroup difficultyGroup = new ButtonGroup();
                difficultyGroup.add(rdbtnEasy);
                difficultyGroup.add(rdbtnMedium);
                difficultyGroup.add(rdbtnHard);
                               
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
                        // Check which radio button is selected and set the selected difficulty accordingly
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
                        GameGUI gameGUI = new GameGUI(null,selectedDifficulty); // Pass selected difficulty to GameGUI
                        gameGUI.setVisible(true); // Start the game
                    }
                });


    }
}
