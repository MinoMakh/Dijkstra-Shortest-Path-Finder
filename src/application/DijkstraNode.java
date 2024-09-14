package application;

// Node class for the Dijkstra algorithm
public class DijkstraNode<T extends Comparable<T>> {

	private boolean known;
	private double distance;
	private City path;
	private T data;

	public DijkstraNode(boolean known, double distance, City path, T data) {
		this.known = known;
		this.distance = distance;
		this.path = path;
		this.data = data;
	}

	@Override
	public String toString() {
		if (path == null) {
			return "DJekstra: Known: " + known + ", Distance: " + ", Path: null " + data.toString();
		}
		return "DJekstra: Known: " + known + ", Distance: " + distance + ", Path: " + path.getCity() + " " +  data.toString();
	}

	// Getters & Setters
	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public City getPath() {
		return path;
	}

	public void setPath(City path) {
		this.path = path;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
