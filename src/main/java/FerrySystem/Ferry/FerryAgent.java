package FerrySystem.Ferry;

import FerrySystem.Commons.*;
import FerrySystem.Commons.helpers.BasicAgent;
//import FerrySystem.Ferry.behaviours.InformDepartureDetailsBehaviour;
//import FerrySystem.Ferry.behaviours.InformPlaceBehaviour;
//import FerrySystem.Ferry.behaviours.InformScheduleBehaviour;
import FerrySystem.Ferry.behaviours.negotiations.AwaitClientBehaviour;
import FerrySystem.Ferry.behaviours.negotiations.ListenForAwaitRequestBehaviour;
import FerrySystem.Ferry.behaviours.register.RegisterInPortBehaviour;
import FerrySystem.Ferry.behaviours.registerDeparture.RegisterDepartureBehaviour;
import FerrySystem.Ferry.behaviours.unregister.*;
import FerrySystem.Ferry.behaviours.weather.InformWeatherBehaviour;
import jade.core.behaviours.Behaviour;

public class FerryAgent extends BasicAgent {
    private Ferry myFerry;

    public Ferry getFerry() {
        return myFerry;
    }

    //region Setup

    public FerryAgent(Ferry ferry) {
        this.myFerry = ferry;
    }

    @Override
    protected void setup() {
        super.setup();
        logger.setAgentName(this.getName());
        logger.log("Ferry is ready");
        myFerry.setAgentAID(this.getAID());

        addBehaviour(new InformWeatherBehaviour(this));
        addBehaviour(new AwaitClientBehaviour(this));
        addBehaviour(new ListenForAwaitRequestBehaviour(this));
    }

    //endregion

    //region Register in port

    public void registerInPort(Port port){
        logger.log("register in port: " + port);
        var registerBehaviour = new RegisterInPortBehaviour(this, port);
        addBehaviour(registerBehaviour);
    }

    public void informRegisterFailure(){
        myFerry.setMyPort(null);
        logger.log("Registration in port failed!!!");
    }

    public void informRegisterSuccess(int id, Port port){
        myFerry.setId(id);
        myFerry.setMyPort(port);
        logger.log("Successfully registered in port");
    }

    //endregion

    //region Unregistering

    public void unregisterFromCurrentPort(){
        if(myFerry.getMyPort() != null){
            var unregisterBehaviour = new UnregisterFromPortBehaviour(this);
            addBehaviour(unregisterBehaviour);
        }
    }

    public void finishUnregistering(){
        myFerry.setMyPort(null);
        logger.log("Successfully removed ferry from port");
    }

    //endregion

    //region AddDeparture

    public void addDeparture(DepartureRegistrationData departure){
        if(myFerry.getMyPort() != null){
            Behaviour registerDepartureBehaviour = new RegisterDepartureBehaviour(this, departure);
            addBehaviour(registerDepartureBehaviour);
        }
    }

    //endregion
}
