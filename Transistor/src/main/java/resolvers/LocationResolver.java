package resolvers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.Coordinates;
import utils.PostCode;

public class LocationResolver
{
    // "Transistor\\MassZipLatLon.xlsx"
    private String spreadsheetPath;
    private ArrayList<PostCode> postCodes;

    public LocationResolver(String spreadsheetPath)
    {
        this.spreadsheetPath = spreadsheetPath;
        this.postCodes = new ArrayList<PostCode>();

        loadData();

        System.out.println("Loaded " + postCodes.size() + " post codes");
    }

    public Coordinates getCordsFromPostCode(String postName)
    {
        Coordinates cords = getCordsFromFile(postName);

        if (cords == null)
        {
            try
            {
                cords = APICaller.call(postName);
            }
            catch (Exception e)
            {
                System.out.println("API call failed with message: " + e.getMessage());
            }
        }

        return cords;
    }

    private Coordinates getCordsFromFile(String postName)
    {
        for (PostCode postCode : postCodes)
        {
            if (postCode.getName().equalsIgnoreCase(postName))
            {
                return postCode.getCords();
            }
        }
        return null;
    }

    // https://stackoverflow.com/questions/37811334/how-to-read-excel-xlsx-file-in-java
    private void loadData()
    {
        try
        {
            FileInputStream excelFile = new FileInputStream(new File(spreadsheetPath));
            XSSFWorkbook wb = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rowCount;
            rowCount = sheet.getPhysicalNumberOfRows();
            System.out.println("row count: " + rowCount);

            DataFormatter df = new DataFormatter();
            // i = 1 since we skip the first row
            int i = 1;
            Iterator<Row> rowIterator = sheet.iterator();

            // skip the first row
            rowIterator.next();
            do
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                String postName = "";
                String postLatitudeStr = "";
                String postLongtitudeStr = "";

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 0)
                    {
                        postName = df.formatCellValue(cell);
                    }
                    if (cell.getColumnIndex() == 1)
                    {
                        postLatitudeStr = df.formatCellValue(cell);
                    }
                    if (cell.getColumnIndex() == 2)
                    {
                        postLongtitudeStr = df.formatCellValue(cell);
                    }
                }

                Coordinates newCords = new Coordinates(Double.parseDouble(postLatitudeStr), Double.parseDouble(postLongtitudeStr));
                PostCode newPostCode = new PostCode(postName, newCords);

                postCodes.add(newPostCode);

                i++;

                if (i > rowCount)
                {
                    break;
                }
            }
            while (rowIterator.hasNext());

            wb.close();

        }
        catch (Exception e)
        {
            System.out.println("Loading data from spreadsheet failed with message: " + e.getMessage());
            return;
        }
    }
    public static void debug()
    {
        //6211AL	50.85523285	5.692237193
        //6212EA	50.83560203	5.689429256
        //6229ZE	50.82092283	5.708232104

        System.out.println("ltest");
        LocationResolver locationResolver = new LocationResolver("Transistor\\MassZipLatLon.xlsx");
        System.out.println(locationResolver.getCordsFromPostCode("6211AL").getLatitude() + " " + locationResolver.getCordsFromPostCode("6211AL").getLongitude());
        System.out.println(locationResolver.getCordsFromPostCode("6212EA").getLatitude() + " " + locationResolver.getCordsFromPostCode("6212EA").getLongitude());
        System.out.println(locationResolver.getCordsFromPostCode("6229ze").getLatitude() + " " + locationResolver.getCordsFromPostCode("6229ze").getLongitude());
    }
}