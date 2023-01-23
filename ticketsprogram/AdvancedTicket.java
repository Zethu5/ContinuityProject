package ticketsprogram;

import java.util.Date;

public class AdvancedTicket implements Ticket {
    private int id;
    private TicketSeverityEnum severity;
    private Date createDate;
    private Date updateDate;
    private String title;

    protected AdvancedTicket(int id, TicketSeverityEnum severity, String title) {
        this.id = id;
        this.severity = severity;
        this.createDate = new Date();
        this.updateDate = new Date();
        this.title = title;
    }

    @Override
    public int getTicketId() {
        return id;
    }

    @Override
    public void setTicketId(int id) {
        this.id = id;
    }

    @Override
    public TicketSeverityEnum getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(TicketSeverityEnum severity) {
        this.severity = severity;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date date) {
        this.createDate = date;
    }

    @Override
    public Date getUpdateDate() {
        return this.updateDate;
    }

    @Override
    public void setUpdateDate(Date date) {
        this.updateDate = date;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
