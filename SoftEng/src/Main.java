import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {

		db conn = null;
		try {
			conn = new db();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	    
	   Storage.addMedicine("aspirini","001",3,5, 0);
	   Storage.addMedicine("depon","002",4,10, 0);
	   Storage.addMedicine("panadol","003",2.5,7, 0);
	   Storage.addMedicine("siropi","004",4,6, 0);

		
	   
	   
	   new GlobalHomeFrame(conn);


			
		
		
	}
	

}
