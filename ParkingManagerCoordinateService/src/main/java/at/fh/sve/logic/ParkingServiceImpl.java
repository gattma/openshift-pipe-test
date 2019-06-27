package at.fh.sve.logic;

import at.fh.sve.dao.ParkingDAO;
import at.fh.sve.domain.AnalyzedParkingPlace;
import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.domain.Coordinates;
import at.fh.sve.domain.ParkingSpaceCoordinate;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ParkingServiceImpl implements ParkingService {

    @Inject
    private Logger LOG;

    @Inject
    private Instance<ParkingDAO> parkingDAO;

    @Override
    public AnalyzedParkingPlace getBestParkingPlaceFor(String city, Double longitude, Double latitude) {
        LOG.info("AnalyzedParkingPlace getBestParkingPlaceFor(String city, Double longitude, Double latitude)");
        List<ParkingPlace> parkingPlaces = parkingDAO.get().findAllFor(city);
        parkingPlaces.forEach(pp -> LOG.info(pp.toString()));

        boolean finished = false;
        AnalyzedParkingPlace result = null;
        while(!finished) {
            LOG.info("ParkingPlace nearest = findNearest(parkingPlaces, longitude, latitude);");
            ParkingPlace nearest = findNearest(parkingPlaces, longitude, latitude);
            parkingPlaces.remove(nearest);

            LOG.info("result = getActualStatus(nearest.getWebcamUrl());");
            result = getActualStatus(nearest.getWebcamUrl());

            List<ParkingSpaceCoordinate> freePlaces = difference(nearest.getParkingSpaces(), result.getParkingSpaces());
            if(!freePlaces.isEmpty() || parkingPlaces.isEmpty()) {
                finished = true;
            } else {
                parkingPlaces.remove(nearest);
            }
        }

        return result;
    }

    @ConfigProperty(name = "ANALYZER_BASE_URL", defaultValue = "http://mdb.parking-manager.svc/analyze")
    private String ANALYZER_BASE_URL;

    private AnalyzedParkingPlace getActualStatus(String webcamUrl) {
        return ClientBuilder.newClient()
                .target(ANALYZER_BASE_URL)
                .queryParam("url", webcamUrl)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(AnalyzedParkingPlace.class);
    }

    private ParkingPlace findNearest(List<ParkingPlace> parkingPlaces, Double longitude, Double latitude) {
        ParkingPlace nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for(ParkingPlace pp : parkingPlaces) {
            double actualDistance = distanceInKmBetweenEarthCoordinates(
                    latitude,
                    longitude,
                    pp.getLocation().getLatitude(),
                    pp.getLocation().getLongitude()
            );

            if(actualDistance < nearestDistance) {
                nearestDistance = actualDistance;
                nearest = pp;
            }
        }

        return nearest;
    }

    private double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
        int earthRadiusKm = 6371;

        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadiusKm * c;
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

}
