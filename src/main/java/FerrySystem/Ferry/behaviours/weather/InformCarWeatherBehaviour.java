package FerrySystem.Ferry.behaviours.weather;

import FerrySystem.Ferry.FerryAgent;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class InformCarWeatherBehaviour extends SequentialBehaviour
{
    private FerryAgent myFerryAgent;

    public InformCarWeatherBehaviour(FerryAgent agent, ACLMessage receivedFromClient)
    {
        super(agent);
        myFerryAgent = agent;

        this.prepare(receivedFromClient);
    }

    private void prepare(ACLMessage message)
    {
        var send = new SendWeatherRequestBehaviour(myFerryAgent);
        addSubBehaviour(send);

        var receive = new ReceiveWeatherInfoBehaviour(myFerryAgent, message);
        addSubBehaviour(receive);
    }
}
