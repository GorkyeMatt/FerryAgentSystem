package FerrySystem.Port.Behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class RegisterFerryBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;
    private ObjectMapper mapper;
    private Logger logger;

    public RegisterFerryBehaviour(PortAgent myPortAgent) {
        super(myPortAgent);
        this.myPortAgent = myPortAgent;
        logger = myPortAgent.getLogger();

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private MessageTemplate template =  MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
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
}
