import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.table.DefaultTableModel;



public class DoctorHomePageFrame extends JFrame {
	
	private JPanel centralPanel, mainPanel, secondPanel;
	
	private JScrollPane scrollPane;
	private JTable table;
	
	private JMenuBar menubar;
	private JMenu programMenu;
	private JMenuItem timetable,tt_shifts;
	
	private JLabel label1;
	private db conn;
	
	private DefaultTableModel model;
	
	public DoctorHomePageFrame(db connection) {
		conn=connection;
		centralPanel = new JPanel(new BorderLayout());
		mainPanel= new JPanel();
		secondPanel= new JPanel();
	
		menubar= new JMenuBar();
		programMenu= new JMenu("Schedule");
		
		timetable= new JMenuItem("TimeTable");
		tt_shifts= new JMenuItem("TimeTable- Only My Shifts");
		label1= new JLabel("Weekly Program");
		
		programMenu.add(timetable);
		programMenu.add(tt_shifts);
		
		
		menubar.add(programMenu);
		
		mainPanel.add(menubar);
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		mainPanel.add(lb);
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
 		mainPanel.add(lab);
 		
 		lab.addMouseListener(new MouseAdapter() 
 		 {
 			public void mouseClicked(MouseEvent e) 
 		    {	 
 				setVisible(false);
 		        new DoctorPreferenceFrame(conn);           
 		    }
 		  });

 		secondPanel.add(label1);
		ButtonListener listener = new ButtonListener();
		timetable.addActionListener(listener);
		tt_shifts.addActionListener(listener);
		
		model = new DefaultTableModel();
		model.addColumn("Schedule");
		model.addColumn("Monday");
		model.addColumn("Tuesday");
		model.addColumn("Wednesday");
		model.addColumn("Thursday");
		model.addColumn("Friday");
		model.addColumn("Saturday");
		model.addColumn("Sunday");
		model.addRow( new Object[] {"06:00-14:00", "-" , "-", "-", "-", "-", "-", "-" });
		model.addRow( new Object[] {"14:00-22:00", "-" , "-", "-", "-", "-", "-", "-" });
		model.addRow( new Object[] {"22:00-00:00", "-" , "-", "-", "-", "-", "-", "-" });
		table = new JTable(model);
		
		table.setAlignmentX(Component.LEFT_ALIGNMENT);
		  
		// Create ScrollPane for the table
		   
	    scrollPane = new JScrollPane(table);
		scrollPane.setBounds(36, 37, 407, 79);
		
		secondPanel.add(scrollPane);
			
		centralPanel.add(mainPanel, BorderLayout.NORTH);
		centralPanel.add(secondPanel, BorderLayout.CENTER);
		
		
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
				//gemizw to model me oti uparxei stin vasi
	
			}
			else {
				//gemizw mono tis grammes opou leei to onoma tou giatrou
				
			}
		}
	}
		
}
