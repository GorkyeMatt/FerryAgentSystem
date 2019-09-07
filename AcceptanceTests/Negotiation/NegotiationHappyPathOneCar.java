package Negotiation;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.*;
import FerrySystem.Ferry.FerryAgent;
import helpers.CommonPreparationForTests;
import helpers.jadeStarter;
import jade.core.AID;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NegotiationHappyPathOneCar extends CommonPreparationForTests {
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
    void OneCarAskForPlaceProperTime() throws InterruptedException {

        //arrange

        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = LocalDateTime.parse("2019-09-12T11:30:00");



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car = new Car();
        car.setDepartureInfo(departure);
        car.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car1", carAgent);




        //act
        carAgent.askForPlace();
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car.getActuallyRegister();
        assertNotNull(registerDeparture);

        var registeredCars = ferry.getRegisteredCars();
        assertEquals(1,registeredCars.size());


    }

    @Test
    void OneCarAskForPlaceWrongTime() throws InterruptedException {

        //arrange

        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = LocalDateTime.parse("2019-09-12T11:30:00");



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car = new Car();
        car.setDepartureInfo(departure);
        car.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:00:00"));

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car1", carAgent);




        //act
        carAgent.askForPlace();
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car.getActuallyRegister();
        assertNull(registerDeparture);

        var registeredCars = ferry.getRegisteredCars();
        assertEquals(0,registeredCars.size());


    }

}