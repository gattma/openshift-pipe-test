package at.fh.sve.rest;

import at.fh.sve.logic.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/parking")
@Api("parking")
@Traced
public class ParkingResource {

    @Inject
    private ParkingService parkingService;

    @GET
    @Path("health")
    public Response health() {
        return Response.ok("SVE").build();
    }

    @GET
    @Path("/parkingplace")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "returns the coordinates for a specific city", response = Response.class, produces = MediaType.APPLICATION_JSON)
    @Counted(unit = MetricUnits.NONE,
            name = "getCoordinateForCity_Counter",
            absolute = true,
            monotonic = true,
            displayName = "getCoordinateForCity_Counter",
            description = "Monitor how many times 'getCoordinateForCity' was called!")
    @Timed(name = "getCoordinateForCity_Timer",
            description = "Monitor the time 'getCoordinateForCity'-Method takes",
            unit = MetricUnits.MILLISECONDS,
            absolute = true)
    public Response getBestParkingPlaceFor(@QueryParam("city") String city, @QueryParam("longitude") Double longitude, @QueryParam("latitude") Double latitude) {
        return Response.ok(parkingService.getBestParkingPlaceFor(city, longitude, latitude)).build();
    }
}
