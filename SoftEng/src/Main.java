import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {

		db conn = null;
		try {
			conn = new db();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	   /* 
	   Storage.addMedicine("aspirini","001",3,5);
	   Storage.addMedicine("depon","002",4,10);
	   Storage.addMedicine("panadol","003",2.5,7);
	   Storage.addMedicine("siropi","004",4,6);
*/

	   new GlobalHomeFrame(conn);
	    
	   
	}
	

}
