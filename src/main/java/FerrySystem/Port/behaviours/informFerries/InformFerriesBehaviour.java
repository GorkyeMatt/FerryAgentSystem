package FerrySystem.Port.behaviours.informFerries;

import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformFerriesBehaviour extends CyclicMessageReceiveBehaviour
{
    public InformFerriesBehaviour(Agent agent)
    {
        super(agent);
    }

    @Override
    public void onMessageReceived(ACLMessage message)
    {

    }

    @Override
    public MessageTemplate messageTemplate()
    {
        return null;
    }
}
