package at.fh.sve.ue04.ms;

import at.fh.sve.domain.AnalyzedParkingPlace;
import at.fh.sve.domain.ParkingSpaceCoordinate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class RestClientTest {
    private static final String BASE_URL = "http://ai-myproject.10.0.75.2.nip.io/analyze";

    @org.junit.Test
    public void healthTest() {
        String result = ClientBuilder.newClient()
                .target(BASE_URL)
                .path("health")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(result);
    }

    @org.junit.Test
    public void test() {
        AnalyzedParkingPlace result = ClientBuilder.newClient()
                .target(BASE_URL)
                .queryParam("url", "http://92.39.160.45:8081/mjpg/video.mjpg")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(AnalyzedParkingPlace.class);

        System.out.println(result);
    }

    @Test
    public void substractListsTest() {
        List<ParkingSpaceCoordinate> nearest = new ArrayList<ParkingSpaceCoordinate>() {{
            add(new ParkingSpaceCoordinate(48, 14, 50, 30));
            add(new ParkingSpaceCoordinate(12, 16, 51, 30));
            add(new ParkingSpaceCoordinate(23, 7, 53, 20));
            add(new ParkingSpaceCoordinate(32, 18, 30, 10));
            add(new ParkingSpaceCoordinate(44, 11, 10, 36));
        }};

        List<ParkingSpaceCoordinate> actStatus = new ArrayList<ParkingSpaceCoordinate>() {{
            add(new ParkingSpaceCoordinate(49, 13, 50, 30));
            add(new ParkingSpaceCoordinate(70, 16, 51, 30));
            add(new ParkingSpaceCoordinate(23, 7, 53, 20));
            add(new ParkingSpaceCoordinate(32, 18, 30, 10));
            add(new ParkingSpaceCoordinate(44, 11, 10, 36));
        }};

        List<ParkingSpaceCoordinate> res = difference(nearest, actStatus);
        Assert.assertEquals(1, res.size());
        System.out.println(res);
    }

    private List<ParkingSpaceCoordinate> difference(List<ParkingSpaceCoordinate> nearest, List<ParkingSpaceCoordinate> actStatus) {
        List<ParkingSpaceCoordinate> result = new ArrayList<>(actStatus);
        for (ParkingSpaceCoordinate actCo1 : actStatus) {
            for (ParkingSpaceCoordinate actCo2 : nearest) {
                if (equalInRange(actCo1.getX1(), actCo2.getX1(), actCo1.getY1(), actCo2.getY1(), 5)
                        && equalInRange(actCo1.getX2(), actCo2.getX2(), actCo1.getY2(), actCo2.getY2(), 5)) {
                    result.remove(actCo1);
                }
            }
        }

        return result;
    }

    private boolean equalInRange(int x1, int x2, int y1, int y2, int range) {
        return (x1 + range >= x2 && x1 - range <= x2) && (y1 + range >= y2 && y1 - range <= y2);
    }

    @Test
    public void test1() {
        double dist = distanceInKmBetweenEarthCoordinates(48.337882, 14.284223, 48.246459, 14.335504);
        System.out.println(dist);
    }

    private double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
        int earthRadiusKm = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }
}
