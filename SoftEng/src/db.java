import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class db{
	private static Connection myConn=null;
	private static String result = null;
	
	public db() throws SQLException{
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb19?useTimezone=true&serverTimezone=UTC", "root" , "1234");
			
			//printTable ("drug", myConn);
			
			Doctor d = new Doctor ("Evag", "Myl", "dai18050", null, null);
			
			//addDoctor(d, myConn);
			//removeDoctor(d, myConn);
		//	result = returnPasswordUser(1, myConn);
			//printTable("doctor", myConn);
			
			
			//System.out.println(result);
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}	
		finally {
			if (myConn != null) {
			//	myConn.close();
			}
		}
		
		
	}
	
   
	
/*	public static void printTable (String name, ArrayList<Doctor> doctors, Connection myConn) {
		
	}
	*/
	
	
	
	/*  Input: A list that will contain all the data from all the doctors
	 * 		   and a connection with the database
	 *  Function: Gets all the doctors from the database and all their data a
	 *            and puts them in the ArrayList that was sent as input
	 *  Output: The list that contains the doctors
	 */
	public static void getAllDoctors(ArrayList<Doctor> doctors, Connection myConn)
	{
		Statement myStmt = null;
		ResultSet myRs = null;
		String Query;
		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select * from doctor";
			myRs = myStmt.executeQuery(query);
			
			// 4. Process the result set and put the data in the doctor list
			Doctor d;
			while (myRs.next()) {
				
				
				d = new Doctor(myRs.getString("First Name"), myRs.getString("Last Name"), myRs.getString("RN")
						, myRs.getString("Password"),  myRs.getString("Timetable"));
				
				// add the doctor to the doctor's list
				doctors.add(d);
			}
		}catch (Exception exc) {
				exc.printStackTrace();
			}finally {
				if (myRs != null) {
					try {
						myRs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (myStmt != null) {
					try {
						myStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}	
	}

	
	/* Input: The name of the table, the field to count, the value of the condition and a connection
	 * Function: counts how many entries exist in the given table that satisfies a certain condition
	 * Output: returns: -1 in the case of an error
	 * 					a positive integer that states the number of satisfied entries 
	 */
	public static int getNumberOfEntries(String table, String field, String value, Connection myConn)
	{
		int Number = -1; 
		 
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			
			//create and send the query
			String query = "select count(*) as Number from " + table + " where " + field + " = " + value;
			myRs = myStmt.executeQuery(query);
			
			// get the result of the database
			while(myRs.next())
				Number = myRs.getInt("Number");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Number;
	}
	
	/* INPUT: the doctor to be inserted to the database and a connection with the database
	 * Function: inserts  doctor to the database with null values in password and timetable
	 * Output: -----------------------
	 */
	 public static void addDoctor (Doctor d, Connection myConn) {
		Statement myStmt = null;
		ResultSet myRs = null;
			
		
		try {
			myStmt = myConn.createStatement();

			myStmt.executeUpdate("Insert into doctor (`First Name`, `Last Name`, `RN`)  Values ('" + d.firstName + "', '" + d.lastName + "', '" + d.rn + "')");
				
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (myRs != null) {
				try {
					myRs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	 }
	 
	 public static void removeDoctor (Doctor d, Connection myConn) {
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
				
			
			try {
				myStmt = myConn.prepareStatement("delete from doctor where RN = ?");
				
				myStmt.setString(1, d.rn);
				
/*				//2. Create a statement
				//myStmt = myConn.createStatement();
				//3. Execute SQL query
				//String query = "delete from doctor where RN = " + d.rn;
*/		
				myStmt.executeUpdate();

									
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
				if (myRs != null) {
					try {
						myRs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
				if (myStmt != null) {
					try {
						myStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 }
	 
	 
	 public static String returnDoctorPassword(String AM, Connection myConn)
	 {
			Statement myStmt = null;
			ResultSet myRs = null;
			String pass=null;
			
			try {
				myStmt = myConn.createStatement();
				// 3. Execute SQL query
				String query = "select Password from doctor where RN = " + AM ;
				myRs = myStmt.executeQuery(query);
				
				while (myRs.next())
					pass = myRs.getString("Password");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return pass;
	 }
	 
	 
	 /* Input: the code of the wanted entity (1: manager, 2: pharmacist) and a database connection
	  * Function: gets the password of the wanted entity
	  * Output: the password
	  */
	 public static String returnPasswordUser (int code, Connection myConn) {
			
			Statement myStmt = null;
			ResultSet myRs = null;
			String pass=null;
			
			try {
				myStmt = myConn.createStatement();
				// 3. Execute SQL query
				String query = "select Password from privileged_user where Code = " + code;
				myRs = myStmt.executeQuery(query);
				
				if(myRs.next())
				    pass = myRs.getString("Password");
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
				if (myRs != null) {
					try {
						myRs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (myStmt != null) {
					try {
						myStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return pass;
		}



	public static Connection getMyConn() {
		return myConn;
	}

	public static String getResult() {
		return result;
	}

	 
	 

}
