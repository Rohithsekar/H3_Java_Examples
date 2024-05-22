package quickstart;


import org.geotools.referencing.GeodeticCalculator;

public class BearingAndHeading {

    public static void main(String[] args) {

        double lat1 = 12.881823455020859;
        double lon1 = 80.19023944447099;

        double lat2 = 12.944174844954095;
        double lon2 = 80.12326061012031;

        GeodeticCalculator calculator = new GeodeticCalculator();

        // Set starting point
        calculator.setStartingGeographicPoint(lon1, lat1); // Set longitude and latitude of starting point

        // Set destination point
        calculator.setDestinationGeographicPoint(lon2, lat2); // Set longitude and latitude of destination point

        // Calculate bearing (azimuth) from starting point to destination point
        double bearing = 0;
        try {
            bearing = calculator.getAzimuth();
            bearing = bearing < 0 ? bearing + 360 : bearing;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        System.out.println("Bearing from Sithalpakkam to MEPZ(Azimuth) is : " + bearing + " degrees");

        // Calculate heading (forward azimuth) from starting point to destination point
        double heading = 0;
        try {
            calculator.setDirection(0.0, 0.0);
            heading = calculator.getAzimuth();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        System.out.println("Heading (Forward Azimuth): " + heading + " degrees");
    }


}