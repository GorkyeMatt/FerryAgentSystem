package AskFerries;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.Port;
import FerrySystem.Commons.WeatherInfo;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;
import jade.core.AID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AskFerriesTests
{
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
    void AskFerries() throws InterruptedException{

        //arrange

        var car = new Car();
        car.setAgentAID(new AID("car", AID.ISLOCALNAME));

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car", carAgent);

        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));
        var weather = new WeatherInfo();
        port.setWeather(weather);

        var portAgent = new PortAgent(port);
        jadeStarter.startAgent("port", portAgent);

        var ferry = new Ferry();
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);

        var ferry2 = new Ferry();
        var ferryAgent2 = new FerryAgent(ferry2);
        jadeStarter.startAgent("ferry2", ferryAgent2);

        //register ferries in port

        ferryAgent.registerInPort(port);
        ferryAgent2.registerInPort(port);

        Thread.sleep(1000);

        //act

        //assert

        var ferries = port.getRegisteredFerries();
    }

    @Test
    void OneBehaviourTest() throws InterruptedException{

        var car = new Car();
        car.setAgentAID(new AID("car", AID.ISLOCALNAME));

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent("car", carAgent);

        carAgent.askFerries();
        Thread.sleep(1000);
    }
}
