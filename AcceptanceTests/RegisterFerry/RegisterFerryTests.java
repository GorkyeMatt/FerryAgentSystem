package RegisterFerry;

import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterFerryTests {

    private jadeStarter jadeStarter;

    @BeforeEach
    void setUp(){
        jadeStarter = new jadeStarter();
    }

    @AfterEach
    void tearDown(){
        jadeStarter.close();
    }


    @Test
    void RegisterFerry() throws InterruptedException {

        var portAgent = new PortAgent();
        jadeStarter.startAgent("port", portAgent);

        var ferryAgent = new FerryAgent();
        jadeStarter.startAgent("ferry1", ferryAgent);


        Thread.sleep(1000); //give agent time to finish job

        var ferriesInPort = portAgent.getFerriesCount();
        assertEquals(1, ferriesInPort);

        var ferryRegisteredInPort = ferryAgent.getFerry().getMyPort();
        assertNotNull(ferryRegisteredInPort);
    }
}
