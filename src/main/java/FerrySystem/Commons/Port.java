package FerrySystem.Commons;

import jade.core.AID;

import java.util.Vector;

public class Port {
    private int id;
    private String name;
    private String address;
    private AID agentAID;
    private WeatherInfo weather;

    private Vector<Ferry> registeredFerries = new Vector<>();
    private Vector<DepartureInfo> registeredDepartures = new Vector<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AID getAgentAID() {
        return agentAID;
    }

    public void setAgentAID(AID agentAID) {
        this.agentAID = agentAID;
    }

    public Vector<Ferry> getRegisteredFerries() {
        return registeredFerries;
    }

    public void setRegisteredFerries(Vector<Ferry> registeredFerries) {
        this.registeredFerries = registeredFerries;
    }

    public void setWeather (WeatherInfo weather) {this.weather = weather;}

    public WeatherInfo getWeather () {return weather;}

    public Vector<DepartureInfo> getRegisteredDepartures() {
        return registeredDepartures;
    }

    public void setRegisteredDepartures(Vector<DepartureInfo> registeredDepartures) {
        this.registeredDepartures = registeredDepartures;
    }
}
