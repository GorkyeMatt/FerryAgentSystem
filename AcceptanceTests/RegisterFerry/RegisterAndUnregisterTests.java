package RegisterFerry;

import FerrySystem.Commons.data.Ferry;
import FerrySystem.Commons.data.Port;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;
import jade.core.AID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterAndUnregisterTests {

    private helpers.jadeStarter jadeStarter;

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

        //arrange
        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));

        var portAgent = new PortAgent(port);
        jadeStarter.startAgent("port", portAgent);

        var ferry = new Ferry();
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);

        //act
        ferryAgent.registerInPort(port);
        Thread.sleep(1000); //give agent time to finish job

        var ferriesInPort = port.getRegisteredFerries().size();
        var ferryRegisteredInPort = ferryAgent.getFerry().getMyPort();



        ferryAgent.unregisterFromCurrentPort();
        Thread.sleep(1000); //give agent time to finish job

        var ferriesInPortAtEnd = port.getRegisteredFerries().size();
        var ferryRegisteredInPortAtEnd = ferryAgent.getFerry().getMyPort();

        //assert

        assertEquals(1, ferriesInPort);
        assertNotNull(ferryRegisteredInPort);

        assertEquals(0, ferriesInPortAtEnd);
        assertNull(ferryRegisteredInPortAtEnd);

    }
}
