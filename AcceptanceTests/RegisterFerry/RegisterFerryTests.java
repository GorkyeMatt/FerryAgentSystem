package RegisterFerry;

import FerrySystem.Commons.Ferry;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterFerryTests {

    private ContainerController containerController;
    private PortAgent port;

    @BeforeEach
    void setUp() throws StaleProxyException {
        containerController = createContainer();
        startPortAgent(containerController);
    }

    private ContainerController createContainer() {
        Profile profile = new ProfileImpl();

        var runtime = Runtime.instance();
        var containerController = runtime.createMainContainer(profile);
        return containerController;
    }

    private void startPortAgent(ContainerController cc) throws StaleProxyException {
        port = new PortAgent();

        AgentController ac = cc.acceptNewAgent("port", port);
        ac.start();
    }

    @Test
    public void RegisterFerry() throws StaleProxyException, InterruptedException {
        FerryAgent ferryAgent = new FerryAgent();

        AgentController ac = containerController.acceptNewAgent("1", ferryAgent);
        ac.start();

        Thread.sleep(1000);
        ferryAgent.getLogger().log("ferries: " + port.getFerriesCount() );
        ferryAgent.getLogger().log("ferries: " + port.getFerriesCount() );

    }
}
