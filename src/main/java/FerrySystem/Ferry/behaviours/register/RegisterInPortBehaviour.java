//package FerrySystem.Ferry.behaviours.register;
//
//import FerrySystem.Commons.Defines;
//import FerrySystem.Commons.Port;
//import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
//import FerrySystem.Ferry.FerryAgent;
//
//import jade.lang.acl.ACLMessage;
//
//public class RegisterInPortBehaviour extends AskAndWaitBehaviour {
//
//    private FerryAgent myFerryAgent;
//    private Port port;
//
//    public RegisterInPortBehaviour(FerryAgent agent, Port port) {
//        super(agent);
//        this.myFerryAgent = agent;
//        this.port = port;
//    }
//
//    @Override
//    protected void prepareMessage() {
//        message = new ACLMessage(ACLMessage.REQUEST);
//        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER);
//        message.addReceiver(port.getAgentAID());
//
//        var ferry = myFerryAgent.getFerry();
//        var serialized = jsonSerializer.serialize(ferry);
//        message.setContent(serialized);
//    }
//
//    @Override
//    public void onMessageSending() {
//        myFerryAgent.getLogger().log("Sent registration message");
//    }
//
//    @Override
//    public void onMessageReceived(ACLMessage received) {
//        var content = received.getContent();
//
//        if (received.getPerformative() != ACLMessage.AGREE) {
//            myFerryAgent.informRegisterFailure();
//        }
//        else {
//            var id = Integer.parseInt(content);
//            myFerryAgent.informRegisterSuccess(id, port);
//        }
//    }
//}
//


package FerrySystem.Ferry.behaviours.register;

import FerrySystem.Commons.Port;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.SequentialBehaviour;

public class RegisterInPortBehaviour extends SequentialBehaviour {

    private FerryAgent myFerryAgent;
    private Port port;

    public RegisterInPortBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
        this.prepare();
    }

    private void prepare(){
        var sendRegistration = new SendRegistrationMessageBehaviour(myFerryAgent, port);
        addSubBehaviour(sendRegistration);

        var confirmRegistration = new ConfirmRegistrationBehaviour(myFerryAgent, port);
        addSubBehaviour(confirmRegistration);
    }
}