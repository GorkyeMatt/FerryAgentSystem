package FerrySystem.Commons;

import jade.core.AID;

import java.util.Hashtable;
import java.util.Vector;

public class Ferry {
    private int id;
    private int capacity;
    private AID agentAID;
    private Port myPort;
    private Hashtable<Integer, DepartureInfo> departureInfos = new Hashtable<>();
    private Vector<RegisteredCar> registeredCars = new Vector<>();

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

    public Hashtable<Integer, DepartureInfo> getDepartureInfos() {
        return departureInfos;
    }

    public void setDepartureInfos(Hashtable<Integer, DepartureInfo> departureInfos) {
        this.departureInfos = departureInfos;
    }

    public Vector<RegisteredCar> getRegisteredCars() {
        return registeredCars;
    }

    public void setRegisteredCars(Vector<RegisteredCar> registeredCars) {
        this.registeredCars = registeredCars;
    }
}
