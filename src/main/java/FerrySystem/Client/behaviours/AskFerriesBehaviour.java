package FerrySystem.Client.behaviours;

import FerrySystem.Client.CarAgent;
import FerrySystem.Commons.Car;
import FerrySystem.Commons.Defines;
import FerrySystem.Commons.Ferry;
import FerrySystem.Commons.helpers.BasicAgent;
import FerrySystem.Commons.helpers.behaviours.AskAndWaitBehaviour;

import com.fasterxml.jackson.core.type.TypeReference;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.List;

public class AskFerriesBehaviour extends AskAndWaitBehaviour
{
    private CarAgent myCarAgent;
    private AID aid;

    public AskFerriesBehaviour(CarAgent a, AID aid){
        super(a);

        this.myCarAgent = a;
        this.aid = aid;
    }

    @Override
    public void prepareMessage()
    {
        message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology(Defines.FERRY_SYSTEM_ONTOLOGY_ASK_FERRIES);

        message.addReceiver(aid);
    }

    @Override
    public void onMessageReceived(ACLMessage received)
    {
        myBasicAgent.getLogger().log("Received answer for ask ferries");

        try {
            var json = received.getContent();

            List<Ferry> ferries = mapper.readValue(json, new TypeReference<List<Ferry>>(){});

            if(ferries!= null && ferries.size()>0)
            myCarAgent.getMyCar().setMyFerry(ferries.get(0));
        }
        catch (IOException e){

        }

    }
}
