package RegisterFerry;

import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.Port;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;

import jade.core.AID;
import org.junit.jupiter.api.*;

import java.util.Vector;

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


        //assert
        var ferriesInPort = port.getRegisteredFerries().size();
        assertEquals(1, ferriesInPort);

        var ferryRegisteredInPort = ferryAgent.getFerry().getMyPort();
        assertNotNull(ferryRegisteredInPort);
    }

    @Test
    void RegisterMultipleFerries() throws InterruptedException {
        int numberOfFerries = 5;

        //arrange
        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));

        var portAgent = new PortAgent(port);
        jadeStarter.startAgent("port", portAgent);

        var ferries = new Vector<FerryAgent>(numberOfFerries);
        for (int i = 0; i< numberOfFerries; i++){
            var ferry = new Ferry();
            var ferryAgent = new FerryAgent(ferry);
            ferries.add(ferryAgent);
            jadeStarter.startAgent("ferry" + i, ferryAgent);
        }


        //act
        for (FerryAgent agent: ferries) {
            agent.registerInPort(port);
        }

        Thread.sleep(1000); //give agent time to finish job


        //assert
        var ferriesInPort = port.getRegisteredFerries().size();
        assertEquals(numberOfFerries, ferriesInPort);

        for (FerryAgent agent: ferries) {
            assertNotNull(agent.getFerry().getMyPort());
        }
    }
}
