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
    private boolean finished = false;
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
    public void onStart() {
//        super.onStart();
        message = prepareMessage();
        prepareMessageTemplate();

        sendMessage();
    }

    @Override
    public void action()
    {
        var received = myAgent.receive(messageTemplate);
        if (received != null)
        {
            myBasicAgent.getLogger().logReceived(received);
            onMessageReceived(received);
            finished = true;
        }
        else
        {
            block();
        }
    }

    private ACLMessage message;
    /** Prepare message to be sent */
    protected abstract ACLMessage prepareMessage();

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

    /** Perform some action when message specified in message template is received */
    public abstract void onMessageReceived(ACLMessage received);

    @Override
    public boolean done()
    {
        return finished;
    }
}
