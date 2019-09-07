package FerrySystem.Client.behaviours.negotiations;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AskForPlaceBehaviour extends AskAndWaitBehaviour {
    private CarAgent myCarAgent;

    public AskForPlaceBehaviour(CarAgent a) {
        super(a);
        this.myCarAgent = a;
    }

    @Override
    protected ACLMessage prepareMessage() {
        var departureInfo = myCarAgent.getMyCar().getDepartureInfo();

        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);

        message.addReceiver(departureInfo.ferryAID);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_PLACE);

        message.setContent(""  + departureInfo.Id + '\n' + myCarAgent.getMyCar().getEstimatedArrivalTime());

        return message;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        if(received.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
            var car = myCarAgent.getMyCar();
            car.setActuallyRegister(car.getDepartureInfo());
            car.setDepartureTime(myCarAgent.getMyCar().getEstimatedArrivalTime());

            var listenNegotiation = new ListenNegotiationBehaviour(myCarAgent);
            myCarAgent.addBehaviour(listenNegotiation);
            myCarAgent.setListenNegotiation(listenNegotiation);
        }
    }
}
