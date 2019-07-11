package FerrySystem.Ferry;

import FerrySystem.Commons.*;
import FerrySystem.Ferry.behaviours.RegisterInPortBehaviour;
import jade.core.Agent;

public class FerryAgent extends Agent {
    private Ferry myFerry;

    @Override
    protected void setup() {
        super.setup();
        myFerry = new Ferry();
    }

    public void registerInPort(Port port){
        var registerBehaviour = new RegisterInPortBehaviour(this, port);
        addBehaviour(registerBehaviour);
    }

    public void informRegisterFailure(){}
    public void informRegisterSuccess(int id, Port port){
        myFerry.setId(id);
        myFerry.setMyPort(port);
    }
}
