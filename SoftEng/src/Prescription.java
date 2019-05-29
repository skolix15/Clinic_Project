import java.util.ArrayList;
import java.util.Iterator;

public class Prescription extends Order {

	// Dimiourgeitai ena kainourgio antikeimeno typou Prescription.
	
	public Prescription() {
		
		medicines = new ArrayList<Drug>();
		quantityOfMedicines = new ArrayList<Integer>();
		totalCost = 0;
		date = super.getDateTime();
		// code = enimerwsi apo vasi + 1
	
	}
	
	// Prostithetai ena neo farmako stis lista me ta farmaka kai h posotita tou sthn antistoixi thesi sthn lista me tis posothtes.
	// Taytoxrona meiwnetai h diathesimotita tou farmakou apo to iatreio.
	
	public void addMedicineInTheOrder (Drug orderedMedicine,int quantity) {
		
		// xreiazetai sto gui na ginetai elegxos diathesimotitas
		
		super.addMedicineInTheOrder(orderedMedicine, quantity);
		orderedMedicine.setAvailability(orderedMedicine.getAvailability() - quantity);
		orderedMedicine.setSoldUnits(orderedMedicine.getSoldUnits() + quantity);
	
	}
	
	public void deleteMedicineFronTheOrder(Drug orderedMedicine) {
	
		Iterator<Drug> iterator = medicines.iterator();
		int i = 0;
		
		while( iterator.hasNext() ) {
		
		    Drug medicine = iterator.next();
		    
		    if(medicine.getId().equals(orderedMedicine.getId())) {
		        iterator.remove();
		        orderedMedicine.setAvailability(orderedMedicine.getAvailability() + quantityOfMedicines.get(i));
		        orderedMedicine.setSoldUnits(orderedMedicine.getSoldUnits() - quantityOfMedicines.get(i));
		        quantityOfMedicines.remove(i);
		        i++;
		    }
		}
	}
	
	// Ypologizetai kai epistrefetai to synoliko kostos ths syntaghs.

	public double getTotalCost() {
		
		totalCost = 0;
		
		for(int i = 0;i<medicines.size();i++) 
			totalCost += medicines.get(i).getPrice() * quantityOfMedicines.get(i);
		
		return totalCost;
	}
	
}
