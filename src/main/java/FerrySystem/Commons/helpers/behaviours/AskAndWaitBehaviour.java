package FerrySystem.Commons.helpers.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.BasicAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
    public abstract void prepareMessage();

    public void sendMessage()
    {
        myBasicAgent.getLogger().logSend(message);
        myAgent.send(message);
    }

    protected MessageTemplate messageTemplate;
    public abstract void prepareMessageTemplate();

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

    public abstract void onMessageReceived(ACLMessage received);

    @Override
    public boolean done()
    {
        return state >= 2;
    }
}
