package FerrySystem.Ferry.behaviours;

import FerrySystem.Commons.*;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendRegistrationMessageBehaviour extends OneShotBehaviour {
    private FerryAgent myFerryAgent;
    private Port port;

    public SendRegistrationMessageBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
    }

    @Override
    public void action() {
        var message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY);
        message.setContent(Defines.FERRY_SYSTEM_REGISTER);
        message.addReceiver(port.getAgentAID());
        myAgent.send(message);
    }

}
