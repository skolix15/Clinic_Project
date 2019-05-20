import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PrescriptionAndSupplyFrame extends JFrame {
	
	private JPanel panel;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JTable storageTable,orderTable;
	private JLabel storageTitle,orderTitle,totalCostTitle;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private DefaultTableModel storageModel,basketModel;
	private boolean typeOfOrder;
	private JTextField textFieldForCost;
	private Order order = null;
	private String fileNameForHistory;
	private JButton confirmButton;
   
	public PrescriptionAndSupplyFrame(boolean aTypeOfOrder) {
		
		// Dimiourgia button gia epikirwsi
		
		confirmButton = new JButton("Confirm Order");
		
		// Prosthiki listener sto confirmButton
		
		confirmButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
				
				order.informHistory(fileNameForHistory);
				DefaultTableModel dm = (DefaultTableModel) orderTable.getModel();
				int rowCount = dm.getRowCount();
				
				//Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
				    dm.removeRow(i);
				}
				
				textFieldForCost.setText("");
				order.clearOrder();
	
			}
		});
		
		
		// Dimiourgia tou text poy tha periexei to synoliko kostos
		
		textFieldForCost = new JTextField();
		
		// Anathesi timwn ( 0 gia prescription kai 1 gia supply ) kai prosdiorismos tou typou paraggelias
		
		typeOfOrder = aTypeOfOrder;
		fileNameForHistory = "";
		
		if( typeOfOrder == true) {
	    	order = new Prescription();
	    	fileNameForHistory = "History For Prescription.txt";
		}
		
	    else if ( typeOfOrder == false) {
	    	order = new Supply();
	    	fileNameForHistory = "History For Supply.txt";
	    }
		
		// Dimiourgia baras menu
		
		mb = new JMenuBar();
		
		// Dimiourgia twn triwn menu
		
		orderMenu = new JMenu("Order");
		storageMenu = new JMenu("Storage");
		statisticsMenu = new JMenu("Statistics");
		centralMenu_Menu = new JMenu("Central Menu");
		
		// Dimiourgia epilogwn-pediwn gia kathe lista ( Paraggelia, Apothiki, Statistika )
		
		i1 = new JMenuItem("Prescription");  
	    i2 = new JMenuItem("Supply");  
	    i3 = new JMenuItem("Add");  
	    i4 = new JMenuItem("Delete"); 
	    i5 = new JMenuItem("Show Statistics");
	    iCentralMenu = new JMenuItem("Go to Central Menu");
	    
	    // Eisagwgi ActionListener gia ta pedia tou Menu
	    
	    JTablePopupMenuListener listener = new JTablePopupMenuListener();
	    i1.addActionListener(listener);
	    i2.addActionListener(listener);
	    i3.addActionListener(listener);
	    i4.addActionListener(listener);
	    i5.addActionListener(listener);
	    iCentralMenu.addActionListener(listener);
	    
	    // Eisagwgi twn pediwn sto antistoixo menu
	    
	    centralMenu_Menu.add(iCentralMenu);
	    
	    orderMenu.add(i1);
	    orderMenu.add(i2);
	    
	    storageMenu.add(i3);
	    storageMenu.add(i4);
	    
	    statisticsMenu.add(i5);
	    
	    // Eisagwgi twn menu sth bara menu
	    
	    mb.add(centralMenu_Menu);
	    mb.add(orderMenu);
	    mb.add(storageMenu);
	    mb.add(statisticsMenu);
	    
	    // Eisagwgi twn farmakwn pou briskontai sthn apothiki tou iatreiou, ston pinaka pou antistoixizetai se aythn
	    
	    storageModel = new DefaultTableModel();
	    basketModel = new DefaultTableModel();
	    
	    storageModel.addColumn("Code");
	    storageModel.addColumn("Name");
	    storageModel.addColumn("Availability");
	    
	    basketModel.addColumn("Code");
	    basketModel.addColumn("Name");
	    basketModel.addColumn("Quantity");
	    
	    String medicineName = null;
	    String medicineCode = null;
	    int medicineAvailability = -1;
	    
	    for(int i=0;i<Storage.getMedicineList().size();i++) {
	    	
	    	medicineName = Storage.getMedicineList().get(i).getName();
	    	medicineCode = Storage.getMedicineList().get(i).getCode();
	    	medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
	    	storageModel.addRow(new Object[] {medicineCode,medicineName,medicineAvailability});
	    }
	    
	    // Dimiourgia tou pinaka me basi tis parapanw stiles kai grammes
	    
	    storageTable = new JTable(storageModel);
	    orderTable = new JTable(basketModel);
	   
	    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(storageTable.getModel());
	    storageTable.setRowSorter(sorter);
	    
	    JLabel label = new JLabel("Search...");
	    JTextField filterText = new JTextField();

	    filterText.getDocument().addDocumentListener(new DocumentListener(){

            public void insertUpdate(DocumentEvent e) {
            	
                String text = filterText.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
	   
            public void removeUpdate(DocumentEvent e) {
            	
                String text = filterText.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            public void changedUpdate(DocumentEvent e) {
            	
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
	    
	    // Kathorismos topothetisis tou pinaka
	    
	    storageTable.setAlignmentX(Component.LEFT_ALIGNMENT);
	    orderTable.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    // Dimiourgia scrollPane gia ton pinaka 
	    
	    JScrollPane storageScrollPane = new JScrollPane(storageTable);
	    JScrollPane orderScrollPane = new JScrollPane(orderTable);
		storageScrollPane.setBounds(36, 37, 407, 79);
		orderScrollPane.setBounds(36, 37, 407, 79);
	    
	    // Dimiourgia titlou tou pinaka (apothiki), tou pinaka (kalathi) kai tou TextField gia to totalCost
		
	    storageTitle = new JLabel("STORAGE");
	    orderTitle = new JLabel("Basket");
	    totalCostTitle = new JLabel("Total Cost");
	    
	    
	    // Kathorismos topothethshs tou titlou
	    
	    storageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
	    orderTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    // Dimiourgia Panel
	    
	    menuPanel = new JPanel();
	    panel = new JPanel();
	    
	    // Kathorismos tou tropou topothetisis twn antikeimenwn sto kathe panel panel ( px katakorifa, orizontia )
	    
	    menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.X_AXIS));
	    menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	    panel.setAlignmentX(Component.BOTTOM_ALIGNMENT);
	    
	    
	    // Prosthiki olwn twn menu sto panel 
	    
	    menuPanel.add(mb);
	    
	    // Prosthiki tou parapanw panel sto teliko panel 
	    // Epipleon prosthiki tou pinaka (apothiki) kai tou titlou tou sto teliko panel
	    
	    panel.add(menuPanel);
	    
	    panel.add(storageTitle);
	    panel.add(storageScrollPane);
	    
	    panel.add(orderTitle);
	    panel.add(orderScrollPane);
	    
	    panel.add(totalCostTitle);
	    panel.add(textFieldForCost);
	    
	    panel.add(label, BorderLayout.WEST);
	    panel.add(filterText, BorderLayout.CENTER);
	    
	    panel.add(confirmButton);
	    
	 // Epikoinwnia pinakwn ( Epilogi apo thn apothiki kai gemisma kalathiou me farmaka )
	    
	    storageTable.setCellSelectionEnabled(true);
 	    ListSelectionModel cellSelectionModel = storageTable.getSelectionModel();
 	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

 	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
 	    	
 	      public void valueChanged(ListSelectionEvent e) {
 	    	  
 	    	if(!e.getValueIsAdjusting() ) {
 	    		
 	    	
 	    	  String medicineName = "";
 	    	  String medicineCode = "";
 	    	  String totalCost = "";
 	    	  
 	    	 if(storageTable.getSelectedRow() != -1) {
 
 	    	  for (int j = 0; j < 2; j++) {
 	        	  
 	        	  	if( j==0 )
 	        	  		medicineCode = (String) storageTable.getModel().getValueAt(storageTable.getSelectedRow(),j);
 	        	  	else if( j==1 )
 	        	  		medicineName = (String) storageTable.getModel().getValueAt(storageTable.getSelectedRow(), j);
 	        	  	
 	    	  }
 	       
 	    	  Medicine clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);		
 	    	  
 	    	  
 		    
 	    	  if(!order.searchForMedicineInOrder( clickedMedicine.getCode())) {
 	    		  
 	    		  String inputAvailabilityString = JOptionPane.showInputDialog(null,"Enter quantity of the medicine you want to buy: ");
 	    		  
 	    		  if(inputAvailabilityString == null || inputAvailabilityString.equals("") )
 	    			  JOptionPane.showMessageDialog(null,"You dind't enter any number.","Error..",JOptionPane.ERROR_MESSAGE);

 	    		  else {
 	    			  
 	    			  int inputAvailabilityInteger = Integer.parseInt(inputAvailabilityString);
 	    			  
 	    			  if(inputAvailabilityInteger <= 0) 
 	    				  JOptionPane.showMessageDialog(null,"Invalid quantity of medicine.","Error..",JOptionPane.ERROR_MESSAGE);
 	    			  
 	    			  else if(typeOfOrder == true && !(clickedMedicine.getAvailability() >= inputAvailabilityInteger))
	    			  		JOptionPane.showMessageDialog(null,"Not enough stocks for this medicine.","Error..",JOptionPane.ERROR_MESSAGE);
 	    			  
 	    			  else  {
 	    		  
 	    			  		order.addMedicineInTheOrder(clickedMedicine, inputAvailabilityInteger); 	
 		    
 	    			  		DecimalFormat df = new DecimalFormat("#.##");
	    			
 	    			  		totalCost = String.valueOf(df.format(order.getTotalCost()));
	     		    
 	    			  		textFieldForCost.setText(totalCost);
 		    
 	    			  		DefaultTableModel modelForOrderTable = (DefaultTableModel) orderTable.getModel();
 	        
 	    			  		modelForOrderTable.addRow(new Object[]{clickedMedicine.getCode(), clickedMedicine.getName(), inputAvailabilityInteger}); 
 	    	  
 	    			  		for(int i=0;i<Storage.getMedicineList().size();i++) {
 	    		  
 	    	  						int medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
 	    	  						storageTable.getModel().setValueAt(medicineAvailability, i, 2); 
 	    	  			
 	    	  					}
 	    			  	}
 	    			  
 	    			  	
 	    			  
 	    		  	}
 	    		  }
 	    	  }
 	    	 }
 	    	}
 	      
 	    } ); 
 	    
 	    orderTable.addMouseListener(new MouseAdapter() {
 	    	
 	    	
 	    	public void mousePressed (MouseEvent e) {
 	    		
 	    		  String medicineName = "";
 		    	  String medicineCode = "";
 		    	  String totalCost = "";

 		    	 int selectedRowFromOrderTable = e.getY()/orderTable.getRowHeight();
 		    	  
 		    	  
 		    	 for (int j = 0; j < 2; j++) {
 		        	  
 		    		 
 		        	  	if( j==0 )
 		        	  		medicineCode = (String) orderTable.getModel().getValueAt(selectedRowFromOrderTable,j);
 		        	  	else if( j==1 )
 		        	  		medicineName = (String) orderTable.getModel().getValueAt(selectedRowFromOrderTable, j);
 		        	  	
 		    	  }
 		    	  
 		    	Medicine clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);
 		    	
 	    		
 	    		if( e.getButton() == MouseEvent.BUTTON3) {
 	    			
 	    			String message = "Are you sure you want to delete this medicine from the list?\n";
 	    	        int returnValue = JOptionPane.showConfirmDialog(null, message,"Delete",JOptionPane.YES_NO_OPTION);
 	    	        
 	    	        if (returnValue == JOptionPane.YES_OPTION) { 	
 	    		        
 	    		    	order.deleteMedicineFronTheOrder(clickedMedicine);
 	 	    			
 	 	    			order.informHistory(fileNameForHistory);
 	 	    			
 	 	    			String medCode = "";
 	 	    			
 	 	    			for(int i=0;i<Storage.getMedicineList().size();i++) {
 	 	    				
 	 	    				medCode = (String) storageTable.getModel().getValueAt(i,1);
 	 	    				
 	 	    				if(clickedMedicine.getCode().equals(medCode)) {
 	 	    					
 	 	    					DefaultTableModel model = (DefaultTableModel)storageTable.getModel();
 	 	    					model.setValueAt(clickedMedicine.getAvailability(),i, 2);
 	 	    					
 	 	    					}
 	 	    				}
 	 	    			
 	 	    			
 	 	    			((DefaultTableModel) orderTable.getModel()).removeRow(selectedRowFromOrderTable);
 	 	    			
 	 	    			DecimalFormat df = new DecimalFormat("#.##");
 	 	    			
 	 	    			totalCost = String.valueOf(df.format(order.getTotalCost()));
 	 	     		    
 	 	   	    	  	textFieldForCost.setText(totalCost);
 	 	   	    	  	
 	 	   	    	  	for(int i=0;i<Storage.getMedicineList().size();i++) {
 	 	   	    	  		
 	 	   	    	  		int medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
 	 	   	    	  		storageTable.getModel().setValueAt(medicineAvailability, i, 2); 
 	 	   	    	  		
 	 	   	    	  	}
 	 	   	    	  }
 	    		    }
 	    		}	
 	    	
 	    } 	);
 	    
 	    
 	   
	    // Eisagwgi tou panel sto ContentPane
	
		this.setContentPane(panel);
		
		// Kathorismos basikwn xaraktiristikwn tou frame
		
		this.setSize(600,400);
		
		if( typeOfOrder == true)
			this.setTitle("Central Menu\\Order\\Prescription");
		else if(typeOfOrder == false)
			this.setTitle("Central Menu\\Order\\Prescription");
		
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

}




