package h3_core.indexing;

import com.uber.h3core.H3Core;
import com.uber.h3core.util.LatLng;

import java.io.IOException;

public class IndexingFunctions {

    public static void main(String[] args) {

        try {

            //This is the core instance on which all the utility functions are available
            H3Core h3Core = H3Core.newInstance();


            //sample location data
            double lat = 12.949050055506168;
            double lng = 80.14086116376426;
            long[] h3IndexResolutionSpectrum = new long[15];

            int res = 1;
            int maxRes = 16;

            /*
            latLngToCell:

            Indexes the location at the specified resolution, returning the index of the cell containing
            the location. This buckets the geographic point into the H3 grid.
             */
            for (int currentRes = 0; currentRes < maxRes; currentRes++) {

                //function to convert lat, lng pair to h3 indexed cell(64 bit)
                long longH3CellValue = h3Core.latLngToCell(lat, lng, currentRes);
                h3IndexResolutionSpectrum[currentRes] = longH3CellValue;

                System.out.println("long h3 cell value for lat: " + lat + " and lng: " + lng + " at resolution level: " + currentRes + " is " + longH3CellValue);
            }

            System.out.println();

            /*
            latLngToCellAddress:

            convert lat, lng pair to h3 string address

             */
            for (int currentRes = 0; currentRes < maxRes; currentRes++) {

                //function to convert lat, lng pair to h3 string address
                String stringH3CellAddress = h3Core.latLngToCellAddress(lat, lng, currentRes);

                System.out.println("String h3 cell address for lat: " + lat + " and lng: " + lng + " at resolution level: " + currentRes + " is " + stringH3CellAddress);
            }

            /*
            cellToLatLng:

            Finds the center of the cell in grid space. See the algorithm description for more information.

            The center will drift versus the centroid of the cell on Earth due to distortion
            from the gnomonic projection within the icosahedron face it resides on and its
            distance from the center of the icosahedron face.
             */

            for (int i = 0; i < maxRes; i++) {

                long h3Index = h3IndexResolutionSpectrum[i];
                LatLng latLng = h3Core.cellToLatLng(h3Index);

                System.out.println("Center latlng value of the H3 index cell at resolution level: " + h3Index + " is " + latLng );
            }



            /*
            results:

            latlngtoCell(lat, lng, res):   lat = 12.949050055506168, lng = 80.14086116376426

            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 0 is 578184786535776255
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 1 is 582683988116635647
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 2 is 587184289209122815
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 3 is 591687682678063103
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 4 is 596191239355760639
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 5 is 600694832540680191
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 6 is 605198434047098879
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 7 is 609702033640914943
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 8 is 614205633253605375
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 9 is 618709232880189439
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 10 is 623212832507428863
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 11 is 627716432134782975
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 12 is 632220031762151935
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 13 is 636723631389522111
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 14 is 641227231016892583
            long h3 cell value for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 15 is 645730830644263076


            latLngToCellAddress(lat,lng,res):  lat = 12.949050055506168, lng = 80.14086116376426

            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 0 is 8061fffffffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 1 is 8161bffffffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 2 is 82618ffffffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 3 is 83618cfffffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 4 is 84618c5ffffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 5 is 85618c47fffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 6 is 86618c4efffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 7 is 87618c4edffffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 8 is 88618c4ed1fffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 9 is 89618c4ed13ffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 10 is 8a618c4ed11ffff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 11 is 8b618c4ed11bfff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 12 is 8c618c4ed11b9ff
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 13 is 8d618c4ed11b8bf
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 14 is 8e618c4ed11b8a7
            String h3 cell address for lat: 12.949050055506168 and lng: 80.14086116376426 at resolution level: 15 is 8f618c4ed11b8a4

             */


        } catch (IOException e) {
            System.out.println(" IO exception occurred: " + e.getMessage());
        }

    }


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
}
