package FerrySystem.Ferry.behaviours.registerDeparture;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.data.DepartureRegistrationData;
import FerrySystem.Commons.helpers.JsonSerializer;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SendDepartureRegistrationMessageBehaviour extends Behaviour {
    private FerryAgent myFerryAgent;
    private DepartureRegistrationData departure;

    private JsonSerializer jsonSerializer = new JsonSerializer();

    public SendDepartureRegistrationMessageBehaviour(FerryAgent agent, DepartureRegistrationData departure) {
        super(agent);
        this.myFerryAgent = agent;
        this.departure = departure;
    }

    @Override
    public void action() {

        var serializedDeparture = jsonSerializer.serialize(departure);
        var portAID = myFerryAgent.getFerry().getMyPort().getAgentAID();

        var message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_DEPARTURE_REGISTER);
        message.addReceiver(portAID);
        message.setContent(serializedDeparture);

        myAgent.send(message);
        myFerryAgent.getLogger().log("Sent departure registration message:" + message);
    }

    @Override
    public boolean done() {
        return false;
    }
}
