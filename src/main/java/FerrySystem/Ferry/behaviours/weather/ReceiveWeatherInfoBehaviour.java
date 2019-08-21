package FerrySystem.Ferry.behaviours.weather;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.OneMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveWeatherInfoBehaviour extends OneMessageReceiveBehaviour
{
    private FerryAgent myFerryAgent;
    private ACLMessage fromClient;

    public ReceiveWeatherInfoBehaviour(FerryAgent agent, ACLMessage fromClient)
    {
        super(agent);

        this.myFerryAgent = agent;
        this.fromClient = fromClient;
    }

    @Override
    public void onMessageReceived(ACLMessage message)
    {
        myFerryAgent.getLogger().log("received:\n:" + message);

        var response = fromClient.createReply();
        response.setContent(message.getContent());

        myAgent.send(response);
    }

    private MessageTemplate template =  MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT),
            MessageTemplate.MatchPerformative(ACLMessage.INFORM ));

    @Override
    public MessageTemplate messageTemplate()
    {
        return template;
    }
}
