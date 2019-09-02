package FerrySystem.Client.behaviours;

import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import jade.lang.acl.ACLMessage;

public class AskDepartureDetailsBehaviour extends AskAndWaitBehaviour {
    public AskDepartureDetailsBehaviour(BasicAgent a) {
        super(a);
    }

    @Override
    protected void prepareMessage() {

    }

    @Override
    public void onMessageReceived(ACLMessage received) {

    }
}
