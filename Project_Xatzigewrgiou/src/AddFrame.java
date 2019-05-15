import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class AddFrame extends JFrame{
	
	private JPanel panel;
	private JTextField code;
	private JTextField name;
	private JTextField price;
	private JButton confirm;
	private JLabel title;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private JSpinner spinner;
	
	
	public AddFrame () {
		
		//Δημιουργία panel
		panel = new JPanel();
		menuPanel = new JPanel();
		
		//Δημιουργία τίτλου για το παράθυρο
	    title = new JLabel("Προσθήκη νέου φαρμάκου");
	    title.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//Δημιουργία πεδίων για εισαγωγή τιμών απο χρήστες
		code = new JTextField("");
		code.setBorder(new TitledBorder("Κωδικός"));
		code.setPreferredSize(new Dimension(75,4));
		price = new JTextField("");
		price.setBorder(new TitledBorder("Τιμή"));
		price.setPreferredSize(new Dimension(75,4));
		name = new JTextField("");
		name.setBorder(new TitledBorder("Ονομασία Φαρμάκου"));
		name.setPreferredSize(new Dimension(75,4));
		//quantity = new JTextField("Ποσότητα");
		
		//Δημιουργία για κουμπιών
		confirm = new JButton("Καταχώρηση");
		ButtonListenerConfirm confirmListener= new ButtonListenerConfirm();
		confirm.addActionListener(confirmListener);
		
		
		//Δημιουργία μπάρας μενού
		mb = new JMenuBar();
		
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
	    
	    //JSpinner
	    spinner = new JSpinner();
	    spinner.setPreferredSize(new Dimension(75, 50));
	    spinner.setBorder(new TitledBorder("Ποσότητα"));
	    
		
	    //Eισαγωγή στο μενού
	    centralMenu_Menu.add(iCentralMenu);
	    orderMenu.add(i1);
	    orderMenu.add(i2);
	    storageMenu.add(i3);
	    storageMenu.add(i4);
	    statisticsMenu.add(i5);
	    
	    //Εισαγωγή των μενού στην μπάρα
	    mb.add(centralMenu_Menu);
	    mb.add(orderMenu);
	    mb.add(storageMenu);
	    mb.add(statisticsMenu);
	    
	    //Τρόπος τοποθέτησης στα πάνελ
	    menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.X_AXIS));
	    menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    menuPanel.add(mb);
	    panel.add(menuPanel);
	    
	    
	    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
		//Προσθήκη στο panel
	    
		panel.add(title);	
		panel.add(code);
		panel.add(name);
		panel.add(price);
		panel.add(spinner);
		//panel.add(quantity);
		panel.add(confirm);
		
		
		
		
		//Εισαγωγή του panel στο contentpane
		
		this.setContentPane(panel);
		
		
		//Καθορισμός των βασικών χαρακτηριστικών του panel
		
		this.setSize(600,300);
		this.setTitle("/Εφοδιαστική_Αλυσίδα/Αποθήκη/Προσθήκη");
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
	
	class ButtonListenerConfirm implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
				
			String	codeText = code.getText();
			String	nameText = name.getText();
			String 	priceText = price.getText();
			String 	quantityText = spinner.getValue().toString();
			
			dispose();
			new AddFrame();
			
			String codeInt = codeText;
			double priceDouble = Double.parseDouble(priceText);
			int quantityInt = Integer.parseInt(quantityText);
			
			
			Storage.addMedicine(nameText, codeInt, priceDouble, quantityInt);			
			
		}
	}

}
