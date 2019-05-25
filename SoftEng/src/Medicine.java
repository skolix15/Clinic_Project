
public class Medicine {
	
	private String name;
	private String code;
	private double price;
	private int availability;
	private int soldUnits;

	
	public Medicine(String name, String code, double price, int availability) {
		this.name = name;
		this.code = code;
		this.price = price;
		this.availability = availability;
	}
	
	
	public String getName() {
		return name;
	}

	
	public String getCode() {
		return code;
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


	public int getSoldUnits() {
		return soldUnits;
	}


	public void setSoldUnits(int soldUnits) {
		this.soldUnits = soldUnits;
	}
	
}
