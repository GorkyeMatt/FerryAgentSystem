package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformScheduleBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;

    public InformScheduleBehaviour(PortAgent a) {
        super(a);
        this.myPortAgent = a;
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_SCHEDULE),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
        );
    }

    @Override
    public void onMessageReceived(ACLMessage message) {
        logger.logReceived(message);
        var response = message.createReply();
        response.setPerformative(ACLMessage.INFORM);

        var departures = myPortAgent.getMyPort().getRegisteredDepartures();
        var serialized = jsonSerializer.serialize(departures);
        response.setContent(serialized);

        myPortAgent.send(response);
        logger.logSend(response);
    }


}
