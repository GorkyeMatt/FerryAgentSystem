package FerrySystem.Ferry.behaviours.weather;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Waits for weather info request
 */
public class InformWeatherBehaviour extends CyclicMessageReceiveBehaviour
{
    private FerryAgent myFerryAgent;

    public InformWeatherBehaviour(FerryAgent agent)
    {
        super(agent);
        this.myFerryAgent = agent;
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM ));
    }

    @Override
    public void onMessageReceived(ACLMessage message)
    {
        var informCar = new InformCarWeatherBehaviour(myFerryAgent, message);
        myFerryAgent.addBehaviour(informCar);
    }
}
