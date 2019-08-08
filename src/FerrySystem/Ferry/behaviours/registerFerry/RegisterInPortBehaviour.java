package FerrySystem.Ferry.behaviours.registerFerry;

import FerrySystem.Commons.data.Port;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.SequentialBehaviour;

public class RegisterInPortBehaviour extends SequentialBehaviour {

    private FerryAgent myFerryAgent;
    private Port port;

    public RegisterInPortBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
        this.prepare();
    }

    private void prepare(){
        var sendRegistration = new SendRegistrationMessageBehaviour(myFerryAgent, port);
        addSubBehaviour(sendRegistration);

        var confirmRegistration = new ConfirmRegistrationBehaviour(myFerryAgent, port);
        addSubBehaviour(confirmRegistration);
    }
}

