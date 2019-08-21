package FerrySystem.Ferry;

import FerrySystem.Commons.*;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.SimpleLogger;
import FerrySystem.Ferry.behaviours.register.RegisterInPortBehaviour;
import FerrySystem.Ferry.behaviours.unregister.*;
import FerrySystem.Ferry.behaviours.weather.AskWeatherBehaviour;
import jade.core.Agent;

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

        var listen = new AskWeatherBehaviour(this);
        addBehaviour(listen);
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
}
