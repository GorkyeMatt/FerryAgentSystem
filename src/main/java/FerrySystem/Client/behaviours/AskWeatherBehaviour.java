package FerrySystem.Client.behaviours;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.WeatherInfo;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

/**
 * Client asks about weather
 */
public class AskWeatherBehaviour extends AskAndWaitBehaviour
{
    private CarAgent myCarAgent;
    private Ferry myFerry;
    private ObjectMapper mapper;

    public AskWeatherBehaviour(CarAgent agent, Ferry ferry)
    {
        super(agent);
        this.myCarAgent = agent;
        this.myFerry = ferry;

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void prepareMessage()
    {
        message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER);
        message.addReceiver(myFerry.getAgentAID());
    }

    @Override
    public void onMessageSending()
    {
        myBasicAgent.getLogger().log("Asking about weather");
    }

    @Override
    public void prepareMessageTemplate()
    {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM ));
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        myBasicAgent.getLogger().log("Received answer for ask weather");
        try
        {
            var json = received.getContent();
            WeatherInfo weather = mapper.readValue(json, WeatherInfo.class);

            myCarAgent.getMyCar().setWeather(weather);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
