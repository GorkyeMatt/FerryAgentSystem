package FerrySystem.Ferry.behaviours;

import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import jade.lang.acl.ACLMessage;

public class InformScheduleBehaviour extends CyclicMessageReceiveBehaviour {
    public InformScheduleBehaviour(BasicAgent a) {
        super(a);
    }

    @Override
    public void prepareMessageTemplate() {

    }

    @Override
    public void onMessageReceived(ACLMessage message) {

    }
}
