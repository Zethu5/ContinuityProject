package ticketsprogram;

import java.util.Date;

public interface Ticket {
    int getTicketId();
    void setTicketId(int id);
    TicketSeverityEnum getSeverity();
    void setSeverity(TicketSeverityEnum severity);
    Date getCreateDate();
    void setCreateDate(Date date);
    Date getUpdateDate();
    void setUpdateDate(Date date);
    String getTitle();
    void setTitle(String title);
}
