package ticketsprogram;

public class BestPracticeTicket extends AdvancedTicket {
    protected BestPracticeTicket(int id, TicketSeverityEnum severity, String title, String cveInformation) {
        super(id, severity, title, cveInformation);
    }
}