package RegisterFerry;

import helpers.CommonPreparationForTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterAndUnregisterTests extends CommonPreparationForTests {
    @Test
    void RegisterFerry() throws InterruptedException {

        //arrange
        var portAgent = CreatePortAgent("port");
        var port = portAgent.getMyPort();

        var ferryAgent = CreateFerryAgent("ferry1");


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

