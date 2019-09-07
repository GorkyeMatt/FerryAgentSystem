package FerrySystem.Commons;

import jade.core.AID;

public class RegisteredCar {
    public int DepartureId;
    public AID clientAid;

    public RegisteredCar(){}

    public RegisteredCar(int departureId, AID clientAid) {
        DepartureId = departureId;
        this.clientAid = clientAid;
    }
}
