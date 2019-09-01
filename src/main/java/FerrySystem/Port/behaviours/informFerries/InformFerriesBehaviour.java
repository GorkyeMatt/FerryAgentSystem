package FerrySystem.Port.behaviours.informFerries;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.JsonSerializer;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformFerriesBehaviour extends CyclicMessageReceiveBehaviour
{
    private Logger logger;
    private JsonSerializer jsonSerializer = new JsonSerializer();
    private PortAgent myPortAgent;

    public InformFerriesBehaviour(PortAgent agent){
        super(agent);
        myPortAgent = agent;
        logger = agent.getLogger();
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        logger.logReceived(received);

        var response = received.createReply();
        var serialized = jsonSerializer.serialize(myPortAgent.getMyPort().getRegisteredFerries());

        logger.logSend(received);
    }

    private MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT),
            MessageTemplate.MatchPerformative(ACLMessage.INFORM ));

    @Override
    public MessageTemplate messageTemplate()
    {
        return template;
    }
}
