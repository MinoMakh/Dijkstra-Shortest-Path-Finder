package application;

public class City implements Comparable<City> {

	private String city;
	private double latitude;
	private double longitude;
	private LinkedList<Adjacent> adjacentList = new LinkedList<Adjacent>();
	private int index;

	public City(String city) {
		this.city = city;
	}

	public City(String city, double latitude, double longitude, int index) {
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.index = index;
	}

	public void addAdjacent(Adjacent adjacent) {
		adjacentList.insertAtHead(adjacent);
	}

	@Override
	public String toString() {
		return "City : " + city;
	}

	@Override
	public int compareTo(City o) {
		if (o.city.equals(this.city))
			return 0;
		return -1;
	}

	// Setters & Getters
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public LinkedList<Adjacent> getAdjacentList() {
		return adjacentList;
	}

	public void setAdjacentList(LinkedList<Adjacent> adjacentList) {
		this.adjacentList = adjacentList;
	}
}
