package FerrySystem.Ferry.behaviours.weather;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AskWeatherBehaviour extends CyclicMessageReceiveBehaviour
{
    private FerryAgent myFerryAgent;
    private Logger logger;

    public AskWeatherBehaviour(FerryAgent agent)
    {
        super(agent);
        this.myFerryAgent = agent;
        logger = agent.getLogger();
    }

    @Override
    public void onMessageReceived(ACLMessage message)
    {
        var informCar = new InformCarWeatherBehaviour(myFerryAgent, message);
        myFerryAgent.addBehaviour(informCar);
    }

    private MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER),
            MessageTemplate.MatchPerformative(ACLMessage.INFORM ));

    @Override
    public MessageTemplate messageTemplate()
    {
        return template;
    }
}
