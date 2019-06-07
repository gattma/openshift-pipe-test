package at.fh.sve.ue04.ms;

import at.fh.sve.ue4.ms.domain.Coordinates;
import at.fh.sve.ue4.ms.logic.ParkingService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class ParkingServiceTest {

    @Inject
    private ParkingService parkingService;

    @Test
    public void test_readCoordinate() {
        List<Coordinates> coordinates = parkingService.readCoordinate("Hagenberg");

        Assert.assertNotNull(coordinates);
        Assert.assertEquals("Hagenberg", coordinates.get(0).getCity());
    }

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "at.fh.sve.ue4.ms")
                .addAsLibraries(
                        Maven.resolver()
                                .loadPomFromFile("pom.xml")
                                .importCompileAndRuntimeDependencies()
                                .importTestDependencies()
                                .resolve()
                                .withTransitivity()
                                .asFile()
                )
                .addAsWebInfResource("WEB-INF/beans.xml");
    }
}
