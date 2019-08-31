package FerrySystem.Client;

import FerrySystem.Client.behaviours.AskFerriesBehaviour;
import FerrySystem.Client.behaviours.AskWeatherBehaviour;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.BasicAgent;

public class CarAgent extends BasicAgent
{

    //region Fields

    private Car myCar;
    //private Logger logger = new SimpleLogger();

    //endregion

    //region Getters and setters

    //public Logger getLogger() {return logger;}

    public Car getMyCar() {return myCar;}

    //endregion

    //region Setup

    public CarAgent(Car car)
    {
        this.myCar = car;
    }

    @Override
    protected void setup() {
        super.setup();
        logger.setAgentName(this.getName());
        logger.log("Car is ready");
        myCar.setAgentAID(this.getAID());
    }

    //endregion

    //region Ask about weather

    public void askAboutWeather(Ferry ferry){
        var askWeather = new AskWeatherBehaviour(this, ferry);
        addBehaviour(askWeather);
    }

    public void askFerries(){
        var ask = new AskFerriesBehaviour(this);
        addBehaviour(ask);
    }

    //region

}
