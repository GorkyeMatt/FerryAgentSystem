package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RegisterDepartureBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;

    public RegisterDepartureBehaviour(PortAgent agent) {
        super(agent);
        myPortAgent = agent;
    }

    private MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_DEPARTURE_REGISTER),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage message) {
        //todo
    }
}
