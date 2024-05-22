package quickstart;

import com.google.maps.internal.PolylineEncoding;
import com.uber.h3core.H3Core;
import com.uber.h3core.util.LatLng;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickStart extends Application {

    public static final String overviewPolyline = "qnrmAcgmhN}BMFcBFIX@JcAAMQICIPiCq@DcDHwF?aDvJ[|@OTWVYL]Hi@DaB@??s@@cAC_@G{@AS?ODOLwBpC??_@j@Kb@SjD??o@lJKt@q@dE[pAIRIDO?}L{@aAGg@AkAHwLlC@j@Cn@MbAg@\\Wj@G^QzBWtCk@rE`@l@r@`BXh@jChGPZlArCr@vApDnId@d@VNpAVzW~KhDrArD`B`G`C\\F_BzI@LJJNDx@FGtC?bBS|A_@`Ay@fBQn@G^GfEBZJLjB`@XRLVGnCBvBUfDS^KHkITSHOVWdAE`@@pBF`E|@lG@jACf@StAOX[nAKp@@NNJtANx@Cf@E`@?JPHpE@~BC|EDtCCRGFwBFuAC}@BQLSXgAlCs@tBoBtEyA`EgHrQoGlOWr@cFlL{M{@gIc@mOq@sXyA__@cBeG[cO_AcIc@eJiBoK}Bu@]{EkE}AoA{FrCq@`@iChB_M|GFdOKjQo@j]MpL_@zQK|Ia@jUm@vQOrCgAhIe@xCYjANLVb@Mb@QLQB[QUWOYeHqHcBcCa@tA^H??_@I`@uA}ByBkEmEyDqDkEoEDRCl@gA`OMnAa@tBAX@z@VjCNnBTjB\\nAv@xB~@rBxAvCR\\AXEJKFgE|@_AJQ?O?MGOdEYbCqCdNqEtRuCjCsKtDYhCU|@Ez@ObBcAhI_@lBkBKaDi@_Ba@{CcAiDyBeB_Bo@s@sAaBqAgBuHcL_BeB_CoAiRmIaAa@aHgDwBeCeBaDw@aDcFmV{BsK]wA_@g@d\\rBt@sDXyBEa@bCPLGH@BDjAgDbAgDnF`B|Cl@JSjB}@";

    private static final double CIRCLE_RADIUS = 2.0;

    private List<Coordinates> originalCoordinates;
    private List<ReversalCoordinateInfo> reversedCoordinates;
    /*
     functions in H3 that convert points to cell identifiers, and from cell identifiers
     back to geometry. These are the core indexing functions used in many applications of H3.
Point / cell

     function example() {
  const lat = 12.949050055506168;
  const lng = 80.14086116376426;
  const res = 10;
  return h3.latLngToCell(lat, lng, res);
}
     */

    // 12.949050055506168, 80.14086116376426 MIT college, Aerospace dept
    //12.946376830221988, 80.1364872949673 Parvathy Hospital
    //12.92158135912479, 80.19753860794549 Jeyachandran textiles
    public static void main(String[] args) throws IOException {

//        launch(args);

        double mitLat = 12.949050055506168;
        double mitLng = 80.14086116376426;
        double hospitalLat = 12.946376830221988;
        double hospitalLng = 80.1364872949673;
        double jeyaChandranLat = 12.92158135912479;
        double jeyaChandranLng = 80.19753860794549;

        short res = 11;
        H3Core h3Core = H3Core.newInstance();

        long mitH3Index = h3Core.latLngToCell(mitLat, mitLng, res);
        long hospitalIndex = h3Core.latLngToCell(hospitalLat, hospitalLng, res);
        long jeyaChandranIndex = h3Core.latLngToCell(jeyaChandranLat, jeyaChandranLng, res);


        String mitStringIndex = h3Core.h3ToString(mitH3Index);
        LatLng mitIndexlatLng = h3Core.cellToLatLng(mitH3Index);
        LatLng mitStringIndexLatLng = h3Core.cellToLatLng(mitStringIndex);


        String hospitalStringIndex = h3Core.h3ToString(hospitalIndex);
        LatLng hospitalStringIndexLatLng = h3Core.cellToLatLng(hospitalStringIndex);

        String jeyaChandranStringIndex = h3Core.h3ToString(jeyaChandranIndex);
        LatLng jeyaChandranStringIndexLatLng = h3Core.cellToLatLng(jeyaChandranStringIndex);

        String[] h3Strings = new String[]{mitStringIndex, hospitalStringIndex, jeyaChandranStringIndex};

        generateKML(h3Strings, "src/main/resources/output.kml");


        System.out.println("The LatLng object obtained via h3Core.cellToLatLng(h3Index) method is " + mitIndexlatLng);
        System.out.println("The LatLng object obtained via h3Core.cellToLatLng(h3String) method is " + mitStringIndexLatLng);

        System.out.println("H3 index for Aerospace Department Building , MIT campus , Chromepet is " + mitH3Index);
        System.out.println("the output to h3core.h3ToString(" + mitH3Index + ") method call is " + mitStringIndex);

         //Example usage of utility methods
        int resolution = getRes(mitH3Index);
        int baseCell = getBaseCell(mitH3Index);
        int mode = getMode(mitH3Index);
        int reserved = getReserved(mitH3Index);

        System.out.println("Resolution of h3 index: " + mitH3Index + " is " + resolution);
        System.out.println("Base Cell of h3 index: " + mitH3Index + " is " + baseCell);
        System.out.println("Mode of h3 index: " + mitH3Index + " is " + mode);
        System.out.println("Reserved bit value is : " + reserved);

        List<com.google.maps.model.LatLng> overviewCoordinates = PolylineEncoding.decode(overviewPolyline);
//
        List<com.google.maps.model.LatLng> decoded = PolylineEncoding.decode("qnrmAcgmhN}BMFcBFIX@JcAAMQICIPiCq@DcDHwF?aDvJ[|@OTWVYL]Hi@DaB@??s@@cAC_@G{@AS?ODOLwBpC??_@j@Kb@SjD??o@lJKt@q@dE[pAIRIDO?}L{@aAGg@AkAHwLlC@j@Cn@MbAg@\\Wj@G^QzBWtCk@rE`@l@r@`BXh@jChGPZlArCr@vApDnId@d@VNpAVzW~KhDrArD`B`G`C\\F_BzI@LJJNDx@FGtC?bBS|A_@`Ay@fBQn@G^GfEBZJLjB`@XRLVGnCBvBUfDS^KHkITSHOVWdAE`@@pBF`E|@lG@jACf@StAOX[nAKp@@NNJtANx@Cf@E`@?JPHpE@~BC|EDtCCRGFwBFuAC}@BQLSXgAlCs@tBoBtEyA`EgHrQoGlOWr@cFlL{M{@gIc@mOq@sXyA__@cBeG[cO_AcIc@eJiBoK}Bu@]{EkE}AoA{FrCq@`@iChB_M|GFdOKjQo@j]MpL_@zQK|Ia@jUm@vQOrCgAhIe@xCYjANLVb@Mb@QLQB[QUWOYeHqHcBcCa@tA^H??_@I`@uA}ByBkEmEyDqDkEoEDRCl@gA`OMnAa@tBAX@z@VjCNnBTjB\\nAv@xB~@rBxAvCR\\AXEJKFgE|@_AJQ?O?MGOdEYbCqCdNqEtRuCjCsKtDYhCU|@Ez@ObBcAhI_@lBkBKaDi@_Ba@{CcAiDyBeB_Bo@s@sAaBqAgBuHcL_BeB_CoAiRmIaAa@aHgDwBeCeBaDw@aDcFmV{BsK]wA_@g@d\\rBt@sDXyBEa@bCPLGH@BDjAgDbAgDnF`B|Cl@JSjB}@");
        generateKMLForLatLngList(decoded, "src/main/resources/out.kml");
        List<Coordinates> latLngList = readCoordinatesFromKML ("src/main/resources/out.kml");
//
//        List<Double> latitudes = new ArrayList<>();
//        List<Double> longitudes = new ArrayList<>();
//
//        for(Coordinates latlng: latLngList){
////            System.out.println(latlng);
//            latitudes.add(latlng.getLat());
//            longitudes.add(latlng.getLng());
//        }
//
//
//
//        System.out.println(" list size is  " + latLngList.size() + "latitue list size is " + latitudes.size()
//        + " and longitudes list size is " + longitudes.size());
//
//        identifyLanes(latitudes, longitudes);


//        for(com.google.maps.model.LatLng latLng: overviewCoordinates){
//            count++;
//            long h3IntegerIndex = h3Core.latLngToCell(latLng.lat, latLng.lng, res);
//            String h3StringIndex = h3Core.h3ToString(h3IntegerIndex);
//            tripH3StringIndices.add(h3StringIndex);
//
//        }


        int count = 0;
        List<String> tripH3StringIndices = new ArrayList<>();
        for (com.google.maps.model.LatLng latLng : overviewCoordinates) {
            count++;
            long h3IntegerIndex = h3Core.latLngToCell(latLng.lat, latLng.lng, res);
            String h3StringIndex = h3Core.h3ToString(h3IntegerIndex);
            tripH3StringIndices.add(h3StringIndex);

        }
//
//
//        List<Coordinates> coordinatesList = new ArrayList<>();
//
//        for (com.google.maps.model.LatLng latLng : overviewCoordinates) {
//            count++;
//            Coordinates coordinates = new Coordinates(latLng.lat, latLng.lng);
//            coordinatesList.add(coordinates);
//        }
//
        writeCoordinatesToFile(latLngList, "src/main/resources/latlng.txt");
//        System.out.println("coordinates written to output file");


//        System.out.println("count value is " + count);

        generateKMLWithH3StringIndexList(tripH3StringIndices, "src/main/resources/tripRes11.kml");


    }

    private static void writeCoordinatesToFile(List<Coordinates> coordinatesList, String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Iterate over the coordinates list and write each coordinate to the file
            for (Coordinates coordinates : coordinatesList) {
                writer.write(coordinates.toString());
                writer.newLine(); // Add a newline for better readability
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }


    // Utility method to extract a specific range of bits from the H3 index
    /*
    Suppose lower = 2 and upper = 5. Without adding 1, the expression (upper - lower)
    would evaluate to 5 - 2 = 3. However, in a range like (2, 5), we have four values:
    2, 3, 4, and 5.

    If we had not added (upper-lower) + 1 , we would have missed out the upper bound in the range.

    So, to include all the values in the range, we add 1 to (upper - lower), making it
    3 + 1 = 4. This ensures that the bitmask created by (1L << (upper - lower + 1)) - 1
    has enough bits to cover the entire inclusive range.

    Subtraction of 1:

   - 1: This subtracts 1 from the binary number, resulting in all 1s up to the specified
    bit position. Using the earlier example, the result becomes 0111 in binary.
    By subtracting 1, you essentially create a bitmask with all 1s in the bits representing
    the range from 0 to (upper - lower + 1). This is useful when you want to cover an
    inclusive range of bits.

For example, if (upper - lower + 1) is 3, without the subtraction of 1, the bitmask would be 1000, and with the subtraction, it becomes 0111, including all bits up to position 2 (assuming positions are zero-indexed). This ensures that you capture the entire range in a bitmask when working with inclusive representations.
     */
    private static int getIndexDigit(long h3Index, int lower, int upper) {
        long mask = (1L << (upper - lower + 1)) - 1;
        return (int) ((h3Index >> lower) & mask);
    }

    // Utility method to get the mode from the H3 index
    private static int getMode(long h3Index) {
        return getIndexDigit(h3Index, 1, 4);
    }

    // Utility method to get the base cell from the H3 index
    private static int getBaseCell(long h3Index) {
        return getIndexDigit(h3Index, 12, 18);
    }

    // Utility method to get the resolution from the H3 index
    private static int getRes(long h3Index) {
        return getIndexDigit(h3Index, 8, 11);
    }

    // Utility method to get the reserved bits from the H3 index
    private static int getReserved(long h3Index) {
        return getIndexDigit(h3Index, 0, 0);
    }

    public static void generateKML(String[] h3Indexes, String outputFile) throws IOException {
        FileWriter writer = new FileWriter(outputFile);

        // KML header
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        writer.write("<Document>\n");

        // Create placemarks and polygons for each H3 cell
        for (String h3Index : h3Indexes) {
            // Extract coordinates and resolution from H3 index
            H3Core h3Core = H3Core.newInstance();
            LatLng latLng = h3Core.cellToLatLng(h3Index);
            double[] coordinates = new double[]{latLng.lat, latLng.lng};

            int resolution = h3Core.getResolution(h3Index);

            // Create placemark
            writer.write("<Placemark>\n");
            writer.write("<Point>\n");
            writer.write("<coordinates>" + coordinates[1] + "," + coordinates[0] + "</coordinates>\n");
            writer.write("</Point>\n");

            // Create hexagonal polygon
            writer.write("<Polygon>\n");
            writer.write("<outerBoundaryIs>\n<LinearRing>\n<coordinates>\n");

            // Generate hexagonal vertices
            for (int i = 0; i < 7; i++) {
                double angle = 2.0 * Math.PI * i / 6;
                double vertexLat = coordinates[0] + 0.0001 * Math.sin(angle);
                double vertexLon = coordinates[1] + 0.0001 * Math.cos(angle);
                writer.write(vertexLon + "," + vertexLat + "\n");
            }

            writer.write("</coordinates>\n</LinearRing>\n</outerBoundaryIs>\n");
            writer.write("</Polygon>\n");

            writer.write("</Placemark>\n");
        }

        // KML footer
        writer.write("</Document>\n");
        writer.write("</kml>");
        writer.close();
    }

    public static void generateKML(double[][] coordinatesList, String outputFile) throws IOException {
        FileWriter writer = new FileWriter(outputFile);

        // KML header
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        writer.write("<Document>\n");

        // Create a single LineString for the entire polyline
        writer.write("<Placemark>\n");
        writer.write("<LineString>\n");
        writer.write("<coordinates>\n");

        // Create the polyline by concatenating coordinates
        for (double[] coordinate : coordinatesList) {
            double lat = coordinate[0];
            double lng = coordinate[1];

            // Append the coordinates to the LineString
            writer.write(lng + "," + lat + "\n");
        }

        writer.write("</coordinates>\n");
        writer.write("</LineString>\n");
        writer.write("</Placemark>\n");

        // KML footer
        writer.write("</Document>\n");
        writer.write("</kml>");
        writer.close();
    }

    public static void generateKMLWithH3StringIndexList(List<String> h3Indexes, String outputFile) throws IOException {
        FileWriter writer = new FileWriter(outputFile);

        // KML header
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        writer.write("<Document>\n");

        // Create placemarks and polygons for each H3 cell
        for (String h3Index : h3Indexes) {
            // Extract coordinates and resolution from H3 index
            H3Core h3Core = H3Core.newInstance();
            LatLng latLng = h3Core.cellToLatLng(h3Index);
            double[] coordinates = new double[]{latLng.lat, latLng.lng};

            int resolution = h3Core.getResolution(h3Index);

            // Create placemark
            writer.write("<Placemark>\n");
            writer.write("<Point>\n");
            writer.write("<coordinates>" + coordinates[1] + "," + coordinates[0] + "</coordinates>\n");
            writer.write("</Point>\n");

            // Create hexagonal polygon
            writer.write("<Polygon>\n");
            writer.write("<outerBoundaryIs>\n<LinearRing>\n<coordinates>\n");

            // Generate hexagonal vertices
            for (int i = 0; i < 7; i++) {
                double angle = 2.0 * Math.PI * i / 6;
                double vertexLat = coordinates[0] + 0.0001 * Math.sin(angle);
                double vertexLon = coordinates[1] + 0.0001 * Math.cos(angle);
                writer.write(vertexLon + "," + vertexLat + "\n");
            }

            writer.write("</coordinates>\n</LinearRing>\n</outerBoundaryIs>\n");
            writer.write("</Polygon>\n");

            writer.write("</Placemark>\n");
        }

        // KML footer
        writer.write("</Document>\n");
        writer.write("</kml>");
        writer.close();
    }


    // Implement this method to extract coordinates from H3 index
    private static double[] getCoordinatesFromH3Index(H3Core h3Core, String h3String) {
        // Implement logic to convert H3 index to coordinates
        LatLng latLng = h3Core.cellToLatLng(h3String);
        return new double[]{latLng.lat, latLng.lng};
    }

    public static void generateKMLForLatLngList(List<com.google.maps.model.LatLng> coordinatesList, String outputFile) throws IOException {
        FileWriter writer = new FileWriter(outputFile);

        // KML header
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        writer.write("<Document>\n");

        // Create a single LineString for the entire polyline
        writer.write("<Placemark>\n");
        writer.write("<LineString>\n");
        writer.write("<coordinates>\n");

        // Create the polyline by concatenating coordinates
        for (com.google.maps.model.LatLng latLng : coordinatesList) {

            double lat = latLng.lat;
            double lng = latLng.lng;

            // Append the coordinates to the LineString
            writer.write(lng + "," + lat + "\n");
        }

        writer.write("</coordinates>\n");
        writer.write("</LineString>\n");
        writer.write("</Placemark>\n");

        // KML footer
        writer.write("</Document>\n");
        writer.write("</kml>");
        writer.close();
    }

    public static List<Coordinates> readCoordinatesFromKML(String filePath) {
        List<Coordinates> coordinates = new ArrayList<>();

        try {
            File inputFile = new File(filePath);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element rootNode = document.getRootElement();

            // Retrieve the namespace URI dynamically from the root element
            Namespace kmlNamespace = rootNode.getNamespace();
            String kmlNamespaceURI = kmlNamespace.getURI();

            System.out.println("root node is " + rootNode);
            System.out.println("namespace is "+ kmlNamespace);
            System.out.println("namespace URI is "+ kmlNamespaceURI);
            Element documentNode = rootNode.getChild("Document", kmlNamespace);
            if (documentNode != null) { // Check if Document element exists

                System.out.println(" document node is not null....");
                List<Element> placemarkNodes = documentNode.getChildren("Placemark", kmlNamespace);
                for (Element placemarkNode : placemarkNodes) {

                    System.out.println(" Placemark node is not null...");
                    Element lineStringNode = placemarkNode.getChild("LineString", kmlNamespace);
                    if (lineStringNode != null) { // Check if LineString element exists

                        System.out.println(" LineString node is not null..");
                        Element coordinatesNode = lineStringNode.getChild("coordinates", kmlNamespace);
                        if (coordinatesNode != null) { // Check if coordinates element exists

                            System.out.println(" coordinatesNode is not null...");
                            String[] coords = coordinatesNode.getText().trim().split("\\s+");
                            for (String coord : coords) {
                                String[] latLng = coord.split(",");
                                double longitude = Double.parseDouble(latLng[0]);
                                double latitude = Double.parseDouble(latLng[1]);
                                coordinates.add(new Coordinates(latitude, longitude));
                            }
                        }
                    }
                }
            }else {
                System.out.println(" document node is null");
            }
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return coordinates;
    }

    // Function to calculate bearing angle between two points
    private static double calculateBearing(double lat1, double lon1, double lat2, double lon2) {

        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δλ = Math.toRadians(lon2 - lon1);

        System.out.println("φ1(lat1) is " + φ1 + ", φ2(lat2) is " + φ2 + " Δλ(difference in longitudes in radian) is " + Δλ);


        double y = Math.sin(Δλ) * Math.cos(φ2);
        double x = Math.cos(φ1) * Math.sin(φ2) - Math.sin(φ1) * Math.cos(φ2) * Math.cos(Δλ);

        double bearingInRadian = Math.atan2(y, x);
        double bearingInDegree = Math.toDegrees(bearingInRadian);

        // Ensure that bearing is positive and within the range of 0 to 360 degrees
        bearingInDegree = (bearingInDegree < 0) ? (bearingInDegree + 360) : bearingInDegree;
        bearingInDegree %= 360;
//        double positiveBearingInDegree = bearingInDegree<0?bearingInDegree:bearingInDegree+360;

        System.out.println(" bearing in radians is " + bearingInRadian + " and bearing in degrees is " + bearingInDegree);

        return bearingInDegree;
    }

    // Function to identify left and right lanes based on changes in bearing angles
    public static List<ReversalCoordinateInfo> identifyLanes(List<Double> latitudes, List<Double> longitudes) {
        // Initialize variables to track lane assignments
        boolean isLeftLane = true; // Start with the assumption that the first point is in the left lane

        List<ReversalCoordinateInfo> reversedCoordinates = new ArrayList<>();
        // Iterate through the dataset
        for (int i = 1; i < latitudes.size(); i++) {
            double lat1 = latitudes.get(i - 1);
            double lon1 = longitudes.get(i - 1);
            double lat2 = latitudes.get(i);
            double lon2 = longitudes.get(i);

            // Calculate bearing angle from current point to next point
            double bearing = calculateBearing(lat1, lon1, lat2, lon2);


            // Detect reversal in direction
            if (bearing > 180) {
                // Update lane assignment
                isLeftLane = !isLeftLane;
                Coordinates from = new Coordinates(lat1, lon1);
                Coordinates to = new Coordinates(lat2, lon2);
                reversedCoordinates.add(new ReversalCoordinateInfo(from, to, bearing));
            }

            // Assign current point to left or right lane based on lane assignment
            if (isLeftLane) {
                // Assign to left lane
            } else {
                // Assign to right lane
            }
        }

        System.out.println("reversed coordinates list size is " + reversedCoordinates.size());

//        for(ReversalCoordinateInfo reversalCoordinateInfo: reversedCoordinates){
//            System.out.println(reversalCoordinateInfo);
//        }
        return reversedCoordinates;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();

        List<Coordinates> latLngList = readCoordinatesFromKML ("src/main/resources/out.kml");

        List<Double> latitudes = new ArrayList<>();
        List<Double> longitudes = new ArrayList<>();

        for(Coordinates latlng: latLngList){
//            System.out.println(latlng);
            latitudes.add(latlng.getLat());
            longitudes.add(latlng.getLng());
        }

        List<ReversalCoordinateInfo> reversalCoordinateInfos = identifyLanes(latitudes, longitudes);

        // Plot original coordinates
        plotCoordinates(latLngList, Color.BLUE, pane);

        // Plot reversed coordinates
        plotReversedCoordinates(reversalCoordinateInfos, Color.RED, pane);

        primaryStage.setScene(new Scene(pane, 800, 600));
        primaryStage.setTitle("Coordinate Visualization");
        primaryStage.show();
    }

    private void plotCoordinates(List<Coordinates> coordinates, Color color, Pane pane) {
        for (Coordinates coord : coordinates) {
            Circle circle = new Circle(coord.getLng(), coord.getLat(), CIRCLE_RADIUS);
            circle.setFill(color);
            pane.getChildren().add(circle);
        }
    }

    private void plotReversedCoordinates(List<ReversalCoordinateInfo> reversedCoordinates, Color color, Pane pane) {
        for (ReversalCoordinateInfo reversalCoord : reversedCoordinates) {
            Coordinates from = reversalCoord.getFrom();
            Coordinates to = reversalCoord.getTo();

            // Plot from coordinate
            Circle fromCircle = new Circle(from.getLng(), from.getLat(), CIRCLE_RADIUS);
            fromCircle.setFill(color);
            pane.getChildren().add(fromCircle);

            // Plot to coordinate
            Circle toCircle = new Circle(to.getLng(), to.getLat(), CIRCLE_RADIUS);
            toCircle.setFill(color);
            pane.getChildren().add(toCircle);
        }
    }
}


