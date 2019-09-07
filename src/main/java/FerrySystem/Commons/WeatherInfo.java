package FerrySystem.Commons;

import java.util.Objects;

public class WeatherInfo
{
    public int WindSpeed;
    public boolean IsRaining;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInfo that = (WeatherInfo) o;
        return WindSpeed == that.WindSpeed &&
                IsRaining == that.IsRaining;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(WindSpeed, IsRaining);
    }
}