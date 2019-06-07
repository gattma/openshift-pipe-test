package at.fh.sve.ue04.ms.rest;

import at.fh.sve.ue04.ms.domain.ParkingSpace;
import at.fh.sve.ue04.ms.service.ParkingService;
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
import java.util.NoSuchElementException;

@Path("/parking")
@Api("parkingManager")
@Traced
public class ParkingResource {

    @Inject
    private ParkingService parkingService;

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
    public Response getAllParkingSpaces() {
        return Response.ok(parkingService.getAllParkingSpaces()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "returns a specific parking space", response = Response.class, produces = MediaType.APPLICATION_JSON)
    @Counted(unit = MetricUnits.NONE,
            name = "getParkingSpace_Counter",
            absolute = true,
            monotonic = true,
            displayName = "getParkingSpace_Counter",
            description = "Monitor how many times 'getParkingSpace' was called!")
    @Timed(name = "getParkingSpace_Timer",
            description = "Monitor the time 'getParkingSpace'-Method takes",
            unit = MetricUnits.MILLISECONDS,
            absolute = true)
    public Response getParkingSpace(@PathParam("id") Long id) {
        try {
            return Response.ok(parkingService.getParkingSpace(id)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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
    public Response addParkingSpace(ParkingSpace ps) {
        return Response.ok(parkingService.addParkingSpace(ps)).build();
    }

}
