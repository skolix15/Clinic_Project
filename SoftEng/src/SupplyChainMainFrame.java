import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
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
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SupplyChainMainFrame extends JFrame{
	
	private JPanel panel;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JTable storageTable;
	private JLabel title;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private DefaultTableModel model;
	private db conn;
	
   
	public SupplyChainMainFrame(db connection) {
	 
		conn=connection;
	 
		// Dimiourgia baras menu
		
		mb = new JMenuBar();
		
		File file = new File("History For Prescription.txt");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		if(sdf.format(file.lastModified()) != PrescriptionOrdersTemporalBase.getDate()) {
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
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
	    
	    // Prosthiki eikonas kai listener gia aythn
		
	 		ImageIcon icon = new ImageIcon("hospital1.png");
	 		JLabel lb = new JLabel(icon);
	 	
	 		lb.addMouseListener(new MouseAdapter() {
	 			public void mouseClicked(MouseEvent e) 
	 		    {	 
	 				dispose();
	 				new GlobalHomeFrame(conn);        
	 		    }
	 			
	 		}	);
	    
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
	    
	    // Gemisma toy pinaka apothiki me ta farmaka pou periexei h apothiki
	    
	    model = new DefaultTableModel();
	    model.addColumn("Id");
	    model.addColumn("Name");
	    model.addColumn("Availability");
	    String medicineName = null;
	    String medicineId = null;
	    int medicineAvailability = -1;
	    
	    for(int i=0;i<Storage.getMedicineList().size();i++) {
	    	medicineName = Storage.getMedicineList().get(i).getName();
	    	medicineId = Storage.getMedicineList().get(i).getId();
	    	medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
	    	model.addRow(new Object[] {medicineId,medicineName,medicineAvailability});
	    }				 
	    
	    // Dimiourgia tou pinaka me basi tis parapanw stiles kai grammes
	      
	    storageTable = new JTable(model);
	    
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
	    
	    // Dimiourgia scrollPane gia ton pinaka 
	    
	    JScrollPane scrollPane = new JScrollPane(storageTable);
		scrollPane.setBounds(36, 37, 407, 79);
	    
	    // Dimiourgia titlou tou pinaka (apothiki)
		
	    title = new JLabel("STORAGE");
	    
	    // Kathorismos topothethshs tou titlou
	    
	    title.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    
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
	    panel.add(title);
	    panel.add(scrollPane);
	
	    panel.add(label, BorderLayout.WEST);
	    panel.add(filterText, BorderLayout.CENTER);
	 
	    // Eisagwgi tou panel sto ContentPane
	
		this.setContentPane(panel);
		
		// Kathorismos basikwn xaraktiristikwn tou frame
		
		this.setSize(600,400);
		this.setTitle("/Supply Chain/Pharmacist/Central Menu");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
	}

class JTablePopupMenuListener implements ActionListener {


	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Go to Central Menu")){
			
			dispose();
			new SupplyChainMainFrame(conn);
		}
		
		else if(e.getActionCommand().equals("Prescription") ) {
				
			dispose();
			new PrescriptionAndSupplyFrame(true, conn);
			
		}
		
		else if(e.getActionCommand().equals("Supply")){
			
			dispose();
			new PrescriptionAndSupplyFrame(false, conn);
			
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


