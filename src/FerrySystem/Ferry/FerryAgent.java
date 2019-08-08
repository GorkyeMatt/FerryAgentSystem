package FerrySystem.Ferry;

import FerrySystem.Commons.data.Ferry;
import FerrySystem.Commons.data.Port;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.SimpleLogger;
import FerrySystem.Ferry.behaviours.registerDeparture.RegisterDepartureBehaviour;
import FerrySystem.Ferry.behaviours.registerFerry.RegisterInPortBehaviour;
import FerrySystem.Ferry.behaviours.unregister.*;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class FerryAgent extends Agent {
    //region Fields

    private Ferry myFerry;
    private Logger logger = new SimpleLogger();

    //endregion

    //region Getters and setters

    public Ferry getFerry() {
        return myFerry;
    }

    public Logger getLogger() {
        return logger;
    }

    //endregion

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
    }

    //endregion

    //region Registration in port management

    //region Register in port

    public void registerInPort(Port port){
        logger.log("registerFerry in port: " + port);
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

    //endregion

    //region Departure management

    public void addDeparture(Object departure){
        if(myFerry.getMyPort() != null){
            Behaviour registerDepartureBehaviour = new RegisterDepartureBehaviour(this);
            addBehaviour(registerDepartureBehaviour);
        }
    }

    //endregion
}
