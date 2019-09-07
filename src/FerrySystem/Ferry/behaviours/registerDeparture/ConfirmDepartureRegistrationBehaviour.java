package FerrySystem.Ferry.behaviours.registerDeparture;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.helpers.behaviours.OneMessageReceiveBehaviour;
import FerrySystem.Ferry.FerryAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConfirmDepartureRegistrationBehaviour
        extends OneMessageReceiveBehaviour {

    private FerryAgent myFerryAgent;

    ConfirmDepartureRegistrationBehaviour(FerryAgent agent) {
        super(agent);
        this.myFerryAgent = agent;
    }

    private MessageTemplate template = MessageTemplate
            .MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_DEPARTURE_REGISTER);

    @Override
    public MessageTemplate messageTemplate() {
        return template;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        myFerryAgent.getLogger().log(received);

        var content = received.getContent();

        if(received.getPerformative() == ACLMessage.AGREE){
            var id = Integer.parseInt(content);
            //todo success(id)
        }
        else{
            //todo failure
        }
    }


}
