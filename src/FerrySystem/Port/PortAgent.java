package FerrySystem.Port;

import FerrySystem.Port.Behaviours.InformWeatherBehaviour;
import jade.core.Agent;

import java.util.Vector;

public class PortAgent extends Agent {


    public PortAgent() {

        var b = new InformWeatherBehaviour(this);
        addBehaviour(b);
    }
}
