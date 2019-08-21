package FerrySystem.Port.behaviours.weather;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import FerrySystem.Commons.helpers.JsonSerializer;

public class InformWeatherBehaviour extends CyclicMessageReceiveBehaviour
{
    private Logger  logger;
    private PortAgent myPortAgent;
    private JsonSerializer jsonSerializer = new JsonSerializer();

    public InformWeatherBehaviour(PortAgent agent)
    {
        super(agent);
        myPortAgent = agent;
        logger = agent.getLogger();
    }

    private MessageTemplate template = MessageTemplate.and(
        MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT),
        MessageTemplate.MatchPerformative(ACLMessage.INFORM ));

    @Override
    public MessageTemplate messageTemplate()
    {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        logger.log("received:\n:" + received);

        // todo process message;
        var s = received.getContent();

        var response = received.createReply();

        var serialized = jsonSerializer.serialize(myPortAgent.getMyPort().getWeather());

        response.setContent(serialized);

        logger.log("sending:\n:" + response);
        myAgent.send(response);
    }


}
