package FerrySystem.Ferry.behaviours.negotiations;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.RegisteredCar;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

public class AwaitClientBehaviour extends CyclicMessageReceiveBehaviour {

    private FerryAgent myFerryAgent;

    public AwaitClientBehaviour(FerryAgent a) {
        super(a);
        this.myFerryAgent = a;
    }

    @Override
    public void onMessageReceived(ACLMessage message) {
        logger.logReceived(message);

        var content = message.getContent();
        var index = content.indexOf('\n');
        var idText = content.substring(0, index);
        var id = Integer.parseInt(idText);

        var timeText = content.substring(index + 1);
        var time = LocalDateTime.parse(timeText);


        var departure = myFerryAgent.getFerry().getDepartureInfos().get(id);

        var response = message.createReply();
        if(departure == null){
            response.setPerformative(ACLMessage.NOT_UNDERSTOOD);
            myAgent.send(response);
        }
        else{
            var timeDifference = Duration.between(time, departure.time);

            if(Math.abs(timeDifference.getSeconds()) < Defines.MAX_TIME_DIFFERENCE.getSeconds() ){
                myFerryAgent.addBehaviour(new StartNegotiationBehaviour(myFerryAgent, id, time, response));
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
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_PLACE),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST ));
    }
}
