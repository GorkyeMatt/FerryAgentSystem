package FerrySystem.Ferry.behaviours.unregister;

import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.SequentialBehaviour;

public class UnregisterFromPortBehaviour extends SequentialBehaviour {

    private FerryAgent myFerryAgent;

    public UnregisterFromPortBehaviour(FerryAgent agent) {
        super(agent);
        this.myFerryAgent = agent;
        this.prepare();
    }

    private void prepare(){
        var sendRegistration = new SendUnregisteringMessageBehaviour(myFerryAgent);
        addSubBehaviour(sendRegistration);

        var confirmRegistration = new ConfirmUnregisteringBehaviour(myFerryAgent);
        addSubBehaviour(confirmRegistration);
    }
}
