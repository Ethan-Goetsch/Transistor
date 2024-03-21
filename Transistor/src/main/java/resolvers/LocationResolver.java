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

import resolvers.Exceptions.CallNotPossibleException;
import entities.Coordinate;
import entities.PostCode;

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
    }

    public Coordinate getCordsFromPostCode(String postName) throws CallNotPossibleException
    {
        Coordinate cords = getCordsFromFile(postName);
        return cords == null ? cords = APICaller.getCoordinates(postName) : cords;
    }

    private Coordinate getCordsFromFile(String postName)
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

                Coordinate newCords = new Coordinate(Double.parseDouble(postLatitudeStr), Double.parseDouble(postLongtitudeStr));
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
}