package FerrySystem.Commons;

import jade.core.AID;

public class Ferry {
    private int id;
    private int capacity;
    private AID agentAID;
    private Port myPort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public AID getAgentAID() {
        return agentAID;
    }

    public void setAgentAID(AID agentAID) {
        this.agentAID = agentAID;
    }

    public Port getMyPort() {
        return myPort;
    }

    public void setMyPort(Port myPort) {
        this.myPort = myPort;
    }
}
