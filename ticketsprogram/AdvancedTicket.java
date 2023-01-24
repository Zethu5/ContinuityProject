package ticketsprogram;

public abstract class AdvancedTicket extends Ticket {
    private String cveInformation;

    protected AdvancedTicket(int id, TicketSeverityEnum severity, String title, String cveInformation) {
        super(id, severity, title);
        this.cveInformation = cveInformation;
    }

    public String getCVEInformation() {
        return cveInformation;
    }

    public void setCVEInformation(String cveInformation) {
        this.cveInformation = cveInformation;
    }
}
