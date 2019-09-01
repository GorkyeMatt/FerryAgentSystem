package helpers;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.Port;
import FerrySystem.Ferry.FerryAgent;
import FerrySystem.Port.PortAgent;
import jade.core.AID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class CommonPreparationForTests
{
    protected jadeStarter jadeStarter;

    @BeforeEach
    void setUp(){
        jadeStarter = new jadeStarter();
    }

    @AfterEach
    void tearDown(){
        jadeStarter.close();
    }

    public PortAgent CreatePortAgent(String name) throws InterruptedException{
        var port = new Port();
        port.setAgentAID(new AID(name, AID.ISLOCALNAME));

        var portAgent = new PortAgent(port);
        jadeStarter.startAgent(name, portAgent);
        Thread.sleep(50);

        return portAgent;
    }

    public FerryAgent CreateFerryAgent(String name) throws InterruptedException{
        var ferry = new Ferry();
        ferry.setAgentAID(new AID(name, AID.ISLOCALNAME));

        var ferryAgent = new FerryAgent(ferry);
        jadeStarter.startAgent(name, ferryAgent);

        Thread.sleep(50);
        return ferryAgent;
    }

    public CarAgent CreateCarAgent(String name) throws InterruptedException{
        var car = new Car();
        car.setAgentAID(new AID(name, AID.ISLOCALNAME));

        var carAgent = new CarAgent(car);
        jadeStarter.startAgent(name, carAgent);

        Thread.sleep(50);
        return carAgent;
    }

}
