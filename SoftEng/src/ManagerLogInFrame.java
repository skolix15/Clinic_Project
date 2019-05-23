import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class ManagerLogInFrame extends JFrame  {

	private JPasswordField code_pass = new JPasswordField(10);
	
	private JButton log_in_But =new JButton("Log In");

	private JLabel label= new JLabel();
	private JPanel CentralPanel;

	private db conn;

	
	public ManagerLogInFrame(db connection) {
		conn = connection;
		CentralPanel=new JPanel();
		
		label.setText("Please enter Manager's password");
		
		CentralPanel.add(label);
		CentralPanel.add(code_pass);
		CentralPanel.add(log_in_But);
		
		ButtonListener listener = new ButtonListener();
		log_in_But.addActionListener(listener);
		

		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		CentralPanel.add(lb);
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	setVisible(false);
		        new GlobalHomeFrame(conn);           
		    }
		});

		this.setContentPane(CentralPanel);
		this.setVisible(true);
		this.setSize(500, 250);
		this.setTitle("Manager/Password");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e)  {
			
			if(e.getSource() == log_in_But) {
				String password= code_pass.getText();
			
				
				/*Manager Code is 1111
				 * The given password must be the same with database's password
				 * All the Managers have the same password
				 */
				String result = db.returnPasswordUser(1, db.getMyConn());
		
				if (password.equals(result)) {
					setVisible(false);
					new ManagerChoiceFrame(conn);
				}
				else {
					
					 JOptionPane.showMessageDialog(CentralPanel, "Wrong password");
				}
		
				
			}
			
		}
	}
	
	
	
}
