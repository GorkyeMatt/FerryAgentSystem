package FerrySystem.Port.Behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Port.PortAgent;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class RegisterFerryBehaviour extends CyclicBehaviour {

    private PortAgent myPortAgent;
    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger;

    public RegisterFerryBehaviour(PortAgent myPortAgent) {
        super(myPortAgent);
        this.myPortAgent = myPortAgent;
        logger = myPortAgent.getLogger();
    }

    private MessageTemplate template =  MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

    @Override
    public void action() {
        var received = myAgent.receive(template);
        if (received != null) {
            logger.log("received:\n:" + received);

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

            logger.log("sending:\n:" + message);
            myAgent.send(message);
        }
        else {
            block();
        }
    }
}
