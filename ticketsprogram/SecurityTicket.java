package ticketsprogram;

public class SecurityTicket extends AdvancedTicket {
    protected SecurityTicket(int id, TicketSeverityEnum severity, String title, String cveInformation) {
        super(id, severity, title, cveInformation);
    }
}
