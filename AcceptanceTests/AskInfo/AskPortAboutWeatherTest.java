package AskInfo;

import FerrySystem.Commons.WeatherInfo;
import helpers.CommonPreparationForTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AskPortAboutWeatherTest extends CommonPreparationForTests
{
    @Test
    void AskWeather() throws InterruptedException{

        //arrange

        var carAgent = CreateCarAgent("car");

        var portAgent = CreatePortAgent("port");
        var weather = new WeatherInfo();
        portAgent.getMyPort().setWeather(weather);

        var ferryAgent = CreateFerryAgent("ferry");

        //act
        ferryAgent.registerInPort(portAgent.getMyPort());
        Thread.sleep(1000);

        carAgent.askAboutWeather(ferryAgent.getFerry());
        Thread.sleep(1000); //give agent time to finish job

        //assert
        assertNotNull(carAgent.getMyCar().getWeather());

        assertEquals(portAgent.getMyPort().getWeather(), carAgent.getMyCar().getWeather());

    }

}
