import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class GuiProgramma extends JFrame{
	
	private JPanel centralPanel;
	
	private JButton button;
	private JScrollPane scrollPane;
	private JTable table;
	
	private JLabel label;
	
	private String preference;

	
	public GuiProgramma() {
		centralPanel=new JPanel();
		label=new JLabel("Εγγραφή γιατρού - επιλογή προτιμήσεων");
		
		 
		//πινακας ωρών για επιλογή απο τον γιατρό
		Object[] columnNames = {"Ωράριο" , "Δευτέρα" , "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σάββτο", "Κυριακή"};
	    Object[][] rowData = { {"06:00-14:00", false , false, false, false, false, false, false },
	    					   {"14:00-22:00", false, false, false, false, false, false, false},
	    					   {"22:00-06:00", false, false, false, false, false, false, false} };
	   
	  
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

        table = new JTable(model) {
        	private static final long serialVersionUID = 1L;
        	 @Override
             public Class getColumnClass(int column) {
                     return getValueAt(0, column).getClass();
        	 }
        };
        
	
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
	 
	   // scrollPane.setBounds(36, 37, 407, 79);
		
		button=new JButton("Καταχώρηση προτιμήσεων");
		centralPanel.add(label);
		centralPanel.add(scrollPane);
		centralPanel.add(button);
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		centralPanel.add(lb);
		
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	setVisible(false);
		        new BasicGUI();           
		    }
		});
		
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
		
		
		this.setContentPane(centralPanel);
		this.setVisible(true);
		this.setSize(950, 600);
		this.setTitle("Γιατρός/Εγγραφή/Επιλογή Προτιμήσεων");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			//Μετατροπή των καταχωρήσεων του γιατρού σε μορφή 0(false) και 1(true)
			if(e.getSource() == button) {
				for(int i=1; i<8; i++) {
					for(int j=0; j<3; j++) {
						if(table.getModel().getValueAt(j,i).toString() =="true") {
                            if(i==1 && j==0) {
                            	preference = "1";}
                            else {
                            	preference += "1";}
                           }
						else {
							if(i==1 && j==0) {
								preference = "0";}
							else {
								preference += "0";}
						}
					}
				}
				// To string preference είναι έτοιμο(μορφή με 0 και 1 ανά ημέρα) και στέλνεται στην βάση!
				setVisible(false);
				new GuiGiatros2();
			}
			
		}
	}
}