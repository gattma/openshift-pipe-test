package at.fh.sve.logic;

import at.fh.sve.dao.ParkingDAO;
import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.domain.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
public class ParkingServiceImpl implements ParkingService {

    private static final String ENDPOINT = "ENDPOINT"; // TODO specify python endpoint

    @Inject
    private Logger LOG;

    @Inject
    private Instance<ParkingDAO> parkingDAO;

    @Override
    public List<Coordinates> readCoordinate(String city) {
        String params = "city=" + city;
        getCall("readCoordinates", params, Coordinates[].class);

        //TODO Compare with db

        return parkingDAO.get().findByCity(city);
    }

    @Override
    public ParkingPlace getBestParkingPlaceFor(String city) {
        List<ParkingPlace> parkingPlaces = parkingDAO.get().findAllFor(city);
        parkingPlaces.forEach(pp -> LOG.info(pp.toString()));

        return parkingPlaces.get(0);
    }

    private <T> T getCall(String path, String parametersString, Class<T> resultClass){
        Client client = ClientBuilder.newClient();
        String params = "";
        if(parametersString != null && !parametersString.isEmpty()){
            params = "?" + parametersString;
        }
        WebTarget target = client.target(ENDPOINT + path + params);
        LOG.info("GET to PythonService: " + ENDPOINT + path + params );
        Response response = target.request().get();
        if(response.getStatus() != Response.Status.OK.getStatusCode()){

            client.close();
            return null; // TODO throw Exception or similar Error handling
        }
        return getEntity(response, client, resultClass);
    }


    private <T> T getEntity(Response response, Client client, Class<T> resultClass){
        if(response.hasEntity()){
            T entity = response.readEntity(resultClass);
            client.close();
            return entity;
        } else {
            client.close();
            return null;
        }
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
