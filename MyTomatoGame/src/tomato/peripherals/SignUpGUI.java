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
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import tomato.database.DatabaseManager;


public class SignUpGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtUserName;
	private JTextField txtEmail;
	private JPasswordField txtNewPassword;
	private JPasswordField txtConfirmPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpGUI frame = new SignUpGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SignUpGUI() {
		setResizable(false);
		setTitle("Sign-Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 649);
		
		JPanel backgroundPanel1 = new JPanel() {
	        
			private static final long serialVersionUID = -8054777292399389283L;


			@Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 // Load the background image
                 ImageIcon imageIcon = new ImageIcon(getClass().getResource("/tomato/resources/tomatobg.jpg"));                
                 Image image = imageIcon.getImage();
                 // Draw the background image
                 g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        	 }};
		
        	 backgroundPanel1.setBorder(null);
             backgroundPanel1.setBackground(new Color(255, 228, 225));
        	 setContentPane(backgroundPanel1);
        	 backgroundPanel1.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(328, 0, 373, 612);
		backgroundPanel1.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("USERNAME :");
		lblUsername.setBounds(32, 81, 225, 21);
		panel.add(lblUsername);
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		
		txtUserName = new JTextField();
		txtUserName.setBounds(32, 113, 315, 31);
		txtUserName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 19));
		panel.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-MAIL :");
		lblEmail.setBounds(32, 155, 225, 31);
		panel.add(lblEmail);
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		
		txtEmail = new JTextField();
		txtEmail.setBounds(32, 197, 315, 31);
		txtEmail.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 19));
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD :");
		lblPassword.setBounds(32, 242, 225, 27);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		
		txtNewPassword = new JPasswordField();
		txtNewPassword.setBounds(32, 280, 315, 31);
		txtNewPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 19));
		panel.add(txtNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("CONFIRM PASSWORD :");
		lblConfirmPassword.setBounds(32, 328, 238, 31);
		panel.add(lblConfirmPassword);
		lblConfirmPassword.setForeground(Color.BLACK);
		lblConfirmPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setBounds(32, 370, 315, 31);
		txtConfirmPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 19));
		panel.add(txtConfirmPassword);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.setBounds(32, 469, 315, 44);
		btnRegister.setBackground(SystemColor.textHighlight);
		panel.add(btnRegister);
		btnRegister.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		
		btnRegister.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String username = txtUserName.getText();
		        String email = txtEmail.getText();
		        String password = new String(txtNewPassword.getPassword());
		        String confirmPassword = new String(txtConfirmPassword.getPassword());
		        
		        if (validateInputs(username, email, password, confirmPassword)) {
		            
		            try {
		            	DatabaseManager databaseManager = new DatabaseManager();
		                databaseManager.registerUser(username, email, password, confirmPassword);
		                JOptionPane.showMessageDialog(SignUpGUI.this, "Registration successful!");
		                
		                LoginGUI loginGUI = new LoginGUI();
		                loginGUI.setVisible(true);
		                dispose(); // Close the signup window
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                // Handle database error
		                JOptionPane.showMessageDialog(SignUpGUI.this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            // Invalid inputs
		            JOptionPane.showMessageDialog(SignUpGUI.this, "Please enter valid username, email, and password!", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});


		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(32, 533, 315, 44);
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {					
				LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                dispose();
                
                
			}
		});
		btnCancel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		btnCancel.setBackground(SystemColor.textHighlight);
		panel.add(btnCancel);
		
		 
	}

    // Method to validate user inputs
    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        // Check if any field is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false; // Empty fields are not allowed
        }

        // Check if email format is valid
        if (!isValidEmail(email)) {
            return false; // Invalid email format
        }

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            return false; // Passwords do not match
        }

        // If all validation checks pass, return true
        return true;
    }
    
    // Method to check if email format is valid
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    
}
