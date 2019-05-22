import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Order {
	
	protected ArrayList<Medicine> medicines;
	protected ArrayList<Integer> quantityOfMedicines;
	protected double totalCost;
	
	// prosthetei ena neo farmako sth lista ths syntaghs me ta farmaka kai thn posotita tou sthn antistoixi thesi sth lista ths syntaghs me tis posothtes.
	
	public void addMedicineInTheOrder (Medicine orderedMedicine,int quantity) {
		
		medicines.add(orderedMedicine);
		quantityOfMedicines.add(quantity);
	
	}
	
	public abstract void deleteMedicineFronTheOrder(Medicine orderedMedicine);
	
	// Tha yparxei ena arxeio me tis paraggelies ( Sto telos tha enhmerwnetai h bash apo auta ta arxeia ).
	// Csexwristo arxeio gia tis syntages kai csexwristo gia tous anefodiasmous.
	// To kathe arxeio tha grafetai-enhmerwnetai me tis nees syntages h anefodiasmous.
	// H diakrisi metacsy twn 2 arxeiwn ginetai me basi to "fileName".
	
	public void informHistory(String fileName) {
		
		try
		{
		    String textToBeWritten = "";
		    
		    for(int i = 0;i<medicines.size();i++) 
		    	textToBeWritten += (medicines.get(i).getCode() + " " + medicines.get(i).getName() + " " + String.valueOf(quantityOfMedicines.get(i)) + "\n");
		         
		    FileWriter fw = new FileWriter(fileName,true);  //the true will append the new data
		    fw.write(textToBeWritten);						 //appends the string to the file
		    fw.close();
		    
		}
		
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
		
	} 
	
	//Epistrefei thn lista me ta farmaka.
	
	public ArrayList<Medicine> getListOfMedicines(){
		
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
			System.out.println("Code: " + medicines.get(i).getCode());
			System.out.println("Availability: " + medicines.get(i).getAvailability());
			System.out.println("Price: " + medicines.get(i).getPrice());
		}
	}
	
	public boolean searchForMedicineInOrder(String medicineCode) {
		
		for(int i=0;i<medicines.size();i++) {
			
			if(medicines.get(i).getCode().equals(medicineCode))
				return true;
		}
		
		return false;
	}
	
	public void clearOrder() {
		
		ArrayList<Medicine> med = getListOfMedicines();
		ArrayList<Integer> quantityOfMed = getQuantityOfMedicines();
		
		med.clear();
		quantityOfMed.clear();
	}

}


