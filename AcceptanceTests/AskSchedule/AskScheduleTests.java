package AskSchedule;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.DepartureInfo;
import FerrySystem.Commons.Port;
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

class AskScheduleTests {

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
    void AskSchedule() throws InterruptedException {

        //arrange

        var departure1 = new DepartureInfo();
        departure1.Id = 1;
        departure1.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure1.from = "Messina";
        departure1.to = "Villa San Giovani";
        departure1.time = LocalDateTime.parse("2019-09-12T11:30:00");

        var departure2 = new DepartureInfo();
        departure2.Id = 2;
        departure2.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure2.from = "Messina";
        departure2.to = "Villa San Giovani";
        departure2.time = LocalDateTime.parse("2019-09-12T14:30:00");

        var departure3 = new DepartureInfo();
        departure3.Id = 3;
        departure3.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure3.from = "Villa San Giovani";
        departure3.to = "Messina";
        departure3.time = LocalDateTime.parse("2019-09-12T11:30:00");

        var departures = new Vector<DepartureInfo>();
        departures.add(departure1);
        departures.add(departure2);
        departures.add(departure3);



        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));
        port.setRegisteredDepartures(departures);
        var portAgent = new PortAgent(port);
        jadeStarter.startAgent("port", portAgent);


        var car = new Car();
        var carAgent = new CarAgent(car);
        car.setPort(port);
        jadeStarter.startAgent("car1", carAgent);

        //act
        carAgent.askSchedule();

        Thread.sleep(1000); //give agent time to finish job


        //assert
        var knownDepartures = car.getKnownDepartures();
        assertNotNull(knownDepartures);
        assertEquals(3, knownDepartures.size());


    }
}
