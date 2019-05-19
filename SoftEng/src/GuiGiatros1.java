import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class GuiGiatros1 extends JFrame {
    private JPanel G_Panel;
    private JTextField nameField;
    private JTextField codeField;
    private JLabel label;
    private JButton move;
    
	
    public GuiGiatros1() {
		G_Panel = new JPanel();
		label = new JLabel("Είσοδος του Ιατρού");
		nameField = new JTextField("Εισήγαγε αριθμό μητρώου");
		move = new JButton("Συνέχεια");
		
		G_Panel.add(label);
		G_Panel.add(nameField);
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
		        new BasicGUI();           
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
				String AM = nameField.getText();
				Boolean flag=true;
				//Αναζήτηση στην βάση άμα υπάρχει το ΑΜ
				//AFOU teleiwsei i anaziti kai den vrethike tote flag false
				//Αν βρεθεί
				/*if(ΑΜ Δεν βρεθηκε) {
					flag = false;
				}*/
				
				//Εφόσον βρέθηκε το AM
			/*	if(flag) {
					if(password(apo vasi) = null) {
						codeField = new JTextField("Κωδικός");
					}
					
				}*/
				//αν δημιουργηθει κωδικος να ενημερωθει η βαση
				
		
				
				//EGGRAFII STIN VASI
				
				setVisible(false);

				new GuiProgramma();
			
			}
			
		}
	}
}
