package database.queries;

import database.DatabaseExtensions;
import database.DatabaseManager;
import entities.transit.TransitRoute;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRouteForTripQuery extends ResultQuery<TransitRoute> {
    private int tripId;

    public GetRouteForTripQuery(int tripId) {
        this.tripId = tripId;
    }

    @Override
    public String getStatement() {
        return "SELECT *" +
                "FROM transitorgtfs.routes r " +
                "WHERE r.route_id = (SELECT route_id FROM transitorgtfs.trips WHERE trip_id = ?)";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException {
        statement.setInt(1, tripId);
    }

    @Override
    public TransitRoute readResult(ResultSet resultSet) {

        try {
            while (resultSet.next()) {
                int routeID = resultSet.getInt("route_id");
                String routeColor = resultSet.getString("route_text_color");
                String routeShortName = resultSet.getString("route_short_name");
                TransitRoute route = new TransitRoute(routeID, routeShortName, Color.BLUE);
                return route;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
