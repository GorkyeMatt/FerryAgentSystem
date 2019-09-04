package FerrySystem.Ferry.behaviours.register;

import FerrySystem.Commons.*;
import FerrySystem.Commons.helpers.JsonSerializer;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendRegistrationMessageBehaviour extends OneShotBehaviour {
    private FerryAgent myFerryAgent;
    private Port port;

    private JsonSerializer jsonSerializer = new JsonSerializer();

    SendRegistrationMessageBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
    }

    @Override
    public void action() {
        var message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER);
        message.addReceiver(port.getAgentAID());

        var ferry = myFerryAgent.getFerry();
        var serialized = jsonSerializer.serialize(ferry);
        message.setContent(serialized);

        myAgent.send(message);

        myFerryAgent.getLogger().log("Sent registration message");
    }
}