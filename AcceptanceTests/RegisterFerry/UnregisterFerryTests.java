package RegisterFerry;

import FerrySystem.Commons.*;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;
import jade.core.AID;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UnregisterFerryTests {
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
    void UnregisterFerryWhenAlreadyRegistered() throws InterruptedException {

        //arrange
        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));

        var portAgent = new PortAgent(port);
        jadeStarter.startAgent("port", portAgent);

        var ferry = new Ferry();
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);
        ferryAgent.getFerry().setMyPort(port);

        portAgent.addFerry(ferry);


        //act
        ferryAgent.unregisterFromCurrentPort();

        Thread.sleep(1000); //give agent time to finish job


        //assert
        var ferriesInPort = port.getRegisteredFerries().size();
        assertEquals(0, ferriesInPort);

        var ferryRegisteredInPort = ferryAgent.getFerry().getMyPort();
        assertNull(ferryRegisteredInPort);
    }
}
