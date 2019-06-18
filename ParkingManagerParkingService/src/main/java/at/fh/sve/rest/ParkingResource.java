package at.fh.sve.rest;

import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/parking")
@Api("parkingManager")
@Traced
public class ParkingResource {

    @Inject
    private ParkingService parkingService;

    @Inject
    private Logger LOG;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "returns the coordinates for a specific city", response = Response.class,
            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Counted(unit = MetricUnits.NONE,
            name = "addParkingSpace_Counter",
            absolute = true,
            monotonic = true,
            displayName = "addParkingSpace_Counter",
            description = "Monitor how many times 'addParkingSpace' was called!")
    @Timed(name = "addParkingSpace_Timer",
            description = "Monitor the time 'addParkingSpace'-Method takes",
            unit = MetricUnits.MILLISECONDS,
            absolute = true)
    public Response addParkingPlace(ParkingPlace parkingPlace) {
        LOG.info(String.format("Add new parking place %s", parkingPlace));
        ParkingPlace persisted = parkingService.addParkingPlace(parkingPlace);
        LOG.debug(String.format("Added new parking place with id = %s", persisted.getId()));
        return Response.ok(persisted).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "returns all available parking spaces", response = Response.class, produces = MediaType.APPLICATION_JSON)
    @Counted(unit = MetricUnits.NONE,
            name = "getAllParkingSpaces_Counter",
            absolute = true,
            monotonic = true,
            displayName = "getAllParkingSpaces_Counter",
            description = "Monitor how many times 'getAllParkingSpaces' was called!")
    @Timed(name = "getAllParkingSpaces_Timer",
            description = "Monitor the time 'getAllParkingSpaces'-Method takes",
            unit = MetricUnits.MILLISECONDS,
            absolute = true)
    public Response getAllParkingPlaces() {
        return Response.ok(parkingService.getAllParkingPlaces()).build();
    }

}
