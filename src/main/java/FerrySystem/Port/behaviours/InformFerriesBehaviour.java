package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformFerriesBehaviour extends CyclicMessageReceiveBehaviour
{
    private PortAgent myPortAgent;

    public InformFerriesBehaviour(PortAgent agent){
        super(agent);
        myPortAgent = agent;
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_FERRIES),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM ));
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        logger.logReceived(received);

        var response = received.createReply();
        var serialized = jsonSerializer.serialize(myPortAgent.getMyPort().getRegisteredFerries());
        response.setContent(serialized);

        logger.logSend(received);
        myAgent.send(response);
    }
}
