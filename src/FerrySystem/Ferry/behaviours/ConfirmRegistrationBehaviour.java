package FerrySystem.Ferry.behaviours;

import FerrySystem.Commons.*;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Commons.helpers.MessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConfirmRegistrationBehaviour extends MessageReceiveBehaviour {

    private FerryAgent myFerryAgent;
    private Port port;
    private Logger logger;

    private MessageTemplate template =  MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER);

    public ConfirmRegistrationBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
        this.logger = myFerryAgent.getLogger();
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        logger.log(received);

        var content = received.getContent();

        if(received.getPerformative() != ACLMessage.AGREE){
            myFerryAgent.informRegisterFailure();
        } else{
            var id = Integer.parseInt(content);
            myFerryAgent.informRegisterSuccess(id, port);
        }
    }

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }
}
