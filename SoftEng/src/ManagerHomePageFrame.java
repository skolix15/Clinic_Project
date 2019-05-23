import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class ManagerHomePageFrame extends JFrame {
	
	private JPanel centralPanel;
	private JPanel mainPanel;
	private JPanel secondPanel;
	
	private JMenuBar menubar;
	private JMenu employeeMenu;
	private JMenu programMenu;
	private JMenuItem change,create;
	
	private JButton add, remove;
	private JTextField FindField;
	private JScrollPane scrollPane, scrollPane2;
	private JTable table, program;
	private JLabel hrLabel;
	private db conn;
	
	private DefaultTableModel model;

	
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>(); 
	// this list contains all the doctors

	public ManagerHomePageFrame(db connection) {
		conn= connection;
		centralPanel = new JPanel(new BorderLayout());
		mainPanel= new JPanel();
		secondPanel= new JPanel();
		
		menubar= new JMenuBar();
		employeeMenu= new JMenu("Employee");
		programMenu= new JMenu("Program");
		
		change= new JMenuItem("Change");
		create= new JMenuItem("Create");
		
		employeeMenu.add(change);
		programMenu.add(create);
		
		menubar.add(employeeMenu);
		menubar.add(programMenu);
		
		mainPanel.add(menubar);
		
		// Gets all the doctors from the database and puts them in the ArrayList doctors
		db.getAllDoctors(doctors, conn.getMyConn());
		
		// Insert the doctors to the JTable
		
		
		//Insert image to return at the Home Page
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
		
		
		ButtonListener listener = new ButtonListener();
		change.addActionListener(listener);
		create.addActionListener(listener);
		
		centralPanel.add(mainPanel, BorderLayout.NORTH);
		centralPanel.add(secondPanel, BorderLayout.CENTER);
		
		this.setContentPane(centralPanel);
		this.setVisible(true);
		this.setSize(600, 600);
		this.setTitle("Manager/Shifts");
		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		
			secondPanel.removeAll();
			
			//Button: Employee->Change
			if(e.getSource()== change ) {
				
				hrLabel = new JLabel ("Workforce availability");
			
				
				// get all doctors from the database
				
				model = new DefaultTableModel();
				model.addColumn("First Name");
				model.addColumn("Last Name");
				model.addColumn("RN");
				String firstname_db=null; 
				String lastname_db=null;
				String rn_db=null;
				
				for(Doctor doct: doctors) {
					firstname_db= doct.firstName;
					lastname_db=doct.lastName;
					rn_db=doct.rn;
					model.addRow(new Object[] {firstname_db, lastname_db, rn_db});
				}
			
			    table = new JTable(model);
				
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
			    table.setRowSorter(sorter);
				
			    
				FindField= new JTextField("Search Employee"); 
				
				add =new JButton("Add"); 
				remove =new JButton("Remove");
				
				//Search Employee in all Fields (LastName, FirstName, RN)
	
				FindField.getDocument().addDocumentListener(new DocumentListener() {

					public void changedUpdate(DocumentEvent e) {
						throw new UnsupportedOperationException("Not supported yet.");
					}

					public void insertUpdate(DocumentEvent e) {
						
						String text = FindField.getText();

		                if (text.trim().length() == 0) {
		                    sorter.setRowFilter(null);
		                } else {
		                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                }
						
					}

					public void removeUpdate(DocumentEvent e) {
						
						String text = FindField.getText();

		                if (text.trim().length() == 0) {
		                    sorter.setRowFilter(null);
		                } else {
		                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                }
					}
				});
				
				table.setAlignmentX(Component.LEFT_ALIGNMENT);
			  
				// Create ScrollPane for the table
				   
			    scrollPane = new JScrollPane(table);
				scrollPane.setBounds(36, 37, 407, 79);
			
				secondPanel.add(hrLabel);
				secondPanel.add(scrollPane);
				secondPanel.add(FindField);
				secondPanel.add(add);
				secondPanel.add(remove);
				pack(); 
				
				
				//Add a new employee in database
				add.addActionListener(new ActionListener()
			    {	
				      public void actionPerformed(ActionEvent e)
				      {
				    	  
				    	  secondPanel.removeAll();
				    	//Insert image to return at the Home Page
				  		 ImageIcon icon = new ImageIcon("back1.png");
				  		 JLabel lb = new JLabel(icon);
				  		 mainPanel.add(lb);
				  		
				  		 lb.addMouseListener(new MouseAdapter() 
				  		 {
				  		 	public void mouseClicked(MouseEvent e) 
				  		    {	 
				  		    	setVisible(false);
				  		        new ManagerHomePageFrame(conn);           
				  		    }
				  		  });
				    	  JLabel label = new JLabel("Create a New Employee");
				    	  JButton add_employee = new JButton("Add Employee");
				    	  JTextField firstNameField = new JTextField("First Name");
				    	  JTextField lastNameField = new JTextField("Last  Name");
				    	  JTextField amField = new JTextField("ΑΜ");
				    	  String AM, firstName, lastName;
				    
				    	  
				    	  secondPanel.add(label, BorderLayout.NORTH);
				    	  secondPanel.add(firstNameField, BorderLayout.CENTER);
				    	  secondPanel.add(lastNameField, BorderLayout.CENTER);
				    	  secondPanel.add(amField, BorderLayout.CENTER);
				   
				    	  secondPanel.add(add_employee, BorderLayout.CENTER);
				    	  pack();
				    	  
				    	  //add_employee -> Button to add a new employee
				    	  add_employee.addActionListener(new ActionListener()
						    {	
							      public void actionPerformed(ActionEvent e)
							      { 
							    	 
							    	  String AM, firstName, lastName;
							    	  int NumberOfDocs;
							    	  
							    	  firstName = firstNameField.getText();
							    	  lastName = lastNameField.getText();
							    	  AM = amField.getText();
							    	  
							    	  // ----------SEARCH IF THE DOCTOR ALREADY EXISTS------------
							    	  //check if the typed AM already exists
							    	  NumberOfDocs =  db.getNumberOfEntries("doctor", "RN", AM, conn.getMyConn());
					
				    	  			  if (NumberOfDocs == -1)
				    	  			  {
				    	  				 JOptionPane.showMessageDialog(secondPanel, "Error");
				    	  			  }
				    	  			  else if (NumberOfDocs == 1 )
				    	  			  {
				    	  				// show that the doctor with the given RN already exists
				    	  				  JOptionPane.showMessageDialog(secondPanel, " The doctor with the given RN already exists");
				    	  				  
				    	  			  }
				    	  			  else
				    	  			  {
								    	  //-----------INSERT DOCTOR-----------
					    	  			  Doctor d = new Doctor(firstName, lastName, AM);
					    	  			  
					    	  			  // add the doctor in database 
					    	  			  db.addDoctor(d, conn.getMyConn());
					    	  			  // add the doctor in ArrayList doctors 
					    	  			  doctors.add(d);
					    	  			  setVisible(false);
					    	  			  new ManagerHomePageFrame(conn);
				    	  			  }	
							      }
						    });
				    	  
				    	  //remove -> button to remove an employee
				    	  remove.addActionListener(new ActionListener()
						    {	
						      public void actionPerformed(ActionEvent e)
						      {		
						    	  //Remove employee from database
						    	  
						  
						      }
					    });
				    	  
				      }
			    });
				
			}
			//Button: Program-> Create
			else if(e.getSource() == create) {
				JLabel label= new JLabel();
				JTextField text1 = new JTextField("Number of doctors in db");
				JButton show_program = new JButton("Show Program");
				JButton cancel_but = new JButton("Cancel");
				
				//text1.setText(); Πλήθος γιατρών, το παίρνει από την βάση
		
				
				label.setText("Create - Export TimeTable");
				secondPanel.add(label, BorderLayout.NORTH);
				secondPanel.add(text1, BorderLayout.CENTER);
				secondPanel.add(show_program, BorderLayout.SOUTH);
				secondPanel.add(cancel_but, BorderLayout.SOUTH);
				pack();
				
				show_program.addActionListener(new ActionListener()
			    {	
			      public void actionPerformed(ActionEvent e)
			      {
			    	  secondPanel.removeAll();
			    	  JLabel label= new JLabel();
			    	  JButton save = new JButton("Save");
			    	  JButton amendment = new JButton("Amendment");
			    	  
			    	  label.setText("Shift's Program ");
			    	 
			    	  Object[] columnNames = {"Schedule" , "Monday" , "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
			  	      Object[][] rowData = { {"06:00-14:00", "-" , "-", "-", "-", "-", "-", "-" },
			  	    					   {"14:00-22:00", "-", "-", "-", "-", "-", "-", "-"},
			  	    					   {"22:00-06:00", "-", "-", "-", "-", "-", "-", "-"} };
			    	 
			    	 
			  	       program = new JTable(rowData,columnNames);
			  
		
			  	       program.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			  	       // Create ScrollPane for the table
			   
			  	       scrollPane2 = new JScrollPane(program);
			  	       scrollPane2.setBounds(20, 20, 10, 30);
			  	      
			  	       
			    	   secondPanel.add(label);
			    	   secondPanel.add(scrollPane2);
			    	   secondPanel.add(save);	
			    	   secondPanel.add(amendment);
			    	   pack();
			    	   
			    	   save.addActionListener(new ActionListener()
					    {	
					      public void actionPerformed(ActionEvent e)
					      {
					    	 // APOTHIKEUSH STIN VASI DEDOMENWN
					    	   
					      }
					    });
			    	  
			    	   amendment.addActionListener(new ActionListener()
					    {	
					      public void actionPerformed(ActionEvent e)
					      {
					    	 //Ston idi uparxonta pinaka. TON kanw na mporei na metavlithei
					    	   
					      }
					    });
			    	   
			    	  }
			    });
				
				cancel_but.addActionListener(new ActionListener()
			    {	
			      public void actionPerformed(ActionEvent e)
			      {	  
			    	  
			    	  setVisible(false);
		  		      new ManagerHomePageFrame(conn);
			    	
			      }
			    });

			}
	
		}	
	}
}