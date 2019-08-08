package FerrySystem.Ferry.behaviours.registerDeparture;

import FerrySystem.Commons.data.DepartureRegistrationData;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.SequentialBehaviour;

public class RegisterDepartureBehaviour extends SequentialBehaviour {
    private FerryAgent myFerryAgent;
    private DepartureRegistrationData departure;

    public RegisterDepartureBehaviour(FerryAgent agent, DepartureRegistrationData departure) {
        super(agent);
        this.myFerryAgent = agent;
        this.departure = departure;
        this.prepare();
    }

    private void prepare(){
        var sendRegistration = new SendDepartureRegistrationMessageBehaviour(myFerryAgent, departure);
        addSubBehaviour(sendRegistration);

        var confirmRegistration = new ConfirmDepartureRegistrationBehaviour();
        addSubBehaviour(confirmRegistration);
    }
}
