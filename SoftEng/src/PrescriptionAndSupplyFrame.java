import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	private db conn;
   
	public PrescriptionAndSupplyFrame(boolean aTypeOfOrder, db connection) {
		conn=connection;
		// Dimiourgia button gia epikirwsi
		
		confirmButton = new JButton("Confirm Order");
		
		/*Prosthiki listener sto confirmButton
		  Me thn energopoihsh ths epilogis "Confirm Order" adeiazei o pinakas ths paraggelias 
		  kai taytoxrona enhmerwnetai to katallilo arxeio me tous kwdikous, ta onomata kai tis posotites
		  twn farmakwn pou yphrxan sthn paraggelia*/
		
		confirmButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
				
				order.informHistory(fileNameForHistory);
				DefaultTableModel dm = (DefaultTableModel) orderTable.getModel();
				int rowCount = dm.getRowCount();
				
				//Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
				    dm.removeRow(i);
				}
				
				textFieldForCost.setText("0");
				order.clearOrder();
	
			}
		});
		
		 /*Prosthiki eikonas kai listener gia aythn
		 Me thn energopoihsh ths epilogis ths eikonas to programma metaferetai sto arxiko
		 parathyro tou programmatos*/
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
	
		lb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	dispose();
		    	new GlobalHomeFrame(conn);   ;           
		        // kanonika new BasicGui();
		    }
			
		}	);
		
		
		// Dimiourgia tou text poy tha periexei to synoliko kostos
		
		textFieldForCost = new JTextField("0");
		
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
	    mb.add(lb);
	    
	    // Dimiourgia model kai sthlwn-kathgoriwn gia tous 2 pinakes ( apothikis kai paraggelias )
	    
	    storageModel = new DefaultTableModel();
	    basketModel = new DefaultTableModel();
	    
	    storageModel.addColumn("Code");
	    storageModel.addColumn("Name");
	    storageModel.addColumn("Availability");
	    
	    basketModel.addColumn("Code");
	    basketModel.addColumn("Name");
	    basketModel.addColumn("Quantity");
	    
	 // Eisagwgi twn farmakwn pou briskontai sthn apothiki tou iatreiou, ston pinaka pou antistoixizetai se aythn
	    
	    String medicineName = null;
	    String medicineCode = null;
	    int medicineAvailability = -1;
	    
	    for(int i=0;i<Storage.getMedicineList().size();i++) {
	    	
	    	medicineName = Storage.getMedicineList().get(i).getName();
	    	medicineCode = Storage.getMedicineList().get(i).getId();
	    	medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
	    	storageModel.addRow(new Object[] {medicineCode,medicineName,medicineAvailability});
	    }
	    
	    // Dimiourgia tou pinaka me basi tis parapanw stiles kai grammes (parathetontas san parametro ta model)
	    
	    storageTable = new JTable(storageModel);
	    orderTable = new JTable(basketModel);
	    
	    // Dimiourgia search baras
	   
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
   	
 	    	 int selectedRow = storageTable.getSelectedRow();
 	    	 
 	    	 if(selectedRow != -1) {
 	    		
 	    	 // Apothikeyetai se metablites o kwdikos kai to onoma tou farmakou pou epilexthike apo ton prwto pinaka (apothikis)
 	    	
 	    	 medicineCode = (String) storageTable.getModel().getValueAt(selectedRow,0);
 	        
 	         medicineName = (String) storageTable.getModel().getValueAt(selectedRow, 1);
 	        	  	
 	    	  
 	    	  // Dimiourgeitai antikeimeno typou medicine me xrisi twn 2 parapanw metablitwn
 	       
 	    	  Drug clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);		
 	    	  
 	    	 
 	    	  // Elegxetai an to dhmiourgimeno farmako yparxei mesa sthn lista ths twrinis paraggelias
 		    
 	    	  if(!order.searchForMedicineInOrder( clickedMedicine.getId())) {
 	    		  
 	    		  // Protrepei ton xristi na plhktrologisei ena noumero pou tha apotelesei thn posotita tou farmakou
 	    		  // pou tha eisagei sthn lista paraggelias
 	    		  
 	    		  String inputAvailabilityString = JOptionPane.showInputDialog(null,"Enter quantity of the medicine you want to buy: ");
 	    		  
 	    		  // Elegxos gia thn periptwsh pou o xristis den plhktrologisei akeraio arithmo
 	    		  
 	    		  if(inputAvailabilityString == null || inputAvailabilityString.equals("") )
 	    			  JOptionPane.showMessageDialog(null,"You dind't enter any number.","Error..",JOptionPane.ERROR_MESSAGE);
 	    		  
 	    		  else if(!PrescriptionAndSupplyFrame.isNumeric(inputAvailabilityString)) 
 	    			 JOptionPane.showMessageDialog(null,"You have to enter an integer number.","Error..",JOptionPane.ERROR_MESSAGE);

 	    		  else {
 	    			  
 	    			  //Metatropi ths posotitas poy plhktrologithike apo String se Integer
 	    			  
 	    			  int inputAvailabilityInteger = Integer.parseInt(inputAvailabilityString);
 	    			  
 	    			  // Elegxos an h posotita poy plhktrologithike einai mikroteri tou midenos
 	    			  
 	    			  if(inputAvailabilityInteger <= 0) 
 	    				  JOptionPane.showMessageDialog(null,"Invalid quantity of medicine.","Error..",JOptionPane.ERROR_MESSAGE);
 	    			  
 	    			  // Elegxos an h posotita pou plhktrologithike einai egyri me basi to apothema poy yparxei sto iatreio 
 	    			  
 	    			  else if(typeOfOrder == true && !(clickedMedicine.getAvailability() >= inputAvailabilityInteger))
	    			  		JOptionPane.showMessageDialog(null,"Not enough stocks for this medicine.","Error..",JOptionPane.ERROR_MESSAGE);
 	    			  
 	    			  else  {
 	    				  
 	    				  	// Prosthithetai to epilegmeno farmako (apo thn apothiki ) sthn lista paraggelias
 	    		  
 	    			  		order.addMedicineInTheOrder(clickedMedicine, inputAvailabilityInteger); 	
 	    			  		
 	    			  		// Periorizoyme to total cost pou tha emfanizetai sto na exei 2 mono dekadika psifia
 	    			  		
 	    			  		DecimalFormat df = new DecimalFormat("##.##");
	    			
 	    			  		totalCost = String.valueOf(df.format(order.getTotalCost()));
	     		    
 	    			  		textFieldForCost.setText(totalCost);
 		    
 	    			  		DefaultTableModel modelForOrderTable = (DefaultTableModel) orderTable.getModel();
 	    			  		
 	    			  		// Prostithetai to epilegmeno farmako (apo thn apothiki) ston neo pinaka (ths paraggelias)
 	    			  		
 	    			  		modelForOrderTable.addRow(new Object[]{clickedMedicine.getId(), clickedMedicine.getName(), inputAvailabilityInteger}); 
 	    			  		
 	    			  		// Metatrepoume katallila thn diathesimotita toy farmakou pou mpike sthn paraggelia
 	    			  		
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

 	    // Listener gia thn diagrafi farmakou apo ton pinaka paraggelias
 	    
 	    orderTable.addMouseListener(new MouseAdapter() {
 	    	
 	    	
 	    	public void mousePressed (MouseEvent e) {
 	    		
 	    		  String medicineName = "";
 		    	  String medicineCode = "";
 		    	  String totalCost = "";
 		    	  
 		    	 // Entopisi tou farmakou pou epilexthike

 		    	 int selectedRowFromOrderTable = e.getY()/orderTable.getRowHeight();
 		    	  
 		    	 // Katagrafi twn stoixeiwn (code,name) tou farmakou pou epilexthike
 		    	 
 		    	 for (int j = 0; j < 2; j++) {
 		        	  
 		    		 
 		        	  	if( j==0 )
 		        	  		medicineCode = (String) orderTable.getModel().getValueAt(selectedRowFromOrderTable,j);
 		        	  	else if( j==1 )
 		        	  		medicineName = (String) orderTable.getModel().getValueAt(selectedRowFromOrderTable, j);
 		        	  	
 		    	  }
 		    	  
 		    	// Dimiourgia antikeimenou typou Medicine
 		    	 
 		    	Drug clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);
 		    	
 	    		// MouseEvent.BUTTON3 gia na elecsei an patithike decsi klik apo ton xristi
 		    	
 	    		if( e.getButton() == MouseEvent.BUTTON3) {
 	    			
 	    			// Emfanisi katallilou minimatos gia to an o xristis ontws epithymei na diagrapsei to epilegmeno
 	    			// farmako apo thn lista paraggelias
 	    			String message = "Are you sure you want to delete this medicine from the list?\n";
 	    	        int returnValue = JOptionPane.showConfirmDialog(null, message,"Delete",JOptionPane.YES_NO_OPTION);
 	    	        
 	    	        // Elegxos an patithei h epilogi "yes" apo ton xristi
 	    	        
 	    	        if (returnValue == JOptionPane.YES_OPTION) { 	
 	    		        
 	    	        	// Diagrafetai to farmako apo thn lista paraggelias
 	    	        	
 	    		    	order.deleteMedicineFronTheOrder(clickedMedicine);
 	 	    			
 	 	    			String medCode = "";
 	 	    			
 	 	    			// Metatrepetai katallila (ston pinaka apothikis ) h diathesimothta tou farmakou pou epilexthike 
 	 	    			//na diagrafei apo ton pinaka paraggelias
 	 	    			 
 	 	    			for(int i=0;i<Storage.getMedicineList().size();i++) {
 	 	    				
 	 	    				medCode = (String) storageTable.getModel().getValueAt(i,1);
 	 	    				
 	 	    				if(clickedMedicine.getId().equals(medCode)) {
 	 	    					
 	 	    					DefaultTableModel model = (DefaultTableModel)storageTable.getModel();
 	 	    					model.setValueAt(clickedMedicine.getAvailability(),i, 2);
 	 	    					
 	 	    					}
 	 	    				}
 	 	    			
 	 	    			// Diagrafetai to farmako pou epilexthike apo ton pinaka paraggelias
 	 	    			
 	 	    			((DefaultTableModel) orderTable.getModel()).removeRow(selectedRowFromOrderTable);
 	 	    			
 	 	    			// Periorizoyme to total cost pou tha emfanizetai sto na exei 2 mono dekadika psifia
 	 	    			
 	 	    			DecimalFormat df = new DecimalFormat("#.##");
 	 	    			
 	 	    			totalCost = String.valueOf(df.format(order.getTotalCost()));
 	 	     		    
 	 	   	    	  	textFieldForCost.setText(totalCost);
 	 	   	    	  	
 	 	   	    	  	// Metatrepetai katallila h diathesimotita tou farmakou pou epilexthike na diagrafei apo ton pinaka paraggelias
 	 	   	    	  	
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
			this.setTitle("/Supply Chain/Pharmacist/Order/Prescription");
		else if(typeOfOrder == false)
			this.setTitle("/Supply Chain/Pharmacist/Order/Supply");
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
		
		
		}
	
	// Methodos pou epistrefei boolean timi analoga, an ena string einai Integer h oxi
	public static boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
// Listener gia thn katallili metafora parathirwn tou programmatos analoga thn epilogi tou xristi apo to menu bar
	
class JTablePopupMenuListener implements ActionListener {


		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Go to Central Menu")){
				
				dispose();
				new SupplyChainMainFrame(conn);
			}
			
			else if(e.getActionCommand().equals("Prescription") ) {
					
				dispose();
				new PrescriptionAndSupplyFrame(true,conn);
				
			}
			
			else if(e.getActionCommand().equals("Supply")){
				
				dispose();
				new PrescriptionAndSupplyFrame(false,conn);
				
			}
			
			else if(e.getActionCommand().equals("Add")) {
				
				dispose();
				new AddFrame(conn);
			}
			
			else if(e.getActionCommand().equals("Delete")) {
				
				dispose();
				new DeleteFrame(conn);
			}
			
			else if(e.getActionCommand().equals("Show Statistics")) {
				
				dispose();
				new StatisticsFrame(conn);
			}
			
		}
			
	 }

}




