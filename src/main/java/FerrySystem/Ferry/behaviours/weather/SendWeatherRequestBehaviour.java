package FerrySystem.Ferry.behaviours.weather;

import FerrySystem.Commons.Defines;
import FerrySystem.Ferry.FerryAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class SendWeatherRequestBehaviour extends OneShotBehaviour
{
    private FerryAgent myFerryAgent;

    public SendWeatherRequestBehaviour(FerryAgent agent)
    {
        super(agent);
        myFerryAgent = agent;
    }

    @Override
    public void action()
    {
        myFerryAgent.getLogger().log("Ferry ask about weather");

        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT);

        message.addReceiver(myFerryAgent.getFerry().getMyPort().getAgentAID());
        myAgent.send(message);
    }
}
