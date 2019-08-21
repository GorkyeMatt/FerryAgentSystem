package FerrySystem.Client.behaviours.weather;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Ferry;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

public class AskWeatherBehaviour extends SequentialBehaviour
{
    private CarAgent myCarAgent;
    private Ferry myFerry;

    public AskWeatherBehaviour(CarAgent agent, Ferry ferry)
    {
        super(agent);
        this.myCarAgent = agent;
        this.myFerry = ferry;

        this.prepare();
    }

    private void prepare(){
        var sendWeatherRequest = new SendWeatherRequestBehaviour(myCarAgent, myFerry);
        addSubBehaviour(sendWeatherRequest);

        var receiveWeatherInfo = new ReceiveWeatherInfoBehaviour(myCarAgent);
        addSubBehaviour(receiveWeatherInfo);
    }
}
