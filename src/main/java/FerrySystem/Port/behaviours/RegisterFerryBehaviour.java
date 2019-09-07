package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class RegisterFerryBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;

    public RegisterFerryBehaviour(PortAgent myPortAgent) {
        super(myPortAgent);
        this.myPortAgent = myPortAgent;
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate =  MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        logger.logReceived(received);

        var message = received.createReply();

        try {
            var json = received.getContent();
            Ferry ferry = mapper.readValue(json, Ferry.class);

            myPortAgent.addFerry(ferry);

            message.setPerformative(ACLMessage.AGREE);
            message.setContent(ferry.getId() + "");
        } catch (IOException e) {
            e.printStackTrace();
            message.setPerformative(ACLMessage.REFUSE);
        }

        logger.logSend(message);
        myAgent.send(message);
    }


}