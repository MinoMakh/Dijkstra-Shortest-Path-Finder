package application;

// Adjacent class to represent a path from one city to the other one
public class Adjacent implements Comparable<Adjacent> {

	private City city1;
	private City city2;
	private double cost;

	public Adjacent(City city1, City city2) {
		this.city1 = city1;
		this.city2 = city2;
		this.cost = calculateDistance(city1.getLatitude(), city1.getLongitude(), city2.getLatitude(),
				city2.getLongitude());
	}

	// Returns the distance in kilometers from one city to the other using
	// latitude and longitude of both countries and taking in consideration that the
	// earth is round (radius of the earth)
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double earthRadiusKm = 6371; // Radius of the earth in km
		lat1 = degToRad(lat1);
		lat2 = degToRad(lat2);
		lon1 = degToRad(lon1);
		lon2 = degToRad(lon2);

		return earthRadiusKm
				* Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
	}

	// Convert from degrees to radians
	private double degToRad(double deg) {
		return deg * (Math.PI / 180);
	}

	@Override
	public String toString() {
		return "From: " + city1.getCity() + " To: " + city2.getCity() + " Cost: " + cost;
	}

	@Override
	public int compareTo(Adjacent o) {
		return -2;
	}

	// Getters & Setters
	public City getCity1() {
		return city1;
	}

	public void setCity1(City city1) {
		this.city1 = city1;
	}

	public City getCity2() {
		return city2;
	}

	public void setCity2(City city2) {
		this.city2 = city2;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
