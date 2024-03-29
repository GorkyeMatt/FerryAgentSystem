package FerrySystem.Commons.helpers.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public abstract class OneMessageReceiveBehaviour extends SimpleBehaviour {
    public OneMessageReceiveBehaviour(Agent a) {
        super(a);
    }

    public abstract void onMessageReceived(ACLMessage message);
    public abstract MessageTemplate messageTemplate();

    private boolean receivedMessage = false;

    @Override
    public final void action() {
        var message = myAgent.receive(messageTemplate());
        if (message != null){
            onMessageReceived(message);
            receivedMessage = true;
        }
        else{
            block();
        }
    }

    @Override
    public final boolean done() {
        return receivedMessage;
    }
}
