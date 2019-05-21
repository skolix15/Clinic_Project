

public class Doctor {
	String firstName;
	String lastName;
	String rn;
	String password;
	String timetable;
	
	public Doctor(String firstName, String lastName, String rn, String password, String timetable) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.rn = rn;
		this.password = password;
		this.timetable = timetable;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTimetable() {
		return timetable;
	}

	public void setTimetable(String timetable) {
		this.timetable = timetable;
	}
	
	
}
