package helpers;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class jadeStarter {

    private AgentContainer containerController;

    public jadeStarter() {
        Profile profile = new ProfileImpl();

        var runtime = Runtime.instance();
        containerController = runtime.createMainContainer(profile);
    }

    public void startAgent(String name, Agent agent){
        try {
            var agentController = containerController.acceptNewAgent(name, agent);
            agentController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        var runtime = Runtime.instance();
        runtime.shutDown();
    }
}
