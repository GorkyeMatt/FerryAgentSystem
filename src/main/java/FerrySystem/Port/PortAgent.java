package FerrySystem.Port;

import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.Port;
import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Port.behaviours.RegisterFerryBehaviour;
import FerrySystem.Port.behaviours.UnregisteringFerryBehaviour;
import FerrySystem.Port.behaviours.InformFerriesBehaviour;
import FerrySystem.Port.behaviours.InformWeatherBehaviour;

public class PortAgent extends BasicAgent {

    private Port myPort;

    public PortAgent(Port port) {
        this.myPort = port;
    }

    public Port getMyPort() {
        return myPort;
    }

    @Override
    protected void setup() {
        super.setup();
        logger.setAgentName(this.getName());
        logger.log("Ready to work");

        var registerFerryBehaviour = new RegisterFerryBehaviour(this);
        addBehaviour(registerFerryBehaviour);

        var unregisteringFerryBehaviour = new UnregisteringFerryBehaviour(this);
        addBehaviour(unregisteringFerryBehaviour);

        var informWeather = new InformWeatherBehaviour(this);
        addBehaviour(informWeather);

        var informFerries = new InformFerriesBehaviour(this);
        addBehaviour(informFerries);
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
