package FerrySystem.Port.Behaviours;

import FerrySystem.Port.PortAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformWeatherBehaviour extends SimpleBehaviour {

    private PortAgent myPortAgent;

    public InformWeatherBehaviour(PortAgent a) {
        super(a);
        myPortAgent = a;
    }

    @Override
    public void action() {

        var template = MessageTemplate.and(MessageTemplate.and(
                MessageTemplate.MatchOntology(""),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM)),
                MessageTemplate.MatchContent(""));

        var message = myAgent.receive(template);

        //process message;

        var response = message.createReply();
        myAgent.send(response);

    }

    @Override
    public boolean done() {
        return false;
    }
}
