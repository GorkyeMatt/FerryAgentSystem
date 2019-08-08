package FerrySystem.Commons.data;

import jade.core.AID;

public class Car {
    private AID agentAID;
    private double length;
    private double mass;
    private VehicleType type;
    private String registration;

    public AID getAgentAID() {
        return agentAID;
    }

    public void setAgentAID(AID agentAID) {
        this.agentAID = agentAID;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
