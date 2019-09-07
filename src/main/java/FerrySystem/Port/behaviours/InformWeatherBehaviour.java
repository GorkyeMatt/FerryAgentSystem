package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformWeatherBehaviour extends CyclicMessageReceiveBehaviour
{
    private PortAgent myPortAgent;

    public InformWeatherBehaviour(PortAgent agent)
    {
        super(agent);
        myPortAgent = agent;
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM ));
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        logger.logReceived(received);

        var response = received.createReply();

        var serialized = jsonSerializer.serialize(myPortAgent.getMyPort().getWeather());

        response.setContent(serialized);

        logger.logSend(response);
        myAgent.send(response);
    }




}
