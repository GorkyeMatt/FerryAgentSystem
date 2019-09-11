package RegisterDeparture;

import FerrySystem.Commons.DepartureInfo;
import FerrySystem.Commons.DepartureRegistrationData;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.Port;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;
import jade.core.AID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegisterDepartureTests {

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
    void RegisterDeparture() throws InterruptedException {

        //arrange

        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));


        var ferry = new Ferry();
        ferry.setMyPort(port);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var portAgent = new PortAgent(port);
        portAgent.addFerry(ferry);
        jadeStarter.startAgent("port", portAgent);


        var departureInfo = new DepartureRegistrationData();
        departureInfo.setFerryAID(new AID("ferry1", AID.ISLOCALNAME));
        departureInfo.setFrom("Messina");
        departureInfo.setTo("Villa San Giovani");
        departureInfo.setDate(LocalDateTime.parse("2019-09-12T11:30:00"));
        departureInfo.setFerryId(ferry.getId());

        //act
        ferryAgent.addDeparture(departureInfo);
        Thread.sleep(1000); //give agent time to finish job


        //assert
        var registeredDeparturesInFerry = ferry.getDepartureInfos().size();
        assertEquals(1, registeredDeparturesInFerry);

        var registeredDeparturesInPort = port.getRegisteredDepartures().size();
        assertEquals(1, registeredDeparturesInPort);
    }
}
