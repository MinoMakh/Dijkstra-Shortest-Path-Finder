package application;

// Djikstra algorithm to calculate the best path from one city to the other
public class Dijkstra {

	private int numberOfVertex;
	private DijkstraNode<City>[] table; // Dijkstra table
	// LinkedList to store the read vertexes for later re-initializing the table in
	// a better time complexity (Only restart the countries that where read before)
	private LinkedList<Integer> readVertex = new LinkedList<Integer>();

	public Dijkstra(int numberOfVertex) {
		this.numberOfVertex = numberOfVertex;
		this.table = new DijkstraNode[numberOfVertex];
	}

	// Initialize the dijkstra table
	public void initializeTable(City start, City end, City[] countries) {

		// If its the first time initializing it just calculate the djikstra straight
		// away
		if (readVertex.getLength() == 0) {
			calculateDijkstra(start, end, countries);
		} // Else restart the countries that were read before and then calculate the
			// dijkstra
		else {
			LinkedNode<Integer> current = readVertex.getHead();
			while (current != null) {
				int i = current.getData();
				table[i] = null;
				current = current.getNext();
			}
			readVertex = new LinkedList<Integer>();
			calculateDijkstra(start, end, countries);
		}
	}

	// Calculating dijkstra using priority queue
	private void calculateDijkstra(City start, City end, City[] countries) {
		PriorityQueue<City> priorityQueue = new PriorityQueue<City>(countries.length);
		priorityQueue.enqueue(start);

		// Initialize the table with the start country's distance as 0
		int startIndex = start.getIndex();
		table[startIndex] = new DijkstraNode<City>(false, 0, null, start);

		// Loop through the vertexes
		while (!priorityQueue.isEmpty()) {
			boolean found = false; // Flag when the target country is found
			City city = priorityQueue.dequeue(); // Dequeue the next country
			int i = city.getIndex();
			readVertex.insertAtHead(i);

			// Set the vertex known to true, and create it in case its null
			if (table[i] == null)
				table[i] = new DijkstraNode<City>(true, Double.MAX_VALUE, null, city);
			else
				table[i].setKnown(true);

			// Loop through the adjacents of the vertex
			LinkedNode<Adjacent> currentAdjacentNode = city.getAdjacentList().getHead();
			while (currentAdjacentNode != null) {
				Adjacent currentAdjacent = currentAdjacentNode.getData();
				City adjacentCountry = currentAdjacent.getCity2();
				int j = adjacentCountry.getIndex();
				readVertex.insertAtHead(j);

				// Create the vertex in case its null and if its not known calculate its
				// distance and update in case its better than the old distance
				if (table[j] == null)
					table[j] = new DijkstraNode<City>(false, Double.MAX_VALUE, null, adjacentCountry);

				if (!table[j].isKnown()) {
					double newDist = table[i].getDistance() + currentAdjacent.getCost();

					if (newDist < table[j].getDistance()) {
						table[j].setDistance(newDist);
						table[j].setPath(city);
						// If the end is found, update the flag
						if (end.compareTo(table[j].getData()) == 0)
							found = true;

						priorityQueue.enqueue(adjacentCountry); // Enqueue the adjacent country
					}
				}
				currentAdjacentNode = currentAdjacentNode.getNext();
			}
			if (found)
				return;
		}
	}

	// Getters & Setters
	public int getNumberOfVertex() {
		return numberOfVertex;
	}

	public void setNumberOfVertex(int numberOfVertex) {
		this.numberOfVertex = numberOfVertex;
	}

	public DijkstraNode<City>[] getTable() {
		return table;
	}

	public void setTable(DijkstraNode<City>[] table) {
		this.table = table;
	}

}
