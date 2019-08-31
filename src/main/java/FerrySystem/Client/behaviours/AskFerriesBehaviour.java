package FerrySystem.Client.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AskFerriesBehaviour extends AskAndWaitBehaviour
{
    public AskFerriesBehaviour(BasicAgent a)
    {
        super(a);
    }

    @Override
    public void prepareMessage()
    {
        message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER);

        message.addReceiver(myAgent.getAID());
    }

    @Override
    public void prepareMessageTemplate()
    {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        myBasicAgent.getLogger().log("Received answer for ask ferries");
    }
}
