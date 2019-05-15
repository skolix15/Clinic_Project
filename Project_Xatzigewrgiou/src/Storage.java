import java.util.ArrayList;

public class Storage {
	
	private static ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
	
	
	public static void updateStorage() {
		
		//Ενημέρωση των φαρμάκων της αποθήκης 
		//σύμφωνα με την βάση δεδομένων
		
	}
	
	public static void addMedicine(String name, String code, double price, int availability) {
		Medicine med = new Medicine(name, code, price, availability);
		medicineList.add(med);
	}
	
	public static void removeMedicine(String name, String code) {
		
		if (Storage.searchMedicine(name, code) != null)
			medicineList.remove(Storage.searchMedicine(name, code));
		else 
			System.out.println("This medicine does not exist in storage");
	}
	
	public static Medicine searchMedicine(String name, String code) {
		
		for (int i=0; i<medicineList.size(); i++) {
			if (medicineList.get(i).getName().equals(name) && (medicineList.get(i).getCode().equals(code))) {
				return medicineList.get(i);
			}
		}
		
		return null;
		
	}

	public static ArrayList<Medicine> getMedicineList(){
		
		return medicineList;
	}
}
