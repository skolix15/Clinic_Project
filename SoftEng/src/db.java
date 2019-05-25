import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class db {
	private Connection myConn = null;
	private String result = null;

	public db() throws SQLException {

		try {
			// Connect to the database
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mydb19?useTimezone=true&serverTimezone=UTC", "root", "1234");
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myConn != null) {
				// myConn.close();
			}
		}

	}

	/*
	 * Input: the name of the table that we want to print and the connection with
	 * the db server Function: Takes the data from the table and prints it on the
	 * console
	 */
	public static void printTable(String name, Connection myConn) {

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// 2. Create a Statement
			myStmt = myConn.createStatement();

			// 3. Execute SQL query
			String query = "select * from " + name;
			myRs = myStmt.executeQuery(query);

			// 4. Process the result set
//			while (myRs.next()) {
//				System.out.println(myRs.getString("First Name") + ", " + myRs.getString("Last Name") + ", " + myRs.getString("RN") + 
//				", " + myRs.getString("Password") + ", " + myRs.getString("Timetable"));
//			}			

			// 4. Process the result set
			ResultSetMetaData rsmd = myRs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			while (myRs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String n = rsmd.getColumnName(i);
					System.out.print(myRs.getString(n) + "\t\t");

				}
				System.out.println();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
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

	/*
	 * Function: gets the global timetable from the database
	 */
	public String returnTimetable() {
		Statement myStmt = null;
		ResultSet myRs = null;
		String timetable = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select ttable from timetable where id = 1";
			myRs = myStmt.executeQuery(query);

			while (myRs.next())
				timetable = myRs.getString("ttable");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timetable;
	}

	/*
	 * Input: The timetable as a String Function: Updates the
	 * 
	 */
	public void saveTimetable(String timetable) {
		Statement myStmt = null;

		try {
			myStmt = myConn.createStatement();
			String query = "update timetable set ttable = \"" + timetable + "\" where id = 1";

			myStmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Input: the name of a field, the value to be saved in that field and the value
	 * that satisfies the condition Function: updates-saves the given field with the
	 * given saveValue if the condition is satisfied
	 */
	public void saveFieldDoctor(String field, String saveValue, String conditionValue) {
		Statement myStmt = null;

		try {
			myStmt = myConn.createStatement();
			String query = "update doctor set " + field + " = \"" + saveValue + "\" where RN = \"" + conditionValue
					+ "\"";

			myStmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Input: A list that will contain all the data from all the doctors Function:
	 * Gets all the doctors from the database and all their data a and puts them in
	 * the ArrayList that was sent as input Output: The list that contains the
	 * doctors
	 */
	public void getAllDoctors(ArrayList<Doctor> doctors) {
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select * from doctor";
			myRs = myStmt.executeQuery(query);

			// 4. Process the result set and put the data in the doctor list
			Doctor d;
			while (myRs.next()) {

				d = new Doctor(myRs.getString("First Name"), myRs.getString("Last Name"), myRs.getString("RN"),
						myRs.getString("Password"), myRs.getString("Timetable"));

				// add the doctor to the doctor's list
				doctors.add(d);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
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

	/*
	 * Input: The name of the table Function: counts how many entries exist in the
	 * given table Output: returns: -1 in the case of an error a positive integer
	 * that states the number entries
	 */
	public int getNumberOfEntries(String table) {
		int Number = -1;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			// create and send the query
			String query = "select count(*) as Number from " + table;
			myRs = myStmt.executeQuery(query);

			// get the result of the database
			while (myRs.next())
				Number = myRs.getInt("Number");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Number;
	}

	/*
	 * Input: The name of the table, the field to count, the value of the condition
	 * Function: counts how many entries exist in the given table that satisfies a
	 * certain condition Output: returns: -1 in the case of an error a positive
	 * integer that states the number of satisfied entries
	 */
	public int getNumberOfEntriesWithCondition(String table, String field, String value) {
		int Number = -1;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			// create and send the query
			String query = "select count(*) as Number from " + table + " where " + field + " = \"" + value + "\"";
			myRs = myStmt.executeQuery(query);

			// get the result of the database
			while (myRs.next())
				Number = myRs.getInt("Number");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Number;
	}

	/*
	 * INPUT: the doctor to be inserted to the database Function: inserts doctor to
	 * the database with null values in password and timetable Output:
	 * -----------------------
	 */
	public void addDoctor(Doctor d) {
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			myStmt.executeUpdate("Insert into doctor (`First Name`, `Last Name`, `RN`)  Values ('" + d.firstName
					+ "', '" + d.lastName + "', '" + d.rn + "')");

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
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

	/*
	 * Input: The RN of the doctor that will be deleted Function: Deletes from the
	 * database the doctor with the given RN
	 */
	public void removeDoctor(String value) {
		Statement myStmt = null;

		try {
			// 2. Create a statement
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "delete from doctor where RN = \"" + value + "\"";

			myStmt.executeUpdate(query);
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
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

	/*
	 * Input: the AM of the doctor Function: Gets the personal password of the given
	 * doctor from the database Output: The password of the given doctor: null if a
	 * password hasn't been defined yet otherwise the password itself is returned
	 */
	public String returnDoctorPassword(String AM) {
		Statement myStmt = null;
		ResultSet myRs = null;
		String pass = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select Password from doctor where RN = \"" + AM + "\"";
			myRs = myStmt.executeQuery(query);

			while (myRs.next())
				pass = myRs.getString("Password");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pass;
	}

	/*
	 * Input: the AM of the doctor Function: Gets the personal timetable of the
	 * given doctor from the database Output: The timetable of the given doctor:
	 * null if a timetable hasn't been defined yet otherwise the timetable itself is
	 * returned
	 */
	public String returnDoctorTimetable(String AM) {
		Statement myStmt = null;
		ResultSet myRs = null;
		String timetable = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select Timetable from doctor where RN = \"" + AM + "\"";
			myRs = myStmt.executeQuery(query);

			while (myRs.next())
				timetable = myRs.getString("Timetable");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timetable;
	}

	/*
	 * Input: the code of the wanted entity (1: manager, 2: pharmacist) Function:
	 * gets the password of the wanted entity from the database Output: the password
	 * of the entity
	 */
	public String returnPasswordUser(int code) {

		Statement myStmt = null;
		ResultSet myRs = null;
		String pass = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select Password from privileged_user where Code = " + code;
			myRs = myStmt.executeQuery(query);

			if (myRs.next())
				pass = myRs.getString("Password");
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
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

	
	
	 /* INPUT: the drug to be inserted to the database and a connection with the database
		 * Function: inserts drug to the database
		 * Output: -----------------------
		 */
	 public void addDrug (Drug d) {
			Statement myStmt = null;
			ResultSet myRs = null;
				
			
			try {
				// 2. Create a Statement
				myStmt = myConn.createStatement();
				
				// 3. Execute SQL query
				myStmt.executeUpdate("Insert into drug (`id`, `Availability`, `Price`, `Name`, `SoldUnits`)  Values ('" + d.getId() + "', '" + d.getAvailability() + "', '" + d.getPrice() + "', '" + d.getName() + "', '" + d.getSoldUnits() + "')");
				
				// 4. Display the result
				printTable("drug", myConn);
					
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
	 
	/*
	 * INPUT: the drug to be deleted from the database and a connection with the
	 * database Function: deletes drug from the database Output:
	 * -----------------------
	 */
	public void removeDrug(Drug d) {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// 2. Prepare Statement
			myStmt = myConn.prepareStatement("delete from drug where Name = ?");

			// 3. Set the Parameters
			myStmt.setString(1, d.getName());

			// 4. Execute SQL query
			myStmt.executeUpdate();

			// 5. Display the result
			printTable("drug", myConn);

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
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

	/*
	 * Input: A list that will contain all the data from all the drugs and a
	 * connection with the database Function: Gets all the drugs from the database
	 * and all their data and puts them in the ArrayList that was sent as input
	 * Output: The list that contains the drugs
	 * 
	 * public static void getAllDrugs(ArrayList<Drug> drugs, Connection myConn) {
	 * Statement myStmt = null; ResultSet myRs = null; try { // 2. Create a
	 * Statement myStmt = myConn.createStatement();
	 * 
	 * // 3. Execute SQL query String query = "select * from drug"; myRs =
	 * myStmt.executeQuery(query);
	 * 
	 * // 4. Process the result set and put the data in the drug list Drug d; while
	 * (myRs.next()) {
	 * 
	 * 
	 * d = new Drug(myRs.getInt("id"), myRs.getInt("Availability"),
	 * myRs.getDouble("Price") , myRs.getString("Name"), myRs.getInt("SoldUnits"));
	 * 
	 * // add the drug to the drug's list drugs.add(d); } }catch (Exception exc) {
	 * exc.printStackTrace(); }finally { if (myRs != null) { try { myRs.close(); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 * 
	 * if (myStmt != null) { try { myStmt.close(); } catch (SQLException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } } } }
	 */

	public Connection getMyConn() {
		return myConn;
	}

	public String getResult() {
		return result;
	}

}
