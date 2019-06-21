package at.fh.sve.ue4.ms.logic;

import at.fh.sve.ue4.ms.dao.ParkingDAO;
import at.fh.sve.ue4.ms.domain.Coordinates;
import at.fh.sve.ue4.ms.logging.JSONStringifier;
import com.mongodb.util.JSON;
import io.jaegertracing.utils.Http;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ParkingServiceImpl implements ParkingService {

    private static final String ENDPOINT = "ENDPOINT"; // TODO specify python endpoint
    private static final Logger LOG = LoggerFactory.getLogger(ParkingServiceImpl.class);

    @Inject
    private Instance<ParkingDAO> parkingDAO;

    @Override
    public void addCoordinates(Coordinates coordinates) {
        // FIXME not needed??
        // FRONTEND doesn't
        postCall("addCoordinates", coordinates, Coordinates.class);

        parkingDAO.get().create(coordinates);
    }

    @Override
    public List<Coordinates> readCoordinate(String city) {
        String params = "city=" + city;
        getCall("readCoordinates", params, Coordinates[].class);

        //TODO Compare with db

        return parkingDAO.get().findByCity(city);
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

    private <T> T postCall(String path, Object body, Class<T> resultClass){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(ENDPOINT + path);
        LOG.info("POST to PythonService: " + ENDPOINT + path + " with body " + JSONStringifier.stringify(body));
        Response response = target.request().post(Entity.json(body));
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
