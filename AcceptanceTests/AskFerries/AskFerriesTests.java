package AskFerries;

import FerrySystem.Commons.WeatherInfo;
import FerrySystem.Ferry.FerryAgent;
import helpers.CommonPreparationForTests;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AskFerriesTests extends CommonPreparationForTests{
    @Test
    void AskFerries() throws InterruptedException{

        //arrange

        var carAgent = CreateCarAgent("car");

        var portAgent = CreatePortAgent("port");
        var port = portAgent.getMyPort();
        var weather = new WeatherInfo();
        port.setWeather(weather);

        var ferryAgent = CreateFerryAgent("ferry");

        var ferryAgent2 = CreateFerryAgent("ferry2");

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
        var carAgent = CreateCarAgent("car");

        carAgent.askFerries();
        Thread.sleep(1000);
    }

    @Test
    void RegisterMultipleFerriesToPort() throws InterruptedException{
        var portAgent = CreatePortAgent("port");
        portAgent.getLogger().setDisplaySendReceived(false);
        portAgent.getLogger().setDisplayLog(false);

        var port = portAgent.getMyPort();

        List<FerryAgent> ferryAgents = new ArrayList<>();

        int count = 5;
        for(int i = 0; i < count; i++){
            var ferryAgent = CreateFerryAgent("ferry" + i);
            ferryAgent.getLogger().setDisplaySendReceived(false);

            ferryAgent.registerInPort(port);
            ferryAgents.add(ferryAgent);
        }

        Thread.sleep(1000); //give agent time to finish job

        var ferriesInPort = port.getRegisteredFerries().size();

        assertEquals(count, ferryAgents.size());
        assertEquals(count, ferriesInPort);
    }
}
