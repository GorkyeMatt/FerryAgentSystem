package FerrySystem;

import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {
    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        Profile profile = new ProfileImpl();

        var runtime = Runtime.instance();
        var containerController = runtime.createMainContainer(profile);

        var port = new PortAgent();

        AgentController ac = containerController.acceptNewAgent("port", port);
        ac.start();

        FerryAgent ferryAgent = new FerryAgent();

        ac = containerController.acceptNewAgent("1", ferryAgent);
        ac.start();

        ferryAgent.getLogger().log("ferries: " + port.getFerriesCount() );

        Thread.sleep(10000);
        ferryAgent.getLogger().log("ferries: " + port.getFerriesCount() );

    }
}
