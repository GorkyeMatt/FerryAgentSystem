package Weather;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.Port;
import FerrySystem.Commons.WeatherInfo;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import helpers.jadeStarter;
import jade.content.onto.basic.Equals;
import jade.core.AID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AskPortAboutWeatherTest
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
    void AskWeather() throws InterruptedException{

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
        portAgent.getLogger().setDisplayLog(false);
        jadeStarter.startAgent("port", portAgent);

        var ferry = new Ferry();
        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent("ferry1", ferryAgent);

        //act
        ferryAgent.registerInPort(port);
        Thread.sleep(1000);

        carAgent.askAboutWeather(ferry);
        Thread.sleep(2000); //give agent time to finish job

        var w1 = port.getWeather();
        var w2 = car.getWeather();
        var e1 = w2.equals(w1);
        //assert
        assertNotNull(carAgent.getMyCar().getWeather());

        assertEquals(port.getWeather(), carAgent.getMyCar().getWeather());

    }

}
