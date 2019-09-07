package FerrySystem.Commons;

import jade.core.AID;

import java.time.LocalDateTime;
import java.util.Vector;

public class Car {

    //Fields

    private AID agentAID;
    private WeatherInfo weather;
    private Ferry myFerry;
    private DepartureInfo departureInfo;

    private DepartureInfo actuallyRegister;
    private LocalDateTime estimatedArrivalTime;
    private LocalDateTime departureTime;


    // Get and set

    public AID getAgentAID() {
        return agentAID;
    }

    public void setAgentAID(AID agentAID) {
        this.agentAID = agentAID;
    }

    public void setWeather(WeatherInfo weather)
    {
        this.weather = weather;
    }

    public WeatherInfo getWeather()
    {
        return weather;
    }

    public void setMyFerry(Ferry myFerry) {
        this.myFerry = myFerry;
    }

    public Ferry getMyFerry() {
        return myFerry;
    }

    public DepartureInfo getDepartureInfo() {
        return departureInfo;
    }

    public DepartureInfo getActuallyRegister() {
        return actuallyRegister;
    }

    public void setActuallyRegister(DepartureInfo actuallyRegister) {
        this.actuallyRegister = actuallyRegister;
    }

    public void setDepartureInfo(DepartureInfo departureInfo) {
        this.departureInfo = departureInfo;


    }

    public LocalDateTime getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(LocalDateTime estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}
