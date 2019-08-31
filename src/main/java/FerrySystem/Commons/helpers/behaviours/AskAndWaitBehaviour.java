package FerrySystem.Commons.helpers.behaviours;

import FerrySystem.Commons.helpers.BasicAgent;
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

    public AskAndWaitBehaviour(BasicAgent a)
    {
        super(a);
        this.myBasicAgent = a;
        this.state = 0;
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
    protected abstract void prepareMessageTemplate();

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
