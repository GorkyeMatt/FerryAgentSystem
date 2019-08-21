package FerrySystem.Ferry.behaviours.unregister;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.OneMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConfirmUnregisteringBehaviour extends OneMessageReceiveBehaviour {
    private FerryAgent myFerryAgent;

    ConfirmUnregisteringBehaviour(FerryAgent agent) {
        super(agent);
        this.myFerryAgent = agent;
    }

    private MessageTemplate template =  MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER);

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        myFerryAgent.getLogger().log("received:\n" + received);

        if(received.getPerformative() == ACLMessage.INFORM){
            myFerryAgent.finishUnregistering();
        }
    }
}
