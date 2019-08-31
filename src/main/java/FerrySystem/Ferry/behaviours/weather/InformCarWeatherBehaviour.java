package FerrySystem.Ferry.behaviours.weather;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import FerrySystem.Ferry.FerryAgent;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Takes request from client, asks port about weather and forwards answer to client
 */
public class InformCarWeatherBehaviour extends AskAndWaitBehaviour
{
    private FerryAgent myFerryAgent;
    private ACLMessage receivedFromClient;

    public InformCarWeatherBehaviour(FerryAgent agent, ACLMessage receivedFromClient)
    {
        super(agent);
        myFerryAgent = agent;
        this.receivedFromClient = receivedFromClient;
    }

    @Override
    protected void prepareMessage()
    {
        message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT);

        message.addReceiver(myFerryAgent.getFerry().getMyPort().getAgentAID());
    }

    @Override
    public void onMessageSending()
    {
        myBasicAgent.getLogger().log("Ferry ask about weather");
    }

    @Override
    protected void prepareMessageTemplate()
    {
        messageTemplate =  MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        var response = receivedFromClient.createReply();
        response.setContent(received.getContent());

        myAgent.send(response);
    }
}
