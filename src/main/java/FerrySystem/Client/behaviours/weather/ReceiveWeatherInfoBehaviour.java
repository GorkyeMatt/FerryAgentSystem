package FerrySystem.Client.behaviours.weather;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.WeatherInfo;
import FerrySystem.Commons.helpers.behaviours.OneMessageReceiveBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ReceiveWeatherInfoBehaviour extends OneMessageReceiveBehaviour
{
    private CarAgent myCarAgent;
    private ObjectMapper mapper;

    public ReceiveWeatherInfoBehaviour(CarAgent agent)
    {
        super(agent);
        this.myCarAgent = agent;

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private MessageTemplate template =  MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER),
            MessageTemplate.MatchPerformative(ACLMessage.INFORM ));

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        myCarAgent.getLogger().log("received:\n:" + received);

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

    @Override
    public MessageTemplate messageTemplate()
    {
        return template;
    }
}
