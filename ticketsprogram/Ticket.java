package ticketsprogram;

import java.util.Date;

public abstract class Ticket {
    private int id;
    private TicketSeverityEnum severity;
    private Date createDate;
    private Date updateDate;
    private String title;

    protected Ticket(int id, TicketSeverityEnum severity, String title) {
        this.id = id;
        this.severity = severity;
        this.createDate = new Date();
        this.updateDate = new Date();
        this.title = title;
    }

    // you can only get ticketId, because it inits on ticket creation
    public int getTicketId() {
        return id;
    }

    public TicketSeverityEnum getSeverity() {
        return severity;
    }

    public void setSeverity(TicketSeverityEnum severity) {
        this.severity = severity;
    }

    // you can only get createDate, because it inits on ticket creation
    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date date) {
        this.updateDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
