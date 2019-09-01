package RegisterFerry;

import FerrySystem.Commons.*;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.CommonPreparationForTests;

import jade.core.AID;
import org.junit.jupiter.api.*;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class RegisterFerryTests extends CommonPreparationForTests {

    @Test
    void RegisterFerry() throws InterruptedException {

        //arrange
        var portAgent = CreatePortAgent("port");
        var port = portAgent.getMyPort();

        var ferryAgent = CreateFerryAgent("ferry1");

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
            var ferryAgent = CreateFerryAgent("ferry" + i);
            ferries.add(ferryAgent);
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
