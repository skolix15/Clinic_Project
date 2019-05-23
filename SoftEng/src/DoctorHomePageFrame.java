import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
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
	
	private JScrollPane scrollPane;
	private JTable table;
	
	private JMenuBar menubar;
	private JMenu programMenu;
	private JMenuItem timetable,tt_shifts;
	
	private JLabel label1, label2, label3, label4;
	private db conn;
	
	public DoctorHomePageFrame(db connection) {
		conn=connection;
		centralPanel=new JPanel();
	
		menubar= new JMenuBar();
		programMenu= new JMenu("Schedule");
		
		timetable= new JMenuItem("TimeTable");
		tt_shifts= new JMenuItem("TimeTable- Only My Shifts");
		
		programMenu.add(timetable);
		programMenu.add(tt_shifts);
		
		
		menubar.add(programMenu);
		
		centralPanel.add(menubar);
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		centralPanel.add(lb);
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	setVisible(false);
		        new GlobalHomeFrame(conn);           
		    }
		});
		

		ImageIcon icon2 = new ImageIcon("back1.png");
 	    JLabel lab = new JLabel(icon2);
 		centralPanel.add(lab);
 		
 		lab.addMouseListener(new MouseAdapter() 
 		 {
 			public void mouseClicked(MouseEvent e) 
 		    {	 
 				setVisible(false);
 		        new DoctorPreferenceFrame(conn);           
 		    }
 		  });

		ButtonListener listener = new ButtonListener();
		timetable.addActionListener(listener);
		tt_shifts.addActionListener(listener);
		
		this.setContentPane(centralPanel);
		this.setVisible(true);
		this.setSize(500, 500);
		this.setTitle("Doctor/Schedule");
		
		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			//κουμπί ωρολόγιο πρόγραμμα
			if(e.getSource()== timetable ) {
				//πίνακας με όλες τις εφημερίες των γιατρών
				label1= new JLabel("Weekly Program");
	
			}
			else {
				
			}
		}
	}
		
}
