package FerrySystem.Port;

import FerrySystem.Commons.data.Ferry;
import FerrySystem.Commons.data.Port;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.SimpleLogger;
import FerrySystem.Port.behaviours.RegisterDepartureBehaviour;
import FerrySystem.Port.behaviours.RegisterFerryBehaviour;
import FerrySystem.Port.behaviours.UnregisteringFerryBehaviour;
import jade.core.Agent;

public class PortAgent extends Agent {

    private Logger logger = new SimpleLogger();
    private Port myPort;

    public PortAgent(Port port) {
        this.myPort = port;
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
        var unregisteringFerryBehaviour = new UnregisteringFerryBehaviour(this);
        var registerDepartureBehaviour = new RegisterDepartureBehaviour(this);

        addBehaviour(registerFerryBehaviour);
        addBehaviour(unregisteringFerryBehaviour);
        addBehaviour(registerDepartureBehaviour);

    }

    public void addFerry(Ferry ferry) {
        logger.log("Adding new ferry: " + ferry.getAgentAID());
        var ferries = myPort.getRegisteredFerries();
        ferries.add(ferry);

        var id = ferries.size();
        ferry.setId(id);
    }

    public void removeFerry(int id){
        logger.log("removing ferry:" + id);
        var ferries = myPort.getRegisteredFerries();
        ferries.removeIf(ferry -> ferry.getId() == id);
    }

}
