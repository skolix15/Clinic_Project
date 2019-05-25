import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IdentityFrame extends JFrame{
	
	private JLabel firstTitleOfPage;
	private JPasswordField textFieldForPassword;
	private JButton logInButton;
	private JPanel panel;
	private String correct = "aris";
	private db conn;
	
	public IdentityFrame(db connection) {
		conn=connection;
		panel = new JPanel();
		
		firstTitleOfPage = new JLabel("Central Page Of Pharmacist. Enter Password: ");
		textFieldForPassword = new JPasswordField(10);
		logInButton = new JButton("Log in");
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		
		lb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	dispose();
		    	new GlobalHomeFrame(conn);
		    	}
			
		});
		

		panel.add(firstTitleOfPage);
		panel.add(textFieldForPassword);
		panel.add(logInButton);
		
		textFieldForPassword.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	logInButton.doClick();
	            }
	        }

	    });
		

		logInButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				String password = textFieldForPassword.getText();
				
				if( password.equals(correct)) {
					dispose();
					new SupplyChainMainFrame(conn);
				}
				
				else {
					JOptionPane.showMessageDialog(null,"Wrong Password","Error..",JOptionPane.ERROR_MESSAGE);
					textFieldForPassword.setText("");
				}
				
			}
	
		}	);
		
		
		panel.add(lb);
		panel.add(firstTitleOfPage);


		panel.add(textFieldForPassword);

		panel.add(logInButton);
		
		
		panel.setLayout(new FlowLayout()); //Centered components

		
		this.setContentPane(panel);
		
		this.setVisible(true);;
		this.setSize(400,400);
		this.setTitle("/Supply Chain/Pharmacist");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}


