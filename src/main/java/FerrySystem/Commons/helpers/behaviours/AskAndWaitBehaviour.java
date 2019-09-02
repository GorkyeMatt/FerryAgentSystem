package FerrySystem.Commons.helpers.behaviours;

import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.JsonSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Sends request and waits for the answer
 */
public abstract class AskAndWaitBehaviour extends SimpleBehaviour
{
    private int state;
    protected BasicAgent myBasicAgent;
    /** for serializing object to message */
    protected JsonSerializer jsonSerializer;

    /** for deserializing object from message*/
    protected ObjectMapper mapper;

    public AskAndWaitBehaviour(BasicAgent a)
    {
        super(a);
        this.myBasicAgent = a;
        this.state = 0;

        this.jsonSerializer = new JsonSerializer();
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void action()
    {
        switch (state)
        {
            case 0:
                prepareMessage();
                prepareMessageTemplate();

                sendMessage();
                state++;
                waitForMessage();
                break;
            case 1:
                waitForMessage();
                break;
        }
    }

    protected ACLMessage message;
    /** Prepare message to be sent */
    protected abstract void prepareMessage();

    public void sendMessage()
    {
        onMessageSending();
        myBasicAgent.getLogger().logSend(message);
        myAgent.send(message);
    }

    /**
     * Perform some action (log) before sending message
     */
    public void onMessageSending(){}

    protected MessageTemplate messageTemplate;
    /** Prepare message template behaviour is waiting for */
    protected void prepareMessageTemplate(){
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(message.getOntology()),
                MessageTemplate.MatchReceiver(new AID[]{myAgent.getAID()}));
    }

    public void waitForMessage()
    {
        var received = myAgent.receive(messageTemplate);
        if (received != null)
        {
            myBasicAgent.getLogger().logReceived(received);
            onMessageReceived(received);
            state++;
        }
        else
        {
            block();
        }
    }

    /** Perform some action when message specified in message template is received */
    public abstract void onMessageReceived(ACLMessage received);

    @Override
    public boolean done()
    {
        return state >= 2;
    }
}
