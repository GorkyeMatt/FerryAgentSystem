package FerrySystem.Commons;

import jade.core.AID;

public class Car {
    private AID agentAID;
    private WeatherInfo weather;

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
}
