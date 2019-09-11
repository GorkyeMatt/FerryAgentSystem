package FerrySystem.Ferry.behaviours.negotiations;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.RegisteredCar;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

public class ListenForAwaitRequestBehaviour extends CyclicMessageReceiveBehaviour {

    private FerryAgent myFerryAgent;

    public ListenForAwaitRequestBehaviour(FerryAgent a) {
        super(a);
        this.myFerryAgent = a;
    }

    @Override
    public void onMessageReceived(ACLMessage message) {
        logger.logReceived(message);

        var content = message.getContent();
        var time = LocalDateTime.parse(content);

        var senderAID = message.getSender();

        var departureID = -1;
        for (RegisteredCar car: myFerryAgent.getFerry().getRegisteredCars()) {
            if(car.clientAid.getName().compareTo(senderAID.getName()) == 0){
                departureID = car.DepartureId;
            }
        }

        var response = message.createReply();

        if(departureID == -1){
            response.setPerformative(ACLMessage.NOT_UNDERSTOOD);
            myAgent.send(response);
        }

        var departure = myFerryAgent.getFerry().getDepartureInfos().get(departureID);
        if(departure == null){
            response.setPerformative(ACLMessage.NOT_UNDERSTOOD);
            myAgent.send(response);
        }
        else{
            var timeDifference = Duration.between(time, departure.time);

            if(Math.abs(timeDifference.getSeconds()) <= Defines.MAX_TIME_DIFFERENCE.getSeconds() ){
                myFerryAgent.addBehaviour(new StartNegotiationBehaviour(myFerryAgent, departureID, time, response, senderAID));
            }
            else{
                response.setPerformative(ACLMessage.REFUSE);
                myAgent.send(response);
            }
        }
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate =  MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_AWAIT),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST ));
    }
}

