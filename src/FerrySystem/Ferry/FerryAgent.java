package FerrySystem.Ferry;

import FerrySystem.Commons.*;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.SimpleLogger;
import FerrySystem.Ferry.behaviours.register.RegisterInPortBehaviour;
import jade.core.AID;
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

    @Override
    protected void setup() {
        super.setup();
        logger.setAgentName(this.getName());
        logger.log("Ferry is ready");

        myFerry = new Ferry();
        myFerry.setAgentAID(this.getAID());

        var port = new Port();
        port.setAgentAID(new AID("port", AID.ISLOCALNAME));
        registerInPort(port);
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
}
