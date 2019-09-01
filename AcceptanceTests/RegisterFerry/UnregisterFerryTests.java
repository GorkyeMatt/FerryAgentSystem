package RegisterFerry;

import helpers.CommonPreparationForTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnregisterFerryTests extends CommonPreparationForTests {
    @Test
    void UnregisterFerryWhenAlreadyRegistered() throws InterruptedException {

        //arrange
        var portAgent = CreatePortAgent("port");
        var port = portAgent.getMyPort();

        var ferryAgent = CreateFerryAgent("ferry1");
        ferryAgent.getFerry().setMyPort(port);

        portAgent.addFerry(ferryAgent.getFerry());


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
