import java.util.ArrayList;
import java.util.Iterator;

public class Supply extends Order {

	// Dimiourgeitai ena kainourgio antikeimeno typou Supply opou apla oi dyo listes tis yperklasis "Order" pairnoun thn timh "null".
	
	public Supply() {
			
		medicines = new ArrayList<Medicine>();
		quantityOfMedicines = new ArrayList<Integer>();
		totalCost = 0;
	}
		
	// Prostithetai ena neo farmako stis lista me ta farmaka kai h posotita tou sthn antistoixi thesi sthn lista me tis posothtes.
	// Taytoxrona ayxanetai h diathesimotita tou farmakou apo to iatreio.
		
	public void addMedicineInTheOrder (Medicine orderedMedicine,int quantity) {
			
		super.addMedicineInTheOrder(orderedMedicine, quantity);
		orderedMedicine.setAvailability(orderedMedicine.getAvailability() + quantity);
		
	}
	
	public void deleteMedicineFronTheOrder(Medicine orderedMedicine) {
		
		Iterator<Medicine> iterator = medicines.iterator();
		int i = 0;
		
		while( iterator.hasNext() ) {
		
		    Medicine medicine = iterator.next();
		    
		    if(medicine.getCode().equals(orderedMedicine.getCode())) {
		        iterator.remove();
		        orderedMedicine.setAvailability(orderedMedicine.getAvailability() - quantityOfMedicines.get(i));
		        quantityOfMedicines.remove(i);
		        i++;
		    }
		}
	}
		
	// Ypologizetai kai epistrefetai to synoliko kostos ths paraggelias-anefodiasmou.
	// Ginetai h paradoxi oti kathe farmako kostizei sto iatreio to 1/3.
	// apo thn timi sthn opoia tha to poulisei ston astheni.

	public double getTotalCost() {

		totalCost = 0;
		
		for(int i = 0;i<medicines.size();i++) 
			totalCost += (medicines.get(i).getPrice() / 3);
			
		return totalCost;
	}
	
}
	

