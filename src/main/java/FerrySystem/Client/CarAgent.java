package FerrySystem.Client;

import FerrySystem.Client.behaviours.*;
import FerrySystem.Client.behaviours.negotiations.AskForPlaceBehaviour;
import FerrySystem.Client.behaviours.negotiations.ListenNegotiationBehaviour;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.BasicAgent;
import jade.core.AID;

public class CarAgent extends BasicAgent
{

    //region Fields

    private Car myCar;
    private ListenNegotiationBehaviour listenNegotiation;

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

    public void askFerries(AID aid){
        var ask = new AskFerriesBehaviour(this, aid);
        addBehaviour(ask);
    }

    public void askDepartureDetails(){
        addBehaviour(new AskDepartureDetailsBehaviour(this));
    }

    public void askSchedule(){
        addBehaviour(new AskScheduleBehaviour(this));
    }

    //endregion



    //region Negotiations


    public void askForPlace(){
        addBehaviour(new AskForPlaceBehaviour(this));
    }

    public ListenNegotiationBehaviour getListenNegotiation() {
        return listenNegotiation;
    }

    public void setListenNegotiation(ListenNegotiationBehaviour listenNegotiation) {
        this.listenNegotiation = listenNegotiation;
    }

    //endregion

}
