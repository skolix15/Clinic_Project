import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;



public class DeleteFrame extends JFrame {
	
	private JPanel panel;
	private JButton confirm;
	private JLabel title, title1;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private JTable storageTable;
	private JTable deleteTable;

	
	public DeleteFrame() {
		
		
		//Δημιουργία panel
		panel = new JPanel();
		menuPanel = new JPanel();
				
		//Δημιουργία τίτλου για το παράθυρο
		title1 = new JLabel("Αφαίρεση");
		title1.setAlignmentX(Component.LEFT_ALIGNMENT);
				
		
		
		//Δημιουργία για κουμπί καταχώρησης
		confirm = new JButton("Διαγραφή Φαρμάκου");
		ButtonListenerDeleteButton deleteButton = new ButtonListenerDeleteButton();
		confirm.addActionListener(deleteButton);
				
		
		//Δημιουργία μπάρας μενού
		mb = new JMenuBar();
		

	    //Πίνακας Διαγραφής
	    String columnNamesDelete[] = {"Code" , "Name" , "Quantity", "Price"};
	    String rowDataDelete[][] = {};
	    deleteTable = new JTable(rowDataDelete, columnNamesDelete);
	    deleteTable.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JScrollPane scrollPaneDelete = new JScrollPane(deleteTable);
		scrollPaneDelete.setBounds(100, 100, 100, 100);
			
		
		// Dokimastikes stiles kai grammes gia thn exakribwsi ths leitourgias tou pinaka (apothiki)
	    String columnNames[] = {"Code" , "Name" , "Quantity", "Price"};
	    String rowData[][] = { {"dai1","Aris","3", "14.04"},
	    					   {"dai2","Aris2","2", "19.99"}	};
	    // Dimiourgia tou pinaka me basi tis parapanw stiles kai grammes
	    storageTable = new JTable(rowData,columnNames);
	    // Kathorismos topothetisis tou pinaka  
	    storageTable.setAlignmentX(Component.CENTER_ALIGNMENT);
	   
	    
	    storageTable.setCellSelectionEnabled(true);
	    ListSelectionModel cellSelectionModel = storageTable.getSelectionModel();
 	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
 	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	    
 	    	public void valueChanged(ListSelectionEvent e) {
 	 	    	  
 	 	    	if( !e.getValueIsAdjusting() ) {
 	 	    		
 	 	    	
 	 	    	  String medicineName = "";
 	 	    	  String medicineCode = "";

 	 	    	  for (int j = 0; j < 2; j++) {
 	 	        	  
 	 	        	  	if( j==0 )
 	 	        	  		medicineCode = (String) storageTable.getModel().getValueAt(storageTable.getSelectedRow(),j);
 	 	        	  	else if( j==1 )
 	 	        	  		medicineName = (String) storageTable.getModel().getValueAt(storageTable.getSelectedRow(), j);
 	 	        	  	
 	 	    	  }

 	 	    	  Medicine clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);
 	 		    
 	 	    	  DefaultTableModel model = (DefaultTableModel) deleteTable.getModel();
 	 	        
 	 	    	  model.addRow(new Object[]{clickedMedicine.getCode(), clickedMedicine.getName(),
 	 	    			  clickedMedicine.getAvailability(), clickedMedicine.getPrice()}); 
 	 	     
 	 	      	}
 	 		
 	 	      }
 	 	      
 	 	    }  );
 	 	    	  
 	 	    	  
 	 	    	  
 	 	    	  
 	 	    	  
 	 	    	  
	    
	    // Dimiourgia scrollPane gia ton pinaka 
	    JScrollPane scrollPane = new JScrollPane(storageTable);
		scrollPane.setBounds(10, 10, 10, 10);
		// Dimiourgia titlou tou pinaka (apothiki)
	    title = new JLabel("Αποθήκη");
	    // Kathorismos topothethshs tou titlou
	    title.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
		
		//Δημιουργία μενού
	    centralMenu_Menu = new JMenu("Central Menu");
		orderMenu = new JMenu("Order");
		storageMenu = new JMenu("Storage");
		statisticsMenu = new JMenu("Statistics");
		
		
		//Δημιουργία επιλογών κάθε μενού
		
		
		
		i1 = new JMenuItem("Recipe");  
	    i2 = new JMenuItem("Supply");  
	    i3 = new JMenuItem("Add");
	    i4 = new JMenuItem("Delete"); 
	    i5 = new JMenuItem("Show Statistics");
	    iCentralMenu = new JMenuItem("Go to Central Menu");
	    
	    // Eisagwgi ActionListener gia ta pedia tou Menu
	    
	    JTablePopupMenuListener menuListener = new JTablePopupMenuListener();
	    i1.addActionListener(menuListener);
	    i2.addActionListener(menuListener);
	    i3.addActionListener(menuListener);
	    i4.addActionListener(menuListener);
	    i5.addActionListener(menuListener);
	    iCentralMenu.addActionListener(menuListener);
		
	    //Eισαγωγή στο μενού
	    centralMenu_Menu.add(iCentralMenu);
	    orderMenu.add(i1);
	    orderMenu.add(i2);
	    storageMenu.add(i3);
	    storageMenu.add(i4);
	    statisticsMenu.add(i5);
	    
	    //Εισαγωγή των μενού στην μπάρα
	    mb.add(iCentralMenu);
	    mb.add(orderMenu);
	    mb.add(storageMenu);
	    mb.add(statisticsMenu);
			    
	    
	    //Τρόπος τοποθέτησης στα πάνελ
	    menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.X_AXIS));
	    menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
	   
	    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
				
	    
	    //Προσθήκη μενού στο πάνελ
	    menuPanel.add(mb);
			    
	    
		//Προσθήκη στο panel   
	    
	    panel.add(menuPanel);
	    
		panel.add(title);	
		panel.add(scrollPane);
		panel.add(title1);	
		panel.add(scrollPaneDelete);
		panel.add(confirm);
				
				
				
				
		//Εισαγωγή του panel στο contentpane
				
		this.setContentPane(panel);
				
				
		//Καθορισμός των βασικών χαρακτηριστικών του panel
				
		this.setSize(600,300);
		this.setTitle("/Εφοδιαστική_Αλυσίδα/Αποθήκη/Αφαίρεση");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	
	class JTablePopupMenuListener implements ActionListener {


		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Go to Central Menu")){
				
				dispose();
				new MainFrame();
			}
			
			else if(e.getActionCommand().equals("Prescription") ) {
					
				dispose();
				new PrescriptionAndSupplyFrame(true);
				
			}
			
			else if(e.getActionCommand().equals("Supply")){
				
				dispose();
				new PrescriptionAndSupplyFrame(false);
				
			}
			
			else if(e.getActionCommand().equals("Add")) {
				
				dispose();
				new AddFrame();
			}
			
			else if(e.getActionCommand().equals("Delete")) {
				
				dispose();
				new DeleteFrame();
			}
			
			else if(e.getActionCommand().equals("Show Statistics")) {
				
				dispose();
				// ....
			}
			
		}
		
	}
	
	class ButtonListenerDeleteButton implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
				//Διαγράφει τα φάρμακα της λίστας διαγραφής
			
		}
	}	
	
	
}
