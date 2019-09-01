package FerrySystem.Ferry.behaviours.unregister;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UnregisterFromPortBehaviour extends AskAndWaitBehaviour {

    private FerryAgent myFerryAgent;

    public UnregisterFromPortBehaviour(FerryAgent agent) {
        super(agent);
        this.myFerryAgent = agent;
    }

    @Override
    protected void prepareMessage() {
        message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER);
        message.addReceiver(myFerryAgent.getFerry().getMyPort().getAgentAID());

        var ferry = myFerryAgent.getFerry();
        message.setContent(ferry.getId() + "");
    }

    @Override
    public void onMessageSending() {
        myFerryAgent.getLogger().log("Sent unregistering message");
    }

    @Override
    protected void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER);
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        myFerryAgent.getLogger().log("received:\n" + received);

        if(received.getPerformative() == ACLMessage.INFORM){
            myFerryAgent.finishUnregistering();
        }
    }
}
