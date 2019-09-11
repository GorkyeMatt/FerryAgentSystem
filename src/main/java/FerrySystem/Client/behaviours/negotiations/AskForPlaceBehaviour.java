package FerrySystem.Client.behaviours.negotiations;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.time.LocalDateTime;

public class AskForPlaceBehaviour extends AskAndWaitBehaviour {
    private CarAgent myCarAgent;
    private LocalDateTime proposedTime;

    public AskForPlaceBehaviour(CarAgent a) {
        super(a);
        this.myCarAgent = a;
        this.proposedTime =  myCarAgent.getMyCar().getEstimatedArrivalTime();
    }

    public AskForPlaceBehaviour(CarAgent a, LocalDateTime proposedTime) {
        super(a);
        this.myCarAgent = a;
        this.proposedTime = proposedTime;
    }

    @Override
    protected ACLMessage prepareMessage() {
        var departureInfo = myCarAgent.getMyCar().getDepartureInfo();

        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);

        message.addReceiver(departureInfo.ferryAID);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_PLACE);

        message.setContent(""  + departureInfo.Id + '\n' + proposedTime);

        return message;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        var car = myCarAgent.getMyCar();

        if(received.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
            car.setActuallyRegister(car.getDepartureInfo());
            car.setDepartureTime(proposedTime);

            var listenNegotiation = new ListenNegotiationBehaviour(myCarAgent);
            myCarAgent.addBehaviour(listenNegotiation);
            myCarAgent.setListenNegotiation(listenNegotiation);
        }
        else if(received.getPerformative() == ACLMessage.INFORM){
            var timeText = received.getContent();
            var proposedTime = LocalDateTime.parse(timeText);
            car.setDepartureTime(proposedTime);
        }
    }
}
