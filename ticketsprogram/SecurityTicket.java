package ticketsprogram;

import java.util.Date;

public class SecurityTicket extends AdvancedTicket {
    private String cveInformation;

    protected SecurityTicket(int id, TicketSeverityEnum severity, String title, String cveInformation) {
        super(id,severity, title);
        this.cveInformation = cveInformation;
    }

    public String getCVEInformation() {
        return cveInformation;
    }

    public void setCVEInformation(String cveInformation) {
        this.cveInformation = cveInformation;
    }
}
