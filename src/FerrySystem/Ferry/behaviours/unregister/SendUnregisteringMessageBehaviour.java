package FerrySystem.Ferry.behaviours.unregister;

import FerrySystem.Commons.Defines;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendUnregisteringMessageBehaviour extends OneShotBehaviour {
    private FerryAgent myFerryAgent;

    SendUnregisteringMessageBehaviour(FerryAgent agent) {
        super(agent);
        this.myFerryAgent = agent;
    }

    @Override
    public void action() {
        var message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER);
        message.addReceiver(myFerryAgent.getFerry().getMyPort().getAgentAID());

        var ferry = myFerryAgent.getFerry();
        message.setContent(ferry.getId() + "");

        myAgent.send(message);

        myFerryAgent.getLogger().log("Sent unregistering message");
    }
}
