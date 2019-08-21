package FerrySystem.Commons.helpers.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public abstract class CyclicMessageReceiveBehaviour extends CyclicBehaviour {

    public CyclicMessageReceiveBehaviour(Agent a) {
        super(a);
    }

    public abstract void onMessageReceived(ACLMessage message);
    public abstract MessageTemplate messageTemplate();

    @Override
    public final void action() {
        var message = myAgent.receive(messageTemplate());
        if (message != null){
            onMessageReceived(message);
        }
        else{
            block();
        }
    }
}
