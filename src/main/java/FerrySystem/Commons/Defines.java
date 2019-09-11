package FerrySystem.Commons;

import java.time.Duration;
import java.time.Period;

public class Defines {
    //main ontology name
    public static final String FERRY_SYSTEM_ONTOLOGY = "ferry-system-ontology";

    public static final String FERRY_SYSTEM_ONTOLOGY_FERRY_REGISTER = "ferry-system-ontology/ferry-register";
    public static final String FERRY_SYSTEM_ONTOLOGY_FERRY_UNREGISTER = "ferry-system-ontology/ferry-unregister";
    public static final String FERRY_SYSTEM_ONTOLOGY_DEPARTURE_REGISTER = "ferry-system-ontology/departure-register";
    public static final String FERRY_SYSTEM_ONTOLOGY_DEPARTURE_UNREGISTER = "ferry-system-ontology/departure-unregister";
    public static final String FERRY_SYSTEM_ONTOLOGY_WEATHER = "ferry-system-ontology/weather";
    public static final String FERRY_SYSTEM_ONTOLOGY_WEATHER_PORT = "ferry-system-ontology/weather-port";
    public static final String FERRY_SYSTEM_ONTOLOGY_ASK_FERRIES = "ferry-system-ontology/ask-ferries";
    public static final String FERRY_SYSTEM_ONTOLOGY_ASK_PLACE = "ferry-system-ontology/ask-place";
    public static final String FERRY_SYSTEM_ONTOLOGY_NEGOTIATION = "ferry-system-ontology/negotiation-loop";
    public static final String FERRY_SYSTEM_ONTOLOGY_ASK_SCHEDULE = "ferry-system-ontology/ask-schedule";


    public static final Duration MAX_TIME_DIFFERENCE = Duration.ofSeconds(15 * 60);
}
