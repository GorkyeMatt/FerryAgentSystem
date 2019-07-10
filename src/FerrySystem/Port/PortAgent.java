package FerrySystem.Port;

import FerrySystem.Port.Behaviours.InformWeatherBehaviour;
import FerrySystem.Port.Data.FerryInform;
import jade.core.Agent;

import java.util.Vector;

public class PortAgent extends Agent {

    public Vector<FerryInform> ferries;

    public PortAgent() {

        var b = new InformWeatherBehaviour(this);
        addBehaviour(b);
    }
}
