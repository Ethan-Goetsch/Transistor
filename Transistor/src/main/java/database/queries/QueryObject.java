package database.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class QueryObject
{
    public abstract String getStatement();
    public void applyParameters(PreparedStatement statement) throws SQLException
    {

    }

    public boolean execute(Connection connection)
    {
        try
        {
            var preparedStatement = connection.prepareStatement(getStatement());
            applyParameters(preparedStatement);
            return preparedStatement.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(Connection connection)
    {
        try
        {
            var preparedStatement = connection.prepareStatement(getStatement());
            applyParameters(preparedStatement);
            return preparedStatement.executeQuery();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}