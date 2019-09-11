package FerrySystem.Client.behaviours.negotiations;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.time.LocalDateTime;

public class ListenNegotiationBehaviour extends CyclicMessageReceiveBehaviour {

    private CarAgent myCarAgent;
    private LocalDateTime previouslyProposedTime;

    public ListenNegotiationBehaviour(CarAgent myCarAgent) {
        super(myCarAgent);
        this.myCarAgent = myCarAgent;
    }

    @Override
    public void onMessageReceived(ACLMessage message) {
        logger.logReceived(message);

        if(message.getPerformative() == ACLMessage.AGREE){
            myCarAgent.getMyCar().setDepartureTime(previouslyProposedTime);

        }else {
            var timeText = message.getContent();
            var proposedTime = LocalDateTime.parse(timeText);
            previouslyProposedTime = proposedTime;

            var response = message.createReply();
            response.setPerformative(ACLMessage.PROPOSE);

            var estimatedArrivalTime = myCarAgent.getMyCar().getEstimatedArrivalTime();
            if(estimatedArrivalTime.isBefore(proposedTime)){
                response.setContent("" + proposedTime);
            }
            else{
                response.setContent("" + estimatedArrivalTime);
            }


            myAgent.send(response);
            logger.logSend(response);
        }

    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_NEGOTIATION),
                MessageTemplate.or(
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchPerformative(ACLMessage.AGREE))
        );
    }
}
