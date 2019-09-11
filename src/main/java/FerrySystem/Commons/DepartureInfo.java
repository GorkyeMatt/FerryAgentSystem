package FerrySystem.Commons;

import FerrySystem.Commons.helpers.LocalDateDeserializer;
import FerrySystem.Commons.helpers.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jade.core.AID;

import java.time.LocalDateTime;

public class DepartureInfo {

    public int Id;
    public AID ferryAID;
    public String from;
    public String to;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDateTime time;

    public DepartureInfo() { }

    public DepartureInfo(int id, DepartureRegistrationData registrationData) {
        this.Id = id;
        this.ferryAID = registrationData.getFerryAID();
        this.from = registrationData.getFrom();
        this.to = registrationData.getTo();
        this.time = registrationData.getDate();
    }
}
