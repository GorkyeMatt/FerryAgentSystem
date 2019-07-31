package FerrySystem.Port;

import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.SimpleLogger;
import FerrySystem.Port.Behaviours.RegisterFerryBehaviour;
import jade.core.Agent;

import java.util.Vector;

public class PortAgent extends Agent {

    private Vector<Ferry> registeredFerries = new Vector<>();
    private Logger logger = new SimpleLogger();

    public int getFerriesCount(){
        return registeredFerries.size();
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    protected void setup() {
        super.setup();
        logger.setAgentName(this.getName());
        logger.log("Ready to work");

        var registerFerryBehaviour = new RegisterFerryBehaviour(this);
        addBehaviour(registerFerryBehaviour);
    }

    public void addFerry(Ferry ferry) {
        logger.log("Adding new ferry");
        int id = registeredFerries.size();
        ferry.setId(id);
        registeredFerries.add(ferry);
    }
}
