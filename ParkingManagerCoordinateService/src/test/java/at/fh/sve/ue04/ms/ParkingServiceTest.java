package at.fh.sve.ue04.ms;

import at.fh.sve.domain.Coordinates;
import at.fh.sve.logic.ParkingService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@Ignore
@RunWith(Arquillian.class)
public class ParkingServiceTest {

    @Inject
    private ParkingService parkingService;


    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "at.fh.sve")
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
