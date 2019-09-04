package FerrySystem.Client.behaviours;

import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import jade.lang.acl.ACLMessage;

public class AskScheduleBehaviour extends AskAndWaitBehaviour {
    public AskScheduleBehaviour(BasicAgent a) {
        super(a);
    }

    @Override
    protected ACLMessage prepareMessage() {
        return null;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {

    }
}
