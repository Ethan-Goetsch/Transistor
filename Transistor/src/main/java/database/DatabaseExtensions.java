package database;

import java.sql.ResultSet;

public class DatabaseExtensions
{
    public static void printResults(ResultSet resultSet)
    {
        try
        {
            while (resultSet.next())
            {
                var printData = "";
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                {
                    printData += resultSet.getString(i) + " ";
                }
                System.out.println(printData);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}