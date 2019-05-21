import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
	
   
	
	public static void printTable (String name, Connection myConn) {
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select * from " + name;
			myRs = myStmt.executeQuery(query);
			
			// 4. Process the result set
//			while (myRs.next()) {
//				System.out.println(myRs.getString("First Name") + ", " + myRs.getString("Last Name") + ", " + myRs.getString("RN") + 
//				", " + myRs.getString("Password") + ", " + myRs.getString("Timetable"));
//			}			
			
			ResultSetMetaData rsmd = myRs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			while (myRs.next()) {
				for (int i = 1; i <= columnCount; i++ ) {
					  String n = rsmd.getColumnName(i);
					  System.out.print(myRs.getString(n) + "       \t");
					  
					}
				System.out.println();
			}	

			  
			
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
	
	
	
	 public static void addDoctor (Doctor d, Connection myConn) {
		Statement myStmt = null;
		ResultSet myRs = null;
			
		
		try {
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("Insert into doctor (`First Name`, `Last Name`, `RN`, `Password`, `Timetable`)  Values ('" + d.firstName + "', '" + d.lastName + "', '" + d.rn + "', '" + d.password + "', '" + d.timetable + "')");;
							
			printTable("doctor", myConn);
				
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
				
				//2. Create a statement
				//myStmt = myConn.createStatement();
				//3. Execute SQL query
				//String query = "delete from doctor where RN = " + d.rn;
				myStmt.executeUpdate();
				
				printTable("doctor", myConn);
					
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
	 
	 public static String returnPasswordUser (int code, Connection myConn) {
			
			Statement myStmt = null;
			ResultSet myRs = null;
			String pass=null;
			
			try {
				myStmt = myConn.createStatement();
				// 3. Execute SQL query
				String query = "select Password from privileged_user where Code=" + code;
				
				System.out.println("select Password from privileged_user where Code=" + code);
				myRs = myStmt.executeQuery(query);
				
				if(myRs.next())
				    pass = myRs.getString("Password");
				
				//System.out.println(pass);
				//4. Process the result set
//				while (myRs.next()) {
//					System.out.println(myRs.getString("Password"));
//				}
				
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
