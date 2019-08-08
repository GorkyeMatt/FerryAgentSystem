package FerrySystem.Ferry.behaviours.registerDeparture;

import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.SequentialBehaviour;

public class RegisterDepartureBehaviour extends SequentialBehaviour {
    private FerryAgent myFerryAgent;

    public RegisterDepartureBehaviour(FerryAgent agent) {
        super(agent);
        this.myFerryAgent = agent;
        this.prepare();
    }

    private void prepare(){
        var sendRegistration = new SendRegistrationMessageBahaviour();
        addSubBehaviour(sendRegistration);

        var confirmRegistration = new ConfirmDepartureRegistrationBehaviour();
        addSubBehaviour(confirmRegistration);
    }
}
