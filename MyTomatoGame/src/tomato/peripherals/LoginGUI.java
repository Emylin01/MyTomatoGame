package tomato.peripherals;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tomato.database.DatabaseManager;


public class LoginGUI extends JFrame {

	private static final long serialVersionUID = -8190370823555522092L;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI frame = new LoginGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
		 });
	}
	/**
	 * Create the frame.
	 */

	public LoginGUI() {
		setResizable(false);
		setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(708, 656);
        
        JPanel backgroundPanel = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 // Load the background image
                 ImageIcon imageIcon = new ImageIcon(getClass().getResource("/tomato/resources/tomatobg.jpg"));                
                 Image image = imageIcon.getImage();
                 // Draw the background image
                 g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        	 }};
        	 
        
        	 backgroundPanel.setBorder(null);
        	 backgroundPanel.setBackground(new Color(255, 228, 225));
        	 setContentPane(backgroundPanel);
        	 backgroundPanel.setLayout(null);
        	 
        	 JPanel panel = new JPanel();
        	 panel.setBorder(null);
        	 panel.setBackground(new Color(255, 255, 255));
        	 panel.setBounds(319, 0, 375, 619);
        	 backgroundPanel.add(panel);
        	 panel.setLayout(null);
        	 
        	 JLabel lblNewLabel_1 = new JLabel("USERNAME :");
        	 lblNewLabel_1.setBounds(30, 130, 140, 31);
        	 panel.add(lblNewLabel_1);
        	 lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
        	 lblNewLabel_1.setForeground(new Color(0, 0, 0));
        	 
        	 txtUsername = new JTextField();
        	 txtUsername.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 19));
        	 txtUsername.setToolTipText("");
        	 txtUsername.setBounds(30, 182, 315, 31);
        	 panel.add(txtUsername);
        	 txtUsername.setColumns(10);
        	 
        	 JLabel lblPassword = new JLabel("PASSWORD :");
        	 lblPassword.setBounds(30, 236, 140, 31);
        	 panel.add(lblPassword);
        	 lblPassword.setForeground(new Color(0, 0, 0));
        	 lblPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
        	 
        	 txtPassword = new JPasswordField();
        	 txtPassword.setBounds(30, 278, 315, 31);
        	 panel.add(txtPassword);
        	 
        	 JLabel lblNewLabel = new JLabel("OR");
        	 lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        	 lblNewLabel.setBounds(180, 478, 23, 14);
        	 panel.add(lblNewLabel);
        	 
        	 
        	 JButton btnLogin = new JButton("LOGIN");
        	 btnLogin.setBackground(new Color(144, 238, 144));
        	 btnLogin.setBounds(30, 403, 315, 44);
        	 panel.add(btnLogin);
        	 btnLogin.addActionListener(new ActionListener() {
        	 	public void actionPerformed(ActionEvent e) {
        	 		
        	 		String username = txtUsername.getText();
                    String password = new String(txtPassword.getPassword());
                    
                    if (validateInputs(username, password)) {
                        try {
                            if (DatabaseManager.authenticateUser(username, password)) {
                                // Login successful
                                JOptionPane.showMessageDialog(LoginGUI.this, "Login successful!");
                                HomeGUI homeGUI = new HomeGUI();
                                homeGUI.setVisible(true);
                                dispose();
                            } else {
                                // Login failed
                                JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            // Handle database error
                            JOptionPane.showMessageDialog(LoginGUI.this, "Database error!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Invalid inputs
                        JOptionPane.showMessageDialog(LoginGUI.this, "Please enter valid username and password!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        	 	}
        	 });
        	 btnLogin.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        	 
        	 JButton btnSignup = new JButton("SIGNUP");
        	 btnSignup.setBackground(SystemColor.textHighlight);
        	 btnSignup.setBounds(30, 518, 315, 44);
        	 panel.add(btnSignup);
        	 btnSignup.addActionListener(new ActionListener() {
        	 	public void actionPerformed(ActionEvent e) {
        	 		 SignUpGUI signupGUI = new SignUpGUI();
                     signupGUI.setVisible(true);
                     dispose();
        	 	}
        	 });
        	 
        	 
        	 btnSignup.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
}
    // Method to validate user inputs
    private boolean validateInputs(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }
	
}