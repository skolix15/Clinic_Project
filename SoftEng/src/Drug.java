
public class Drug {
	
	private String name;
	private String id;
	private double price;
	private int availability;
	private int soldUnit;

	public Drug(String name, String Id, double price, int availability) {
		this.name = name;
		this.id = Id;
		this.price = price;
		this.availability = availability;
		this.soldUnit = 0;

	}
	
	
	public String getName() {
		return name;
	}

	
	public String getId() {
		return id;
	}
	
	
	public double getPrice() {
		return price;
	}
	
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	public int getAvailability() {
		return availability;
	}


	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public void addSoldUnit(int quantity) {
		
		soldUnit += quantity;
	}

	public int getSoldUnit() {
		
		return soldUnit;
	}
	
}
