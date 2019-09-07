package FerrySystem.Ferry.behaviours.registerFerry;

import FerrySystem.Commons.*;
import FerrySystem.Commons.data.Port;
import FerrySystem.Commons.helpers.behaviours.OneMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConfirmRegistrationBehaviour
        extends OneMessageReceiveBehaviour {

    private FerryAgent myFerryAgent;
    private Port port;

    ConfirmRegistrationBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
    }

    private MessageTemplate template =  MessageTemplate
            .MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER);

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        myFerryAgent.getLogger().log(received);

        var content = received.getContent();

        if(received.getPerformative() != ACLMessage.AGREE){
            myFerryAgent.informRegisterFailure();
        } else{
            var id = Integer.parseInt(content);
            myFerryAgent.informRegisterSuccess(id, port);
        }
    }
}
