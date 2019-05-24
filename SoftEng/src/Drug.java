
public class Drug {
	
	private String name;
	private String id;
	private double price;
	private int availability;

	
	public Drug(String name, String Id, double price, int availability) {
		this.name = name;
		this.id = Id;
		this.price = price;
		this.availability = availability;
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
	
}
