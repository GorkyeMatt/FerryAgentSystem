package FerrySystem.Client.behaviours.negotiations;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import jade.lang.acl.ACLMessage;

import java.time.LocalDateTime;

public class AskAwaitBehaviour extends AskAndWaitBehaviour {
    private CarAgent myCarAgent;
    private LocalDateTime proposedTime;

    public AskAwaitBehaviour(CarAgent a, LocalDateTime proposedTime) {
        super(a);
        this.myCarAgent = a;
        this.proposedTime = proposedTime;
    }

    @Override
    protected ACLMessage prepareMessage() {
        var departureInfo = myCarAgent.getMyCar().getActuallyRegister();

        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(departureInfo.ferryAID);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_AWAIT);
        message.setContent("" + proposedTime);
        return message;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        var car = myCarAgent.getMyCar();

        switch (received.getPerformative()){
            case ACLMessage.INFORM:
                var content = received.getContent();
                var time = LocalDateTime.parse(content);
                car.setDepartureTime(time);
                break;
            case ACLMessage.ACCEPT_PROPOSAL:
                car.setDepartureTime(proposedTime);
                break;
            case ACLMessage.REJECT_PROPOSAL:
                car.setDepartureTime(null);
                car.setActuallyRegister(null);
                break;
            case ACLMessage.NOT_UNDERSTOOD:
                myCarAgent.getLogger().log("Sth went wrong");
                break;
        }
    }
}
