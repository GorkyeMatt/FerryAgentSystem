package FerrySystem.Commons.helpers.behaviours;

import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.JsonSerializer;
import FerrySystem.Commons.helpers.Logger;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public abstract class CyclicMessageReceiveBehaviour extends CyclicBehaviour {

    protected JsonSerializer jsonSerializer;
    protected ObjectMapper mapper;

    protected MessageTemplate messageTemplate;

    protected Logger logger;

    public CyclicMessageReceiveBehaviour(BasicAgent a) {
        super(a);
        logger = a.getLogger();
        prepareMessageTemplate();

        jsonSerializer = new JsonSerializer();
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public abstract void onMessageReceived(ACLMessage message);

    /**
     * Set messageTemplate field
     */
    public abstract void prepareMessageTemplate();

    public MessageTemplate messageTemplate(){
        return messageTemplate;
    }

    @Override
    public final void action() {
        var message = myAgent.receive(messageTemplate());
        if (message != null){
            onMessageReceived(message);
        }
        else{
            block();
        }
    }
}