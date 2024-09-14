package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends Application {

	// Graph for the djikstra using array
	private static City[] citiesGraph;
	private Dijkstra djekstra;
	private ComboBox<String> sourceCombo, targetCombo;
	private CheckBox displayNamesCheckBox;

	@Override
	public void start(Stage primaryStage) {
		readFile(new File("src/Cities.txt"));

		Scene scene = new Scene(getMainUI());
		primaryStage.setTitle("Dijkstra Best Path Finder");
		primaryStage.getIcons().add(new Image("file:src/icons8-world-96 (1).png"));
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	// Calculate the x position of a city given the width of the map and the
	// longitude of the country
	private double longitudeToX(double longitude, double mapWidth) {
		double minLon = -180.0;
		double maxLon = 180.0;
		double lonRatio = (longitude - minLon) / (maxLon - minLon);
		return lonRatio * mapWidth;
	}

	// Calculate the y position of a city given the height of the map and the
	// latitude of the country
	private double latitudeToY(double latitude, double mapHeight) {
		double minLat = -90.0;
		double maxLat = 90.0;
		double latRatio = (latitude - minLat) / (maxLat - minLat);
		return (1 - latRatio) * mapHeight;
	}

	// Update the comboboxes when a country is clicked
	private void handleCityClick(MouseEvent event, String CountryName) {
		String sourceCountry = sourceCombo.getValue();
		String targetCountry = targetCombo.getValue();
		if (sourceCountry != null && targetCountry != null) {
			sourceCombo.setValue(null);
			targetCombo.setValue(null);
			sourceCountry = null;
			targetCountry = null;
		}
		if (sourceCountry == null)
			sourceCombo.setValue(CountryName);
		else if (targetCountry == null)
			targetCombo.setValue(CountryName);

		System.out.println("Clicked on city: " + CountryName);
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Returns the main user interface
	private HBox getMainUI() {
		HBox mainHBox = new HBox(20);

		djekstra = new Dijkstra(citiesGraph.length);
		ImageView worldMapView = new ImageView(new Image("file:src/world_map_equirectangular.jpg"));
		worldMapView.setPreserveRatio(true);

		// Create a pane for the map image, bind its height, width and bind its
		// container
		Pane mapPane = new Pane(worldMapView);
		
		mapPane.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.8));
		mapPane.prefHeightProperty().bind(mainHBox.heightProperty());

		worldMapView.fitWidthProperty().bind(mapPane.widthProperty());
		worldMapView.fitHeightProperty().bind(mapPane.heightProperty());

		// Buttons interface
		VBox buttonsVBox = new VBox(20);
		Text sourceTitle = new Text("Source:");
		sourceTitle.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		sourceCombo = new ComboBox<>();
		sourceCombo.setPromptText("Select a country");

		Text targetTitle = new Text("Target:");
		targetTitle.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		targetCombo = new ComboBox<>();
		targetCombo.setPromptText("Select a country");

		displayNamesCheckBox = new CheckBox("Display names");
		displayNamesCheckBox.setFont(Font.font("Gill Sans", FontWeight.BOLD, 14));
		displayNamesCheckBox.setSelected(true);

		// Add the cities to the comboboxes
		for (int i = 0; i < citiesGraph.length; i++) {
			City city = citiesGraph[i];
			String cityName = city.getCity();
			double latitude = city.getLatitude();
			double longitude = city.getLongitude();

			sourceCombo.getItems().add(city.getCity());
			targetCombo.getItems().add(city.getCity());

			// Create a triangle for each city and bind its x and y position using the x
			// and y functions
			Polygon cityMarker = createTriangle(10, Color.RED);
			cityMarker.setOnMouseClicked(event -> handleCityClick(event, cityName));

			cityMarker.layoutXProperty()
					.bind(Bindings.createDoubleBinding(
							() -> longitudeToX(longitude, worldMapView.getBoundsInParent().getWidth()),
							worldMapView.boundsInParentProperty()));
			cityMarker.layoutYProperty()
					.bind(Bindings.createDoubleBinding(
							() -> latitudeToY(latitude, worldMapView.getBoundsInParent().getHeight()),
							worldMapView.boundsInParentProperty()));

			// Create a text for the country name
			Text cityNameText = new Text(cityName);
			cityNameText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 12));
			cityNameText.setFill(Color.BLACK);
			cityNameText.setUserData("cityNameText");
			// Bind the text position to be slightly above the city marker
			cityNameText.layoutXProperty()
					.bind(cityMarker.layoutXProperty().subtract(cityNameText.getBoundsInLocal().getWidth() / 2));
			cityNameText.layoutYProperty().bind(cityMarker.layoutYProperty().subtract(15));

			mapPane.getChildren().addAll(cityMarker, cityNameText);
		}

		// Checkbox to display or hide names of the cities
		displayNamesCheckBox.setOnAction(event -> {
			if (displayNamesCheckBox.isSelected()) {
				mapPane.getChildren().forEach(node -> {
					if ("cityNameText".equals(node.getUserData())) {
						node.setVisible(true);
					}
				});
			} else {
				mapPane.getChildren().forEach(node -> {
					if ("cityNameText".equals(node.getUserData())) {
						node.setVisible(false);
					}
				});
			}
		});

		// Texts of the calculations
		Text pathTitle = new Text("Path:");
		pathTitle.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		TextArea pathArea = new TextArea();
		pathArea.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		pathArea.setMaxSize(250, 200);
		pathArea.setWrapText(true);
		pathArea.setEditable(false);

		Text distanceTitle = new Text("Distance:");
		distanceTitle.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		TextArea distanceArea = new TextArea();
		distanceArea.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		distanceArea.setEditable(false);
		distanceArea.setWrapText(true);
		distanceArea.setMaxSize(250, 200);

		Button runButton = new Button("Run");
		runButton.setScaleX(2);
		runButton.setScaleY(2);

		runButton.setOnAction(event -> {
			// Getting the source & target
			String sourceCityName = sourceCombo.getValue();
			String targetCityName = targetCombo.getValue();

			if (sourceCityName != null && targetCityName != null) {
				City targetCity = search(new City(targetCityName));
				City sourceCity = search(new City(sourceCityName));

				// Create the djekstra graph
				djekstra.initializeTable(sourceCity, targetCity, citiesGraph);
				LinkedList<City> path = getPath(targetCity);

				// If there is at least more than one node in the path
				if (path.getLength() != 1) {
					mapPane.getChildren().removeIf(node -> "dynamic".equals(node.getUserData()));

					LinkedNode<City> currentCity = path.getHead();
					String pathString = "";
					int step = 1;

					// Loop through the path of the targetCountry calculating the distances
					while (currentCity != null && currentCity.getNext() != null) {
						City start = currentCity.getData();
						City end = currentCity.getNext().getData();
						pathString += start.getCity() + " -> ";
						double startX = longitudeToX(start.getLongitude(), worldMapView.getBoundsInParent().getWidth());
						double startY = latitudeToY(start.getLatitude(), worldMapView.getBoundsInParent().getHeight());
						double endX = longitudeToX(end.getLongitude(), worldMapView.getBoundsInParent().getWidth());
						double endY = latitudeToY(end.getLatitude(), worldMapView.getBoundsInParent().getHeight());

						addLine(mapPane, startX, startY, endX, endY);

						double midX = (startX + endX) / 2;
						double midY = (startY + endY) / 2;

						// Cost from one city to the other
						double cost = djekstra.getTable()[end.getIndex()].getDistance()
								- djekstra.getTable()[start.getIndex()].getDistance();
						String newCost = String.format("%.2f", cost);
						Text stepText = new Text(midX, midY, step + " (" + newCost + ")");
						stepText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 14));
						stepText.setFill(Color.BLACK);
						stepText.setUserData("dynamic");
						mapPane.getChildren().add(stepText);

						currentCity = currentCity.getNext();
						step++;
					}

					pathString += targetCity.getCity();

					// Total cost is taken from the target city
					double totalCost = djekstra.getTable()[targetCity.getIndex()].getDistance();
					distanceArea.setText("The total distance is: " + totalCost + " kilometers.");
					pathArea.setText(pathString);

					// If the same city is selected as source and target
				} else if (sourceCombo.getValue().equals(targetCombo.getValue())) {
					mapPane.getChildren().removeIf(node -> "dynamic".equals(node.getUserData()));
					distanceArea.setText("The total distance is: 0 kilometers.");
					pathArea.setText(targetCombo.getValue());

					// If the path is not possible
				} else {
					mapPane.getChildren().removeIf(node -> "dynamic".equals(node.getUserData()));
					distanceArea.setText("Not possible path.");
					pathArea.setText("Not possible path.");
				}
			}
		});

		buttonsVBox.setAlignment(Pos.CENTER);
		buttonsVBox.getChildren().addAll(sourceTitle, sourceCombo, targetTitle, targetCombo, pathTitle, pathArea,
				distanceTitle, distanceArea, displayNamesCheckBox, runButton);

		mainHBox.getChildren().addAll(mapPane, buttonsVBox);

		return mainHBox;
	}

	// Reading the input file of countries with their adjacent
	private void readFile(File file) {
		try {
			Scanner scanFile = new Scanner(file);
			scanFile.nextLine(); // Skip the first line

			// Getting the number of vertexes and edges
			String[] numbers = scanFile.nextLine().split(",");
			int numberOfVertex = Integer.parseInt(numbers[0]);
			int numberOfEdges = Integer.parseInt(numbers[1]);

			scanFile.nextLine(); // Skip third line

			citiesGraph = new City[numberOfVertex];

			// Read all the vertex and add them with their data into the graph
			for (int i = 0; i < numberOfVertex; i++) {
				String p = scanFile.nextLine();
				if (!p.isBlank()) {
					String[] words = p.split(",");
					String country = words[0];
					double latitude = Double.parseDouble(words[1].trim());
					double longitude = Double.parseDouble(words[2].trim());
					City newCity = new City(country, latitude, longitude, i);
					citiesGraph[i] = newCity;
				}
			}

			scanFile.nextLine(); // Skip one line

			// Read all the adjacents, store every adjacent in the Adjacent object and store
			// it in the adjacentList of the city
			for (int i = 0; i < numberOfEdges; i++) {
				String p = scanFile.nextLine();
				if (!p.isBlank()) {
					String[] words = p.split(",");
					City city1 = search(new City(words[0]));
					City city2 = search(new City(words[1]));
					Adjacent adjacent = new Adjacent(city1, city2);
					city1.addAdjacent(adjacent);
				}
			}

			scanFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} 
	}

	// Search for a city in the cities graph
	private City search(City city) {
		for (int i = 0; i < citiesGraph.length; i++) {
			if (citiesGraph[i].compareTo(city) == 0) {
				return citiesGraph[i];
			}
		}
		return null;
	}

	// Geting the entire path from the source city to the target city in one LinkedList
	private LinkedList<City> getPath(City target) {
		LinkedList<City> path = new LinkedList<City>();
		DijkstraNode<City> node = djekstra.getTable()[target.getIndex()];

		if (node == null) {
			path.insertAtHead(target);
			return path;
		}

		while (node != null && node.getPath() != null) {
			path.insertAtHead(node.getData());
			node = djekstra.getTable()[node.getPath().getIndex()];
		}

		if (node != null)
			path.insertAtHead(node.getData());
		return path;
	}


	// Add lines to represent the path from one city to the other
	private void addLine(Pane mapPane, double startX, double startY, double endX, double endY) {
		Line line = new Line(startX, startY, endX, endY);
		line.setStroke(Color.BLUE);
		line.setStrokeWidth(2);
		line.setUserData("dynamic"); // Set data to dynamic for later remove it

		mapPane.getChildren().addAll(line);
	}

	// Create a triangle to represent each country
	private Polygon createTriangle(double size, Color color) {
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(0.0, size / 2, -size / 2, -size / 2, size / 2, -size / 2);
		triangle.setFill(color);
		return triangle;
	}
}
