package at.fh.sve.rest;

import at.fh.sve.domain.Address;
import at.fh.sve.domain.Coordinate;
import at.fh.sve.domain.ParkingSpace;
import at.fh.sve.service.ParkingService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;
import java.util.NoSuchElementException;

//@RunWith(Arquillian.class)
public class ParkingServiceTest {
/* TODO
    @Inject
    private ParkingService parkingService;

    @Test
    public void test_getAllParkingSpaces() {
        List<ParkingSpace> parkingSpaces = parkingService.getAllParkingSpaces();

        Assert.assertNotNull(parkingSpaces);
        Assert.assertEquals(2L, parkingSpaces.size());
        Assert.assertEquals(1L, parkingSpaces.get(0).getId().longValue());
        Assert.assertEquals(2L, parkingSpaces.get(1).getId().longValue());
    }

    @Test
    public void test_findParkingSpace_ok() {
        ParkingSpace ps = parkingService.getParkingSpace(1L);

        Assert.assertNotNull(ps);
        Assert.assertEquals(1, ps.getId().intValue());
        Assert.assertEquals("Linz", ps.getAddress().getCity());
        Assert.assertEquals("Erich-Fried-Weg", ps.getAddress().getStreet());
        Assert.assertEquals(4030, ps.getAddress().getPostalCode().intValue());
        Assert.assertEquals(123, ps.getLocation().getLongitude().intValue());
        Assert.assertEquals(432, ps.getLocation().getLatitude().intValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void test_findParkingSpace_invalidId() {
        parkingService.getParkingSpace(10000L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_findParkingSpace_nullParam() {
        parkingService.getParkingSpace(null);
    }

    @Test
    public void test_addParkingSpace_ok() {
        ParkingSpace original = parkingService.addParkingSpace(new ParkingSpace(
                0L, // wird vom service gesetzt
                new Address(3333L, "TEST", "TEST STREET"),
                new Coordinate(9876L, 3456L)
        ));

        Assert.assertNotEquals(0, original.getId().intValue());
        Assert.assertEquals("TEST", original.getAddress().getCity());
        Assert.assertEquals("TEST STREET", original.getAddress().getStreet());
        Assert.assertEquals(3333, original.getAddress().getPostalCode().intValue());
        Assert.assertEquals(9876, original.getLocation().getLongitude().intValue());
        Assert.assertEquals(3456, original.getLocation().getLatitude().intValue());

        ParkingSpace persisted = parkingService.getParkingSpace(original.getId());
        Assert.assertNotNull(persisted);
        Assert.assertEquals(persisted.getAddress().getCity(), original.getAddress().getCity());
        Assert.assertEquals(persisted.getAddress().getStreet(), original.getAddress().getStreet());
        Assert.assertEquals(
                persisted.getAddress().getPostalCode().intValue(),
                original.getAddress().getPostalCode().intValue()
        );
        Assert.assertEquals(
                persisted.getLocation().getLongitude().intValue(),
                original.getLocation().getLongitude().intValue()
        );
        Assert.assertEquals(
                original.getLocation().getLatitude().intValue(),
                original.getLocation().getLatitude().intValue()
        );
    }

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "at.fh.sve.ue04.ms")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

 */
}
