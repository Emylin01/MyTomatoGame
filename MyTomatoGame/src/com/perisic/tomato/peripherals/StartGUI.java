package com.perisic.tomato.peripherals;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class StartGUI extends JFrame {

    private static final long serialVersionUID = -8190370823555522092L;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartGUI startGUI = new StartGUI();
            startGUI.setVisible(true);
        });
    }

    public StartGUI() {
    	setResizable(false);
    	setIconImage(Toolkit.getDefaultToolkit().getImage(StartGUI.class.getResource("/Pics/Tomato1.png")));
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

        JButton btnPlay = new JButton("START");
        btnPlay.setBackground(new Color(51, 204, 153));
        btnPlay.setBounds(247, 433, 193, 59);
        btnPlay.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        
        btnPlay.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                dispose();
        	}
        });
        
        JLabel lblTomato = new JLabel(" ");
        lblTomato.setHorizontalAlignment(SwingConstants.CENTER);
        lblTomato.setBounds(173, 114, 330, 293);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Pics/Tomato1.png"));
      // Scale down the image to fit the button (adjust the width and height as needed)
        Image scaledImage1 = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage1);
        
        backgroundPanel.setLayout(null);
        
        lblTomato.setIcon(imageIcon);
        backgroundPanel.add(lblTomato);
        backgroundPanel.add(btnPlay);
        
        JLabel lblNewLabel = new JLabel("Tomatoo");
        lblNewLabel.setBounds(216, 39, 233, 94);
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
        backgroundPanel.add(lblNewLabel);

    }
}

