package FerrySystem.Client.behaviours;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.DepartureInfo;
import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;
import com.fasterxml.jackson.core.type.TypeReference;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.Vector;

public class AskScheduleBehaviour extends AskAndWaitBehaviour {
    private CarAgent myCarAgent;

    public AskScheduleBehaviour(CarAgent a) {
        super(a);
        this.myCarAgent = a;
    }

    @Override
    protected ACLMessage prepareMessage() {
        var message = new ACLMessage(ACLMessage.REQUEST);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_SCHEDULE);
        message.addReceiver(myCarAgent.getMyCar().getPort().getAgentAID());

        return message;
    }

    @Override
    public void onMessageReceived(ACLMessage received) {
        if(received.getPerformative() == ACLMessage.INFORM){
            try {
                var content = received.getContent();
                Vector<DepartureInfo> departureInfos = mapper.readValue(content,
                        new TypeReference<Vector<DepartureInfo>>(){});

                myCarAgent.getMyCar().setKnownDepartures(departureInfos);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
