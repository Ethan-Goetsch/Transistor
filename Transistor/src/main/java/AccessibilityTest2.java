import application.ApplicationManager;
import application.RequestValidator;
import calculators.*;
import entities.AccessibilityMeasure;
import entities.AccessibilityRequest;
import entities.TransportType;
import entities.geoJson.GeoDeserializer;
import resolvers.LocationResolver;
import utils.PathLocations;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AccessibilityTest2 {
    public static void main(String[] args) {

        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        RequestValidator requestValidator = new RequestValidator();

        accessibility.IndexCalculator accessibilityCalculator = new accessibility.IndexCalculator();
        TransitGraphCalculator transitGraphCalculator = new TransitGraphCalculator();

        var transitCalculators = new ArrayList<TransitCalculator>();
        transitCalculators.add(new DirectTransitCalculator());
        transitCalculators.add(new TransferTransitCalculator(transitGraphCalculator));

        AerialCalculator aerialCalculator = new AerialCalculator();
        GeoDeserializer geoDeserializer = new GeoDeserializer();

        ApplicationManager manager = new ApplicationManager(locationResolver, requestValidator, accessibilityCalculator, transitCalculators, aerialCalculator);
        accessibilityCalculator.setManager(manager);

        // Create the Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Accessibility Data");

        // Create header row
        String[] headers = {"Postal Code", "Coordinate", "Healthcare Disabled", "Education Disabled", "Shop Disabled", "Entertainment Disabled", "Tourism Disabled", "Public Services Disabled", "Transportation Disabled", "Healthcare General", "Education General", "Shop General", "Entertainment General", "Tourism General", "Public Services General", "Transportation General"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Write data
        int rowNum = 1;
        for (String postalCode : getPostalCodes()) {
            if (postalCode.equals("---")) {
                continue;
            }
            AccessibilityMeasure measureDisabled = manager.calculateAccessibilityMeasure(new AccessibilityRequest(postalCode, true, 3, TransportType.BUS));
            AccessibilityMeasure measureGeneral = manager.calculateAccessibilityMeasure(new AccessibilityRequest(postalCode, false, 3,TransportType.BUS));

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(postalCode);
            row.createCell(1).setCellValue(measureDisabled.postalCodeLocation().toString());
            row.createCell(2).setCellValue(measureDisabled.indexes().get(0));
            row.createCell(3).setCellValue(measureDisabled.indexes().get(1));
            row.createCell(4).setCellValue(measureDisabled.indexes().get(2));
            row.createCell(5).setCellValue(measureDisabled.indexes().get(3));
            row.createCell(6).setCellValue(measureDisabled.indexes().get(4));
            row.createCell(7).setCellValue(measureDisabled.indexes().get(5));
            row.createCell(8).setCellValue(measureDisabled.indexes().get(6));
            row.createCell(9).setCellValue(measureGeneral.indexes().get(0));
            row.createCell(10).setCellValue(measureGeneral.indexes().get(1));
            row.createCell(11).setCellValue(measureGeneral.indexes().get(2));
            row.createCell(12).setCellValue(measureGeneral.indexes().get(3));
            row.createCell(13).setCellValue(measureGeneral.indexes().get(4));
            row.createCell(14).setCellValue(measureGeneral.indexes().get(5));
            row.createCell(15).setCellValue(measureGeneral.indexes().get(6));
        }

        // Write the output to an Excel file
        try (FileOutputStream fileOut = new FileOutputStream("accessibility_data1.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String[] getPostalCodes() {
        return new String[]{
                "6211JA", "6211GA", "6211JZ", "6211LP", "6211MC", "6211TG", "6211RW", "6211PL", "6211PN", "6211XS", "---",
                "6212CG", "6212AE", "6212BT", "6212AW", "6212CW", "6212GN", "6212EZ", "6212HC", "6212CL", "6212EP", "---",
                "6213BK", "6213CJ", "6213EB", "6213GA", "6213GM", "6213HE", "6213JG", "6213KB", "6213NC", "6213AB", "---",
                "6214RW", "6214RH", "6214AL", "6214PL", "6214AD", "6214AJ", "6214TK", "6214RT", "6214AB", "6214TT", "---",
                "6215XA", "6215KC", "6215VM", "6215VK", "6215XM", "6215TR", "6215RT", "6215JR", "6215GE", "6215VG", "---",
                "6216AM", "6216XP", "6216RA", "6216EV", "6216PL", "6216PE", "6216PH", "6216XD", "6216EM", "6216BW", "---",
                "6217XM", "6217AC", "6217LR", "6217CV", "6217PX", "6217KA", "6217GG", "6217EN", "6217HN", "6217XS", "---",
                "6218EX", "6218RC", "6218RS", "6218AR", "6218VC", "6218EX", "6218HK", "6218EH", "6218VH", "6218SP", "---",
                "6219BJ", "6219AZ", "6219BC", "6219BE", "6219BT", "6219AG", "6219AC", "6219BX", "6219BP", "6219AW", "---",
                "6221BC", "6221CM", "6221BZ", "6221AL", "6221VD", "6221HB", "6221BC", "6221XE", "6221SW", "6221HE", "---",
                "6222TJ", "6222BP", "6222EC", "6222TC", "6222VM", "6222VZ", "6222CM", "6222AG", "6222XM", "6222VH", "---",
                "6223CS", "6223CG", "6223GM", "6223GR", "6223AE", "6223AK", "6223EA", "6223AW", "6223AH", "6223CL", "---",
                "6224JT", "6224JN", "6224XJ", "6224AT", "6224EM", "6224LG", "6224KH", "6224AD", "6224LJ", "6224LS", "---",
                "6225AZ", "6225BR", "6225CA", "6225CD", "6225GT", "6225GZ", "6225JT", "6225KM", "6225JS", "6225GK", "---",
                "6226GT", "6226EN", "6226GP", "6226CV", "6226GH", "6226EA", "6226AJ", "6226NE", "6226GS", "6226CJ", "---",
                "6227BZ", "6227BG", "6227CB", "6227RW", "6227EE", "6227EW", "6227SK", "6227SZ", "6227HD", "6227XB", "---",
                "6228EE", "6228XV", "6228GK", "6228EM", "6228SP", "6228AR", "6228EG", "6228GB", "6228BV", "6228AH", "---",
                "6229VJ", "6229ZC", "6229SB", "6229WH", "6229ZD", "6229XT", "6229VK", "6229TE", "6229EM", "6229VE", "---"
        };
    }
}
