package FerrySystem.Ferry.behaviours;

import FerrySystem.Commons.Port;
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

    public void prepare(){
        var sendRegistration = new SendRegistrationMessageBehaviour(myFerryAgent, port);
        var confirmRegistration = new ConfirmRegistrationBehaviour(myFerryAgent, port);

        addSubBehaviour(sendRegistration);
        addSubBehaviour(confirmRegistration);
    }
}

