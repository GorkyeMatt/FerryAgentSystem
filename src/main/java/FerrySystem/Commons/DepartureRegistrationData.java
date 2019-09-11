package FerrySystem.Commons;

import FerrySystem.Commons.helpers.LocalDateDeserializer;
import FerrySystem.Commons.helpers.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jade.core.AID;
import java.time.LocalDateTime;

public class DepartureRegistrationData {
    private AID ferryAID;
    private int ferryId;
    private String from;
    private String to;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime date;

    public AID getFerryAID() {
        return ferryAID;
    }

    public void setFerryAID(AID ferryAID) {
        this.ferryAID = ferryAID;
    }

    public int getFerryId() {
        return ferryId;
    }

    public void setFerryId(int ferryId) {
        this.ferryId = ferryId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
