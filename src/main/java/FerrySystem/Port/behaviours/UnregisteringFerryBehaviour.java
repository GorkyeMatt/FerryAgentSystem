package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UnregisteringFerryBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;

    public UnregisteringFerryBehaviour(PortAgent myPortAgent) {
        super(myPortAgent);
        this.myPortAgent = myPortAgent;
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        logger.logReceived(received);

        var content = received.getContent();
        var id = Integer.parseInt(content);

        myPortAgent.removeFerry(id);

        var message = received.createReply();
        message.setPerformative(ACLMessage.INFORM);

        logger.logSend(message);
        myAgent.send(message);
    }

}