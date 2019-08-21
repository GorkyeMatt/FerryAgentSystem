package FerrySystem.Client.behaviours.weather;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.SimpleLogger;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class SendWeatherRequestBehaviour extends OneShotBehaviour
{
    private CarAgent myCarAgent;
    private Ferry myFerry;

    public SendWeatherRequestBehaviour(CarAgent agent, Ferry ferry){
        super(agent);
        myCarAgent = agent;
        myFerry = ferry;
    }

    public void action()
    {
        myCarAgent.getLogger().log("Sending request");

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        msg.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_WEATHER);

        msg.addReceiver(myFerry.getAgentAID());
        myAgent.send(msg);
    }

}
