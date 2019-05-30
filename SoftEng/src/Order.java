import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Order {
	
	protected ArrayList<Drug> medicines;
	protected ArrayList<Integer> quantityOfMedicines;
	protected double totalCost;
	protected String date;
	protected int code;
	
	// prosthetei ena neo farmako sth lista ths syntaghs me ta farmaka kai thn posotita tou sthn antistoixi thesi sth lista ths syntaghs me tis posothtes.
	
	public void addMedicineInTheOrder (Drug orderedMedicine,int quantity) {
		
		medicines.add(orderedMedicine);
		quantityOfMedicines.add(quantity);
		
	}
	
	public abstract void deleteMedicineFronTheOrder(Drug orderedMedicine);
	
	// Tha yparxei ena arxeio me tis paraggelies ( Sto telos tha enhmerwnetai h bash apo auta ta arxeia ).
	// Csexwristo arxeio gia tis syntages kai csexwristo gia tous anefodiasmous.
	// To kathe arxeio tha grafetai-enhmerwnetai me tis nees syntages h anefodiasmous.
	// H diakrisi metacsy twn 2 arxeiwn ginetai me basi to "fileName".
	
	//Epistrefei thn lista me ta farmaka.
	
	public ArrayList<Drug> getListOfMedicines(){
		
		return medicines;
	}
	
	// Epistrefei thn lista me tis posotites twn farmakwn.
	
	public ArrayList<Integer> getQuantityOfMedicines(){
		
		return quantityOfMedicines;
	}
	
	// Abstract synarthsh poy ypologizei to synoliko kostos ths syntaghs.
	
	public abstract double getTotalCost();
	
	public void printListOfMedicines() {
		
		System.out.println("Size of List: " + medicines.size());
		
		for(int i=0;i<medicines.size();i++) {
			
			
			System.out.println("Name: " + medicines.get(i).getName());
			System.out.println("Id: " + medicines.get(i).getId());
			System.out.println("Availability: " + medicines.get(i).getAvailability());
			System.out.println("Price: " + medicines.get(i).getPrice());
		}
	}
	
	public boolean searchForMedicineInOrder(String medicineId) {
		
		for(int i=0;i<medicines.size();i++) {
			
			if(medicines.get(i).getId().equals(medicineId))
				return true;
		}
		
		return false;
	}
	
	public void clearOrder() {
		
		ArrayList<Drug> med = getListOfMedicines();
		ArrayList<Integer> quantityOfMed = getQuantityOfMedicines();
		
		med.clear();
		quantityOfMed.clear();
	}
	
	public String getDateTime() {
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public int getCode() {
		
		return code;
	}
	
	public void setCode(int code) {
		
		this.code = code;
		
	}
	
	public String getDate() {
		
		return date;
	}

}


