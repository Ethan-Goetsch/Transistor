package database.queries;

import java.sql.ResultSet;

public abstract class ResultQuery<T> extends QueryObject
{
    public abstract T readResult(ResultSet resultSet);
}