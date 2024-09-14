## **Dijkstra's Algorithm for Shortest Paths**

<br>

**Project Overview**

This project implements Dijkstra's algorithm to find the **shortest route** between cities on a map. The algorithm calculates the **least-cost path** based on the Euclidean distances between cities. The program allows users to choose two cities and find the route with the **lowest price**, using JavaFX for a user-friendly graphical user interface (GUI).

<br>

**Features**

* **Graph Representation:** Cities and roads are modeled as a graph with cities as vertices and paths as edges.
* **Efficient Pathfinding:** Optimized Dijkstra's algorithm to handle multiple shortest path queries efficiently.
* **User Interaction:** Cities can be selected via mouse or keyboard, and the optimal route is displayed on the map.
* **Large Dataset Support:** The map supports any amount of cities as input.
* **Visualization:** The shortest path is visually displayed on a world map using JavaFX.

<br>

**Project Details**

**Input:** A list of cities with coordinates and the roads connecting them. Cities and roads are represented in a text file format.

**Output:** The shortest path between two cities, including the total cost and the route displayed on the map.

**Example of the input data format:**

```
6 9  // Number of cities, number of Edges
City1 1000 2400  // City, longitude, latitude
City2 2800 3000
City3 2400 2500
...
City1 City2  // City1 goes to City2
City1 City4  // City1 goes to City4
...
```

<br>

**How It Works**

* **Priority Queue:** Dijkstra's algorithm uses a priority queue to efficiently calculate the shortest path by selecting the next closest city.
* **Path Optimization:** The algorithm avoids recomputation of already processed cities to speed up pathfinding.
* **Space Optimization:** The program optimizes space usage, avoiding the need to store all possible shortest paths between cities.

<br>

**Installation & Usage**

**Clone the Repository:**

```bash
git clone https://github.com/yourusername/project-name.git
```

**Install JavaFX:**

Download the JavaFX SDK from [https://openjfx.io](https://openjfx.io). Add the JavaFX libraries to your project by setting the VM options.

**Set Up JavaFX:**

Make sure to include the correct JavaFX libraries in your run configuration.

```bash
--module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml
```

**Compile and Run the Program:**

Compile and run the project using your preferred IDE (e.g., IntelliJ, Eclipse).

```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml Dijkstra.java
java --module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml Dijkstra
```

**Select Cities:** Choose the start and end cities using the graphical interface, and the shortest path will be displayed on the map.

<br>

**Example Output**

The user selects "Country1" as the starting point and "Country6" as the destination. The program outputs the shortest path, showing the cities passed through and the total cost.

**Shortest Path:** Country1 -> Country4 -> Country6  
**Total Cost:** X kilometers

**Note:**

* Replace `project-name` with your actual project name.
* Replace `/path/to/javafx-sdk/lib` with the actual path to your JavaFX SDK library folder.
* You can further customize the styling using additional markdown elements and CSS.
