package NegotiationCornerCases;

import FerrySystem.Client.CarAgent;
import FerrySystem.Client.behaviours.negotiations.ListenNegotiationBehaviour;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.DepartureInfo;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.RegisteredCar;
import FerrySystem.Ferry.FerryAgent;
import helpers.jadeStarter;
import jade.core.AID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AwaitNegotiationTests {
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
    void AllCarsRegisterLastsAwaits() throws InterruptedException {

        //arrange
        var departureTime = LocalDateTime.parse("2019-09-12T11:30:00");

        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = departureTime;



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);

        Vector<RegisteredCar> registeredCars = new Vector<>();
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car1", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car2", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car3", AID.ISLOCALNAME)));

        ferry.setRegisteredCars(registeredCars);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car1 = new Car();
        car1.setActuallyRegister(departure);
        car1.setDepartureInfo(departure);
        car1.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:35:00"));
        car1.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent1 = new CarAgent(car1);
        jadeStarter.startAgent("car1", carAgent1);
        carAgent1.addBehaviour(new ListenNegotiationBehaviour(carAgent1));


        var car2 = new Car();
        car2.setActuallyRegister(departure);
        car2.setDepartureInfo(departure);
        car2.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:25:00"));
        car2.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent2 = new CarAgent(car2);
        jadeStarter.startAgent("car2", carAgent2);
        carAgent2.addBehaviour(new ListenNegotiationBehaviour(carAgent2));


        var car3 = new Car();
        car3.setActuallyRegister(departure);
        car3.setDepartureInfo(departure);
        car3.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:25:00"));
        car3.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));


        var carAgent3 = new CarAgent(car3);
        jadeStarter.startAgent("car3", carAgent3);
        carAgent3.addBehaviour(new ListenNegotiationBehaviour(carAgent3));


        //act
        carAgent1.askAwait(LocalDateTime.parse("2019-09-12T11:35:00"));
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car1.getActuallyRegister();
        assertNotNull(registerDeparture);

        var registeredCarsCount = ferry.getRegisteredCars().size();
        assertEquals(3, registeredCarsCount);

        var time = carAgent1.getMyCar().getDepartureTime();
        assertNotNull(time);
        assertEquals(11, time.getHour());
        assertEquals(35, time.getMinute());

    }

    @Test
    void AllCarsRegisterFirstAwaitsLastChangesEstimateTimeToo() throws InterruptedException {

        //arrange
        var departureTime = LocalDateTime.parse("2019-09-12T11:30:00");

        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = departureTime;



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);

        Vector<RegisteredCar> registeredCars = new Vector<>();
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car1", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car2", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car3", AID.ISLOCALNAME)));

        ferry.setRegisteredCars(registeredCars);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car1 = new Car();
        car1.setActuallyRegister(departure);
        car1.setDepartureInfo(departure);
        car1.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:20:00"));
        car1.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent1 = new CarAgent(car1);
        jadeStarter.startAgent("car1", carAgent1);
        carAgent1.addBehaviour(new ListenNegotiationBehaviour(carAgent1));


        var car2 = new Car();
        car2.setActuallyRegister(departure);
        car2.setDepartureInfo(departure);
        car2.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:25:00"));
        car2.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent2 = new CarAgent(car2);
        jadeStarter.startAgent("car2", carAgent2);
        carAgent2.addBehaviour(new ListenNegotiationBehaviour(carAgent2));


        var car3 = new Car();
        car3.setActuallyRegister(departure);
        car3.setDepartureInfo(departure);
        car3.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:29:00")); //changed from 11:30
        car3.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));


        var carAgent3 = new CarAgent(car3);
        jadeStarter.startAgent("car3", carAgent3);
        carAgent3.addBehaviour(new ListenNegotiationBehaviour(carAgent3));


        //act
        carAgent1.askAwait(LocalDateTime.parse("2019-09-12T11:25:00")); //will be late by 5 mins
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car1.getActuallyRegister();
        assertNotNull(registerDeparture);

        var registeredCarsCount = ferry.getRegisteredCars().size();
        assertEquals(3, registeredCarsCount);

        var time = carAgent1.getMyCar().getDepartureTime();
        assertNotNull(time);
        assertEquals(11, time.getHour());
        assertEquals(29, time.getMinute());

    }
}
