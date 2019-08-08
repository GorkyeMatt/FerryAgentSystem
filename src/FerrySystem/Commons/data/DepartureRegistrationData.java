package FerrySystem.Commons.data;

import jade.core.AID;

import java.util.Date;

public class DepartureRegistrationData {
    private AID ferryAID;
    private int ferryId;
    private String from;
    private String to;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
