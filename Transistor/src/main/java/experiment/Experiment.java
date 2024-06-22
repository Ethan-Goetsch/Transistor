//package experiment;
//
//import application.ApplicationManager;
//import entities.*;
//import entities.exceptions.*;
//import resolvers.LocationResolver;
//import java.io.FileWriter;
//import java.io.IOException;
//import com.opencsv.CSVWriter;
//
//public class Experiment {
//    public static void main(String[] args) throws NetworkErrorException, CallNotPossibleException, InvalidCoordinateException, RateLimitExceededException, PostcodeNotFoundException {
//        // Origin Array List with 6 postal codes
//        ApplicationManager applicationManager = new ApplicationManager(new LocationResolver(), null, null);
//        String[] originArrayList = {
//                "6211JA",
//                "6212CG",
//                "6213BK",
//                "6214RW",
//                "6215XA",
//                "6216AM"
//        };
//
//        // Destination Array List with 5 postal codes
//        String[] destinationArrayList = {
//                "6217XM",
//                "6218EX",
//                "6219BJ",
//                "6221BC",
//                "6222TJ"
//        };
//
//        Coordinate[] originCoordinates = new Coordinate[originArrayList.length];
//        LocationResolver locationResolver = new LocationResolver();
//        for (int i = 0; i < originArrayList.length; i++) {
//            originCoordinates[i] = locationResolver.getCordsFromPostCode(originArrayList[i]);
//        }
//
//        Coordinate[] destinationCoordinates = new Coordinate[destinationArrayList.length];
//        for (int i = 0; i < destinationArrayList.length; i++) {
//            destinationCoordinates[i] = locationResolver.getCordsFromPostCode(destinationArrayList[i]);
//        }
//
//        try(CSVWriter writer = new CSVWriter(new FileWriter("journey_times.csv"))){
//            String[] header = {"Trial", "Travel Time"};
//            writer.writeNext(header);
//            for (Coordinate coordinate: originCoordinates) {
//                for (Coordinate destinationCoordinate: destinationCoordinates) {
//                    Journey journey = applicationManager.getJourney(coordinate, destinationCoordinate,  new RouteRequest("", "", TransportType.BUS, RouteType.ACTUAL));
//                    String[] data = {coordinate.toString() + " to " + destinationCoordinate.toString(), Double.toString(journey.getTotalTravelTime())};
//                    writer.writeNext(data);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
