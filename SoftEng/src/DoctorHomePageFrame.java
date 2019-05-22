import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class DoctorHomePageFrame extends JFrame {
	
	private JPanel centralPanel;
	
	private JButton button;
	private JScrollPane scrollPane;
	private JTable table;
	
	private JMenuBar menubar;
	private JMenu programMenu;
	private JMenuItem item1,item2;
	
	private JLabel label1, label2, label3, label4;
	private db conn;
	
	public DoctorHomePageFrame(db connection) {
		conn=connection;
		centralPanel=new JPanel();
	
		menubar= new JMenuBar();
		programMenu= new JMenu("Πρόγραμμα");
		
		item1= new JMenuItem("Ωρολόγιο Πρόγραμμα");
		item2= new JMenuItem("Οι εφημερίες μου");
		
		programMenu.add(item1);
		programMenu.add(item2);
		
		
		menubar.add(programMenu);
		
		centralPanel.add(menubar);
		

		ButtonListener listener = new ButtonListener();
		item1.addActionListener(listener);
		item2.addActionListener(listener);
		
		this.setContentPane(centralPanel);
		this.setVisible(true);
		this.setSize(500, 500);
		this.setTitle("Γιατρός/Πρόγραμμα");
		
		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			//κουμπί ωρολόγιο πρόγραμμα
			if(e.getSource()== item1 ) {
				//πίνακας με όλες τις εφημερίες των γιατρών
				label1= new JLabel("Εβδομαδιαίο Πρόγραμμα Εφημεριών");
				label2= new JLabel("Προβολή μόνο για γιατρούς");  //χρειάζεται αυτο;
			}
		}
	}
		
}
