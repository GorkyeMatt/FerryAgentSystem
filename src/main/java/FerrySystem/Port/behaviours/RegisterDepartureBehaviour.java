package FerrySystem.Port.behaviours;

import FerrySystem.Commons.Defines;
import FerrySystem.Commons.DepartureInfo;
import FerrySystem.Commons.DepartureRegistrationData;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.behaviours.CyclicMessageReceiveBehaviour;
import FerrySystem.Port.PortAgent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class RegisterDepartureBehaviour extends CyclicMessageReceiveBehaviour {

    private PortAgent myPortAgent;
    private ObjectMapper mapper;

    public RegisterDepartureBehaviour(PortAgent agent) {
        super(agent);
        myPortAgent = agent;
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void prepareMessageTemplate() {
        messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchOntology(Defines.FERRY_SYSTEM_ONTOLOGY_DEPARTURE_REGISTER),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
    }

    @Override
    public void onMessageReceived(ACLMessage message) {
        logger.logReceived(message);
        var port = myPortAgent.getMyPort();

        var content = message.getContent();
        try {
            var departureRegistrationData = mapper.readValue(content, DepartureRegistrationData.class);
            var ferryAID = departureRegistrationData.getFerryAID().getName();

            boolean isFerryRegistered = false;
            for(Ferry ferry: port.getRegisteredFerries()){
                if(ferry.getAgentAID().getName().compareTo(ferryAID) == 0)
                    isFerryRegistered = true;
            }

            var reply = message.createReply();
            if(isFerryRegistered){
                var departures = port.getRegisteredDepartures();
                var id = departures.size();
                var departure = new DepartureInfo(id, departureRegistrationData);
                departures.add(departure);

                reply.setPerformative(ACLMessage.AGREE);
                reply.setContent("" + id);
            }
            else{
                reply.setPerformative(ACLMessage.REFUSE);
            }

            myPortAgent.send(reply);
            logger.logSend(reply);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
