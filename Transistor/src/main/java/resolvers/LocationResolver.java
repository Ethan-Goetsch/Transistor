package resolvers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.Coordinates;

public class LocationResolver
{
    private String spreadsheetPath;
    private ArrayList<PostCode> postCodes;

    class PostCode
    {
        String name;
        Coordinates cords;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Coordinates getCords() {
            return cords;
        }
        public void setCords(Coordinates cords) {
            this.cords = cords;
        }
        public PostCode(String name, Coordinates cords)
        {
            this.name = name;
            this.cords = cords;
        }
    
    }

    public LocationResolver(String spreadsheetPath)
    {
        this.spreadsheetPath = spreadsheetPath;
        this.postCodes = new ArrayList<PostCode>();
    }

    public void readData()
    {
        try 
        {
            FileInputStream excelFile = new FileInputStream(new File(spreadsheetPath));
            XSSFWorkbook wb = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rowCount;
            rowCount= sheet.getPhysicalNumberOfRows();
            System.out.println("row count: " + rowCount);

            // iterate the excel file
            int i = 1;
            Iterator<Row> rowIterator = sheet.iterator();

            //skip the first row
            rowIterator.next();
        do
        {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            String postName = "";
            String postLatitudeStr = "";
            String postLongtitudeStr = "";

            while(cellIterator.hasNext())
            {
                Cell cell = cellIterator.next();
                    if(cell.getColumnIndex()==0)
                    {
                        DataFormatter df = new DataFormatter();
                        postName = df.formatCellValue(cell);
                    }
                    if(cell.getColumnIndex()==1)
                    {
                        DataFormatter df = new DataFormatter();
                        postLatitudeStr = df.formatCellValue(cell);
                    }
                    if(cell.getColumnIndex()==2)
                    {
                        DataFormatter df = new DataFormatter();
                        postLongtitudeStr = df.formatCellValue(cell);
                    }
            }



            //System.out.println(postName + " " + postLatitudeStr + " " + postLongtitudeStr);

            Coordinates newCords = new Coordinates(Double.parseDouble(postLatitudeStr), Double.parseDouble(postLongtitudeStr));
            PostCode newPostCode = new PostCode(postName, newCords);

            postCodes.add(newPostCode);

            i++;

            if(i > rowCount)
            {
                break;
            }
            //break;
        }
        while(rowIterator.hasNext());

        wb.close();

        }
        catch (Exception e)
        {
            System.out.println("fe: " + e.getMessage());
            return;
        }
    }

    public void printCodes()
    {
        for (PostCode postCode : postCodes)
        {
            System.out.println(postCode.getName() + " " + postCode.getCords().getLatitude() + " " + postCode.getCords().getLongtitude());
        }
    }

    public static void debug()
    {
        System.out.println("ltest");
        LocationResolver locationResolver = new LocationResolver("Transistor\\MassZipLatLon.xlsx");
        locationResolver.readData();
        locationResolver.printCodes();
    }

}
