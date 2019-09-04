package FerrySystem.Client.behaviours;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.WeatherInfo;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;

import jade.lang.acl.ACLMessage;

import java.io.IOException;

/**
 * Client asks about weather
 */
public class AskWeatherBehaviour extends AskAndWaitBehaviour {
    private CarAgent myCarAgent;
    private Ferry myFerry;

    public AskWeatherBehaviour(CarAgent agent, Ferry ferry) {
        super(agent);
        this.myCarAgent = agent;
        this.myFerry = ferry;
    }

    @Override
    public ACLMessage prepareMessage() {
        var message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER);
        message.addReceiver(myFerry.getAgentAID());
        return message;
    }

    @Override
    public void onMessageSending() {
        myBasicAgent.getLogger().log("Asking about weather");
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        myBasicAgent.getLogger().log("Received answer for ask weather");
        try {
            var json = received.getContent();
            WeatherInfo weather = mapper.readValue(json, WeatherInfo.class);

            myCarAgent.getMyCar().setWeather(weather);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
