package FerrySystem.Ferry.behaviours.negotiations;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.RegisteredCar;
import FerrySystem.Commons.helpers.Logger;
import FerrySystem.Ferry.FerryAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Vector;

public class StartNegotiationBehaviour extends SimpleBehaviour {

    private FerryAgent myFerryAgent;
    private int id;
    private LocalDateTime proposedTime;
    private LocalDateTime clientTime;
    private ACLMessage response;

    private Vector<AID> registered = new Vector<>();
    private boolean sent;
    private ACLMessage message;

    private boolean finished;
    private int repliesCount;
    private boolean allAgreed;
    private LocalDateTime notAcceptedTime;

    private MessageTemplate messageTemplate;

    private Logger logger;

    StartNegotiationBehaviour(FerryAgent myFerryAgent, int id, LocalDateTime clientTime, ACLMessage response) {
        super(myFerryAgent);
        this.myFerryAgent = myFerryAgent;
        this.id = id;
        this.clientTime = clientTime;
        this.response = response;
        this.proposedTime = clientTime;
        this.sent = false;
        this.finished = false;

        this.logger = myFerryAgent.getLogger();

        this.messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_NEGOTIATION),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
    }

    @Override
    public void onStart() {
        logger.log("Starting Negotiations");

        message = new ACLMessage(ACLMessage.PROPOSE);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_NEGOTIATION);
        for (var registeredCar: myFerryAgent.getFerry().getRegisteredCars()) {
            if(registeredCar.DepartureId == id) {
                registered.add(registeredCar.clientAid);
                message.addReceiver(registeredCar.clientAid);
            }
        }
    }

    @Override
    public void action() {
        if(registered.size() == 0){
            finished = true;
            return;
        }

        if(!sent){
            repliesCount = 0;
            allAgreed = true;
            sent = true;
            message.setContent("" + proposedTime);
            myAgent.send(message);
            logger.logSend(message);
        }
        else{
            CollectReplies();

            if(repliesCount == registered.size()){
                if(allAgreed){
                    finished = true;
                }
                else{
                    proposedTime = notAcceptedTime;
                    sent = false;
                }
            }
        }

    }

    private void CollectReplies(){
        var message = myAgent.receive(messageTemplate);
        if (message != null){
            logger.logReceived(message);

            repliesCount++;
            var content = message.getContent();
            var time = LocalDateTime.parse(content);
            if(Duration.between(time, proposedTime).getSeconds() != 0){
                allAgreed = false;
                notAcceptedTime = time; //todo
            }
        }
        else{
            block();
        }
    }


    @Override
    public int onEnd() {
        var timeDifference = Duration.between(proposedTime, clientTime);
        if(timeDifference.getSeconds() == 0){
            var registeredCar = new RegisteredCar();
            registeredCar.clientAid = message.getSender();
            registeredCar.DepartureId = id;
            myFerryAgent.getFerry().getRegisteredCars().add(registeredCar);

            response.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }
        else{
            response.setPerformative(ACLMessage.INFORM);
            response.setContent("" + proposedTime);
        }

        myAgent.send(response);
        logger.logSend(response);
        return 0;
    }

    @Override
    public boolean done() {
        return finished;
    }
}
