package FerrySystem.Ferry.behaviours;

import FerrySystem.Commons.*;
import FerrySystem.Ferry.FerryAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConfirmRegistrationBehaviour extends OneShotBehaviour {

    private FerryAgent myFerryAgent;
    private Port port;

    public ConfirmRegistrationBehaviour(FerryAgent agent, Port port) {
        super(agent);
        this.myFerryAgent = agent;
        this.port = port;
    }

    @Override
    public void action() {

        var template = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));

        var message = myAgent.receive(template);
        var content = message.getContent();

        if(content.compareTo(Defines.FERRY_SYSTEM_REGISTRATION_REJECTED) == 0){
            myFerryAgent.informRegisterFailure();
        }
        else{
            var id = Integer.parseInt(content);
            myFerryAgent.informRegisterSuccess(id, port);
        }
    }
}
