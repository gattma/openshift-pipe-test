package at.fh.sve.config;

import at.fh.sve.provider.CorsResponseFilter;
import at.fh.sve.rest.ParkingResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(CorsResponseFilter.class);
        resources.add(ParkingResource.class);
        //need to be added. else swagger.json will not be present
        //https://issues.jboss.org/browse/THORN-1667
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return resources;
    }

}
