import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrescriptionOrdersTemporalBase {
	
	
	private static ArrayList<Drug> medicines = new ArrayList<Drug>();
	private static ArrayList<Integer> quantityOfMedicines = new ArrayList<Integer>();
	private static String date = getDateTime();
	private static int numberOfLinesInFile = 0;
	
	public static void setNumberOfLinesInFile(int numberOfLines) {
		
		numberOfLinesInFile = numberOfLines;
	}
	
	public static int getNumberOfLinesInFile() {
		
		return numberOfLinesInFile;
	}
	
	public static ArrayList<Drug> getListOfMedicinesFromAllTheDailyOrders(){
		
		return medicines;
	}
	
	public static ArrayList<Integer> getListOfQuantityOfMedicinesFromAllTheDailyOrders(){
		
		return quantityOfMedicines;
	}
	
	public static boolean checkIfMedicineExistsInTheOrderFile(String name) {
		
		for(int i=0;i<medicines.size();i++) 
			if(medicines.get(i).getName().equals(name))
					return true;
		
		return false;
	}
	
	public static void addMedicineInTheListOfMedicinesFromAllTheDailyOrders(Drug medicine,int quantity) {
		
		if(!checkIfMedicineExistsInTheOrderFile(medicine.getName())) {
			medicines.add(medicine);
			quantityOfMedicines.add(quantity);
		}
		else{
			
			int index = PrescriptionOrdersTemporalBase.findAndReturnIndexOfMedicineInTheListOfMedicinesFromAllTheDailyOrders(medicine.getName());
			int newQuantityOfMedicine = quantityOfMedicines.get(index) + quantity;
			
			if(index != -1)
				quantityOfMedicines.set(index, newQuantityOfMedicine);
		
			}
			
			
			
		}
	
	public static void writeToOrderFile(ArrayList<Drug> medicinesInOrder,ArrayList<Integer> quantityOfMedicinesInOrder) {
		
		PrescriptionOrdersTemporalBase.readFromOrderFile();
		
		String fileName = "History For Prescription.txt";
		
		
		for(int i=0;i<medicinesInOrder.size();i++) {
			
			PrescriptionOrdersTemporalBase.addMedicineInTheListOfMedicinesFromAllTheDailyOrders(medicinesInOrder.get(i), quantityOfMedicinesInOrder.get(i));
		}		
		
		try
		{
		    String textToBeWritten = "";
		    
		    textToBeWritten = date + ": \n\n";
		    for(int i = 0;i<medicines.size();i++) 
		    	textToBeWritten += (medicines.get(i).getId() + " " + medicines.get(i).getName() + 
		    						" " + String.valueOf(quantityOfMedicines.get(i)) + "\n");
		         
		    FileWriter fw = new FileWriter(fileName,false);  //the true will append the new data
		    fw.write(textToBeWritten);						 //appends the string to the file
		    fw.close();
		    
		}
		
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	
	public static String getDateTime() {
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	
	
	public static int findAndReturnIndexOfMedicineInTheListOfMedicinesFromAllTheDailyOrders(String name) {
		
		int index = -1;
		
		boolean exists = PrescriptionOrdersTemporalBase.checkIfMedicineExistsInTheOrderFile(name);
		
		if(exists) {
			
			for(int i=0;i<medicines.size();i++)
				if(medicines.get(i).getName().equals(name))
					index = i;
		}
		
		return index;
			
		
	}
	
	public static void readFromOrderFile() {
	
		int count = 0;
		String fileName = "History For Prescription.txt";

		
		File file = new File(fileName);
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			
			String line;
			while((line = br.readLine()) != null) {
				
				if(count > 1) {
					String temp[] = line.split(" ");
					Drug med = new Drug(temp[1],temp[0]);
					int quantity = Integer.parseInt(temp[2]);
					medicines.add(med);
					quantityOfMedicines.add(quantity);
					}
				else if(count == 0)
					date = line;
				
				
				count += 1;
				numberOfLinesInFile  = numberOfLinesInFile + 1;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static String getDate(){
		
		return date;
	}

}
