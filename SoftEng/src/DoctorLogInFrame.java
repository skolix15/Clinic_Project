import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class DoctorLogInFrame extends JFrame {
    private JPanel G_Panel;
    private JTextField amField;
    private JTextField codeField;
    private JLabel label;
    private JButton move;
	private db conn;


   
	
    public DoctorLogInFrame(db connection) {
    	conn=connection;
		G_Panel = new JPanel();
		label = new JLabel("Είσοδος του Ιατρού");
		amField = new JTextField("Εισήγαγε αριθμό μητρώου");
		move = new JButton("Συνέχεια");
		
		G_Panel.add(label);
		G_Panel.add(amField);
		//G_Panel.add(codeField);
		G_Panel.add(move);
	

		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		G_Panel.add(lb);
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	setVisible(false);
		        new GlobalHomeFrame(conn);           
		    }
		});
		
		ButtonListener listener = new ButtonListener();
		move.addActionListener(listener);
	
		
		this.setContentPane(G_Panel);
		this.setVisible(true);
		this.setSize(530, 250);
		this.setTitle("Ιατρός");
					 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    

	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == move) {
				String AM = amField.getText();
				int NumberOfDocs;
				String password;
				
				Boolean flag=true;
		    	// ----------SEARCH IF THE DOCTOR ALREADY EXISTS------------
				NumberOfDocs = db.getNumberOfEntries("doctor", "RN", AM, conn.getMyConn());
				
	  			if (NumberOfDocs == -1)
	  			{
	  				  // show error message and exit program??
	  			}
	  			else if (NumberOfDocs == 1 )
	  		    {
	  		 		// Search if the doctor has created his/her password
	  				password = db.returnDoctorPassword(AM, conn.getMyConn());
	  				
	  				if (password == null) // the password hasn't been created. CREATE NOW
	  				{
	  					
	  				}
	  				else // The password has been set. LOG IN
	  				{
	  					
	  				}
	  		    }
	  			else
	  		    {
	  				// show that the doctor with the given AM doesn't exist
	  		    }
	  			
	  			
		
			
				
				setVisible(false);

				new DoctorPreferenceFrame(conn);
			
			}
			
		}
	}
}
