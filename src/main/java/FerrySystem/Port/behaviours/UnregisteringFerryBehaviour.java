package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UnregisteringFerryBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;
    private Logger logger;

    public UnregisteringFerryBehaviour(PortAgent myPortAgent) {
        super(myPortAgent);
        this.myPortAgent = myPortAgent;
        logger = myPortAgent.getLogger();
    }

    private MessageTemplate template =  MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        logger.log("received:\n:" + received);

        var content = received.getContent();
        var id = Integer.parseInt(content);

        myPortAgent.removeFerry(id);

        var message = received.createReply();
        message.setPerformative(ACLMessage.INFORM);

        logger.log("sending:\n:" + message);
        myAgent.send(message);
    }
}
