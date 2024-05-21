# Transitor

## Description

Transitor is a Java-based routing engine developed for Maastricht’s public transport system. It aims to analyze and improve socio-economic accessibility across the city by integrating geographical data, public transport information, and socio-economic metrics.

## Installation

1. Clone the repository: `git clone https://github.com/Ethan-Goetsch/Transistor.git`
2. Navigate to the project directory: `cd transitor`
3. Set up the required database and load the GTFS data.
   1. Download the GTFS data from Canvas-Project1-2.
   2. Load the GTFS data into the database
   3. Create a new database connection in Dbeaver 
   4. Connect the database with transitor
4. Set up the required dependencies.<br/>
   mvn clean install

## Dependencies

This project uses Maven for dependency management. The required dependencies are listed in the `pom.xml` file and include:

- Apache POI for reading and writing Microsoft Office files
- GraphHopper for routing and navigation
- JXMapViewer2 for displaying interactive maps
- Gson for converting Java Objects into their JSON representation and vice versa
- JUnit for testing
- MySQL Connector for connecting to a MySQL database

## Usage
1. Run the application: `java Transitor`
2. Enter the starting and destination postal codes in the GUI to find the best route.
3. The application will calculate and display the distance and travel time, including bus routes and walking/cycling options.
4. To switch to view Maastricht's transit map, click the "Show Map" button.

## Contributing
1. Dani Angelov
2. Ethan Goetsch
3. Vojtěch Kosatík
4. Julian Nijhuis Nijhuis
5. Huy Pham
6. Nikita Souslau
7. Michał Urbanowicz


