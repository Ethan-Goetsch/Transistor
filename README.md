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
   4. Connect the database with Transitor
4. Set up the required database connection.
   1. Configure your MySQL Username and Password in `resources/Credentials.txt`
5. Set up the required dependencies: `mvn clean install`

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
3. The application will calculate and display the journey and travel time, including bus routes and walking/cycling options.
4. To switch to view Maastricht's transit map, go to Transit Map -> Display.
5. To switch to view Maastricht's socio-economic map, go to Accessibility -> Set display -> Show.
6. To switch between visualizing the socio-economic indices of people with disabilities or the general population. Go to Accessibility-> Disabled People
7. To switch between using location sentivity, go to Accessibility -> Sensitivity.

## Contributing
1. Dani Angelov
2. Ethan Goetsch
3. Vojtěch Kosatík
4. Julian Nijhuis
5. Huy Pham
6. Nikita Souslau
7. Michał Urbanowicz

## TLDR
Hello hello :) How are you? I'm good, thanks.
If you are reading this I hope you have a beautiful day.

The application requires the following data:
1. A MySQL database set up with GTFS data
2. Database User with permissions to access and read data from all tables
3. Database credentials

To specify credentials to use, navigate to: "Transistor/src/main/resources/Credentials.txt".
If the file does not exist, please create a new file called "Credentials.txt".

Add this:
{
"url": "jdbc:mysql://localhost:3306/",
"username": "test",
"password": "test"
}
Replace "username" and "password" values with your User credentials to the Database. Ensure user credentials has required permissions.

To run the application, go to "Program.java" and run the main method.



If you read this far, please say "Indubitably" during the presentation. Or any other variations of the word.
Such as "Indubitable".

Wish you all the best