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
	private JTable doctorsTable, program;
	private JLabel hrLabel;
	private db conn ;
	
	private DefaultTableModel model, model2;

	
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
		conn.getAllDoctors(doctors, conn.getMyConn());
		
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
			
			    doctorsTable = new JTable(model);
				
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(doctorsTable.getModel());
			    doctorsTable.setRowSorter(sorter);
				
			    
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
				
				doctorsTable.setAlignmentX(Component.LEFT_ALIGNMENT);
			  
				// Create ScrollPane for the table
				   
			    scrollPane = new JScrollPane(doctorsTable);
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
				    	  JTextField amField = new JTextField("ал");
				    	  
				       	  
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
							    	  NumberOfDocs =  conn.getNumberOfEntries("doctor", "RN", AM, conn.getMyConn());
					
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
					    	  			  conn.addDoctor(d, conn.getMyConn());
					    	  			  // add the doctor in ArrayList doctors 
					    	  			  doctors.add(d);
					    	  			  setVisible(false);
					    	  			  new ManagerHomePageFrame(conn);
				    	  			  }	
							      }
						    });
				    	  
				    	 
				    	 
				       	  
				      }
			    });
				
				 //remove -> button to remove an employee
		    	  remove.addActionListener(new ActionListener()
				    {	
					      public void actionPerformed(ActionEvent e){
					    	  
					    	  
					    	  DefaultTableModel model =
					    			  (DefaultTableModel)doctorsTable.getModel();
					    			model.removeRow(doctorsTable.getSelectedRow());
					      }
					      
					   });
		    	  
				
				
				
				doctorsTable.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mousePressed (MouseEvent e) {
		 	    		
		 	    		 String firstName = "";
		 	    		 String lastName = "";
		 	    		 String rn="";

		 		    	 int selectedRowFromOrderTable = e.getY()/doctorsTable.getRowHeight();
		 		    	  
		 		    	  
		 		    	 
		 		    	 for (int j = 0; j < 3; j++) {
		 		        	  
		 		    		    if( j==0 )
		 		        	  		lastName = (String) doctorsTable.getModel().getValueAt(selectedRowFromOrderTable,j);
		 		        	  	else if( j==1 )
		 		        	  		firstName = (String) doctorsTable.getModel().getValueAt(selectedRowFromOrderTable, j);
		 		        	  	else {
		 		        	  		rn= (String) doctorsTable.getModel().getValueAt(selectedRowFromOrderTable, j);
		 		        	  	}
		 		    	  }
		 		    	  
			 		    Doctor clickedDoct = Doctor.searchDoctor(lastName, firstName, rn, doctors);
			 		   
			 		  

		 	    		
		 	    		if( e.getButton() == MouseEvent.BUTTON2) {
		 	    			
		 	    			String message = "Would you like to delete this medicine from the list?\n";
		 	    	        int returnValue = JOptionPane.showConfirmDialog(null, message,"Delete",JOptionPane.YES_NO_OPTION);
		 	    	        
		 	    	        if (returnValue == JOptionPane.YES_OPTION) {
		 	    			
		 	    			
		 	    			String doctCode = "";
		 	    			
		 	    			for(int i=0; i<doctors.size(); i++) {
		 	    				
		 	    				doctCode = (String) doctorsTable.getModel().getValueAt(i,2);
		 	    				
		 	    				if(doctors.get(i).getRn().equals(doctCode)) {
		 	    					
		 	    					DefaultTableModel model = (DefaultTableModel)doctorsTable.getModel();
		 	    					//model.setValueAt(clickedMedicine.getAvailability(),i, 2);
		 	    					
		 	    					}
		 	    				}
		 	    			
		 	    			
		 	    			((DefaultTableModel) doctorsTable.getModel()).removeRow(selectedRowFromOrderTable);
		 	   	    	  	
		 	   	    	  /*	for(int i=0;i<Storage.getMedicineList().size();i++) {
		 	   	    	  		
		 	   	    	  		int medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
		 	   	    	  		storageTable.getModel().setValueAt(medicineAvailability, i, 2);
		 	   	    	  		
		 	   	    	  	}*/
		 	    	       }
		 	    	      }
		 	            }
		 	    	
		 	    	} 	);
				
				
				
				
				
				
			}
			//Button: Program-> Create
			else if(e.getSource() == create) {
				JLabel label= new JLabel();
				JLabel text1 = new JLabel("Number of doctors in db: __");//Arithmos apo vasi
				JButton show_program = new JButton("Show Program");
				JButton cancel_but = new JButton("Cancel");
				
			
		
				
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
			    	 
			    	  model2 = new DefaultTableModel();
			  		  model2.addColumn("Schedule");
			  		  model2.addColumn("Monday");
			  		  model2.addColumn("Tuesday");
			  		  model2.addColumn("Wednesday");
			  		  model2.addColumn("Thursday");
			  		  model2.addColumn("Friday");
			  		  model2.addColumn("Saturday");
			  		  model2.addColumn("Sunday");
			  		  model2.addRow( new Object[] {"06:00-14:00", "-" , "-", "-", "-", "-", "-", "-" });
			  		  model2.addRow( new Object[] {"14:00-22:00", "-" , "-", "-", "-", "-", "-", "-" });
			  		  model2.addRow( new Object[] {"22:00-00:00", "-" , "-", "-", "-", "-", "-", "-" });
			  		  program = new JTable(model2);
			  		
			  		  program.setAlignmentX(Component.LEFT_ALIGNMENT);
			  
		
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