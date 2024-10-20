package dataviewer3final;

public class DataRecord {
	
	private int year;
	private int month;
	private double avgTemp;
	private double avgTempUncer;
	private String state;
	private String country;
	
	// Getter and Setter for year
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// Getter and Setter for month
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	// Getter and Setter for avgTemp
	public double getAvgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(double d) {
		this.avgTemp = d;
	}

	// Getter and Setter for avgTempUncer
	public double getAvgTempUncer() {
		return avgTempUncer;
	}

	public void setAvgTempUncer(int avgTempUncer) {
		this.avgTempUncer = avgTempUncer;
	}

	// Getter and Setter for state
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// Getter and Setter for country
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "DataRecord{" +
				"year=" + year +
				", month=" + month +
				", avgTemp=" + avgTemp +
				", avgTempUncer=" + avgTempUncer +
				", state='" + state + '\'' +
				", country='" + country + '\'' +
				'}';
	}
}
