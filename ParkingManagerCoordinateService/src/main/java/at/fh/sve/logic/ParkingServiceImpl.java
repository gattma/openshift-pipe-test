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
}
