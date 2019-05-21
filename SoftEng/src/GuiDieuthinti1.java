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


public class GuiDieuthinti1 extends JFrame  {
	
	//private JTextField code= new JTextField("Κωδικός Διευθυντή");
	private JPasswordField code = new JPasswordField(10);
	
	private JButton button =new JButton("Είσοδος");

	private JLabel label= new JLabel();
	private JPanel CentralPanel;

	
	public GuiDieuthinti1() {
		CentralPanel=new JPanel();
		
		label.setText("Εισάγεται τον κωδικό του διευθυντή");
		
		CentralPanel.add(label);
		CentralPanel.add(code);
		CentralPanel.add(button);
		
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
		

		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		CentralPanel.add(lb);
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	setVisible(false);
		        new BasicGUI();           
		    }
		});

		this.setContentPane(CentralPanel);
		this.setVisible(true);
		this.setSize(500, 250);
		this.setTitle("Διευθυντής/Κωδικός");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e)  {
			
			if(e.getSource() == button) {
				String password= code.getText();
				//Έλεγχος για εγκυρότητα κωδικού(μέσω βάσεων δεδομένων)
				
				try {
					db conn = new db();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//όπου 1 ο διευθυντής στην βάση δεδομένων
				//o κωδικός είναι 1111
				String result = db.returnPasswordUser(1, db.getMyConn());
		
				if (password.equals(result)) {
					setVisible(false);
					new GuiDieuthinti2();
				}
				else {
					
					 JOptionPane.showMessageDialog(CentralPanel, "Ο κωδικός δεν είναι έγκυρος");
				}
				
				//(Για δοκιμή μέχρι να μπει η βάση)
			//	setVisible(false);
				//new GuiDieuthinti2();
				
			}
			
		}
	}
	
	
	
}
