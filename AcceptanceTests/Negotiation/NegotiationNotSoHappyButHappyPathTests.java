package Negotiation;

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

class NegotiationNotSoHappyButHappyPathTests {
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
    void NewCarDifferentTimeRegisteredCarsSameTime() throws InterruptedException {

        //arrange
        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = LocalDateTime.parse("2019-09-12T11:30:00");



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);

        Vector<RegisteredCar> registeredCars = new Vector<>();
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car2", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car3", AID.ISLOCALNAME)));

        ferry.setRegisteredCars(registeredCars);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car = new Car();
        car.setDepartureInfo(departure);
        car.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:20:00")); //10 min earlier that others

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car1", carAgent);

        //

        var car2 = new Car();
        car2.setActuallyRegister(departure);
        car2.setDepartureInfo(departure);
        car2.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:30:00"));
        car2.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent2 = new CarAgent(car2);
        jadeStarter.startAgent("car2", carAgent2);
        carAgent2.addBehaviour(new ListenNegotiationBehaviour(carAgent2));

        //

        var car3 = new Car();
        car3.setActuallyRegister(departure);
        car3.setDepartureInfo(departure);
        car3.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:30:00"));
        car3.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));


        var carAgent3 = new CarAgent(car3);
        jadeStarter.startAgent("car3", carAgent3);
        carAgent3.addBehaviour(new ListenNegotiationBehaviour(carAgent3));


        //act
        carAgent.askForPlace();
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car.getActuallyRegister();
        assertNull(registerDeparture);

        var registeredCarsCount = ferry.getRegisteredCars().size();
        assertEquals(2,registeredCarsCount);



        //act
        carAgent.askForPlace(carAgent.getMyCar().getDepartureTime());
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture2 = car.getActuallyRegister();
        assertNotNull(registerDeparture2);

        var registeredCarsCount2 = ferry.getRegisteredCars().size();
        assertEquals(3,registeredCarsCount2);

    }

    @Test
    void NewCarDifferentTimeRegisteredCarsDifferentTime() throws InterruptedException {

        //arrange
        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = LocalDateTime.parse("2019-09-12T11:30:00");



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);

        Vector<RegisteredCar> registeredCars = new Vector<>();
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car2", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car3", AID.ISLOCALNAME)));

        ferry.setRegisteredCars(registeredCars);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car = new Car();
        car.setDepartureInfo(departure);
        car.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:20:00")); //10 min earlier that others

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car1", carAgent);

        //

        var car2 = new Car();
        car2.setActuallyRegister(departure);
        car2.setDepartureInfo(departure);
        car2.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:25:00"));
        car2.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent2 = new CarAgent(car2);
        jadeStarter.startAgent("car2", carAgent2);
        carAgent2.addBehaviour(new ListenNegotiationBehaviour(carAgent2));

        //

        var car3 = new Car();
        car3.setActuallyRegister(departure);
        car3.setDepartureInfo(departure);
        car3.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:30:00"));
        car3.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));


        var carAgent3 = new CarAgent(car3);
        jadeStarter.startAgent("car3", carAgent3);
        carAgent3.addBehaviour(new ListenNegotiationBehaviour(carAgent3));


        //act
        carAgent.askForPlace();
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car.getActuallyRegister();
        assertNull(registerDeparture);

        var registeredCarsCount = ferry.getRegisteredCars().size();
        assertEquals(2,registeredCarsCount);



        //act
        carAgent.askForPlace(carAgent.getMyCar().getDepartureTime());
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture2 = car.getActuallyRegister();
        assertNotNull(registerDeparture2);

        var registeredCarsCount2 = ferry.getRegisteredCars().size();
        assertEquals(3,registeredCarsCount2);

    }


    @Test
    void NewCarDifferentTimeRegisteredCarsDifferentTimeNewCArBetween() throws InterruptedException {

        //arrange
        var departure = new DepartureInfo();
        departure.Id = 1;
        departure.ferryAID = new AID("ferry1", AID.ISLOCALNAME);
        departure.from = "Messina";
        departure.to = "Villa San Giovani";
        departure.time = LocalDateTime.parse("2019-09-12T11:30:00");



        var ferry = new Ferry();
        ferry.getDepartureInfos().put(departure.Id, departure);

        Vector<RegisteredCar> registeredCars = new Vector<>();
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car2", AID.ISLOCALNAME)));
        registeredCars.add(new RegisteredCar(departure.Id, new AID("car3", AID.ISLOCALNAME)));

        ferry.setRegisteredCars(registeredCars);
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);


        var car = new Car();
        car.setDepartureInfo(departure);
        car.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:25:00")); //10 min earlier that others

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car1", carAgent);

        //

        var car2 = new Car();
        car2.setActuallyRegister(departure);
        car2.setDepartureInfo(departure);
        car2.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:20:00"));
        car2.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));

        var carAgent2 = new CarAgent(car2);
        jadeStarter.startAgent("car2", carAgent2);
        carAgent2.addBehaviour(new ListenNegotiationBehaviour(carAgent2));

        //

        var car3 = new Car();
        car3.setActuallyRegister(departure);
        car3.setDepartureInfo(departure);
        car3.setEstimatedArrivalTime(LocalDateTime.parse("2019-09-12T11:30:00"));
        car3.setDepartureTime(LocalDateTime.parse("2019-09-12T11:30:00"));


        var carAgent3 = new CarAgent(car3);
        jadeStarter.startAgent("car3", carAgent3);
        carAgent3.addBehaviour(new ListenNegotiationBehaviour(carAgent3));


        //act
        carAgent.askForPlace();
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture = car.getActuallyRegister();
        assertNull(registerDeparture);

        var registeredCarsCount = ferry.getRegisteredCars().size();
        assertEquals(2,registeredCarsCount);



        //act
        carAgent.askForPlace(carAgent.getMyCar().getDepartureTime());
        Thread.sleep(1000); //give agent time to finish job


        //assert

        var registerDeparture2 = car.getActuallyRegister();
        assertNotNull(registerDeparture2);

        var registeredCarsCount2 = ferry.getRegisteredCars().size();
        assertEquals(3,registeredCarsCount2);

    }
}
