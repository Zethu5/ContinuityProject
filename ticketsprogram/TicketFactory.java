package ticketsprogram;

public class TicketFactory {
    protected Ticket createTicket(String ticketType, int id, TicketSeverityEnum severity, String title) {
        if (ticketType.equals("Configuration")) {
            return new ConfigurationTicket(id, severity, title);
        }
        return null;
    }

    protected Ticket createTicket(String ticketType, int id, TicketSeverityEnum severity,
                               String title, String cveInformation) {
        switch (ticketType) {
            case "Security" -> {
                return new SecurityTicket(id, severity, title, cveInformation) {
                };
            }
            case "BestPractice" -> {
                return new BestPracticeTicket(id, severity, title, cveInformation);
            }
            default -> {
                return null;
            }
        }
    }
}
