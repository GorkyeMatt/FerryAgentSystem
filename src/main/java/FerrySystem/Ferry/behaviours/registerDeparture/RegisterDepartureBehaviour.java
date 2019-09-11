package FerrySystem.Ferry.behaviours.registerDeparture;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.DepartureInfo;
import FerrySystem.Commons.DepartureRegistrationData;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;

public class RegisterDepartureBehaviour extends AskAndWaitBehaviour {
    private FerryAgent myFerryAgent;
    private DepartureRegistrationData departureRegistrationData;

    public RegisterDepartureBehaviour(FerryAgent agent, DepartureRegistrationData departureRegistrationData) {
        super(agent);
        this.myFerryAgent = agent;
        this.departureRegistrationData = departureRegistrationData;
    }

    @Override
    protected ACLMessage prepareMessage() {
        var serializedDeparture = jsonSerializer.serialize(departureRegistrationData);
        var portAID = myFerryAgent.getFerry().getMyPort().getAgentAID();

        var message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_DEPARTURE_REGISTER);
        message.addReceiver(portAID);
        message.setContent(serializedDeparture);
        return message;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        myFerryAgent.getLogger().log(received);

        if(received.getPerformative() == ACLMessage.AGREE){
            var content = received.getContent();
            var id = Integer.parseInt(content);
            var departures = myFerryAgent.getFerry().getDepartureInfos();
            var departureInfo = new DepartureInfo(id, departureRegistrationData);
            departures.put(id, departureInfo);
        }
        else{
            myFerryAgent.getLogger().log("Unable to register departure");
        }
    }
}