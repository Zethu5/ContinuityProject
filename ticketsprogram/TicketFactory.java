package ticketsprogram;

public class TicketFactory {
    public Ticket createNormalTicket(String ticketType, int id, TicketSeverityEnum severity, String title) {
        /*
        keeping it as switch case because maybe in the future other types of normal tickets would be added
         */
        switch(ticketType) {
            case "Configuration" -> {return new ConfigurationTicket(id, severity, title);}
            default -> {return null;}
        }
    }

    public AdvancedTicket createAdvancedTicket(
            String ticketType, int id, TicketSeverityEnum severity, String title, String cveInformation) {
        switch(ticketType) {
            case "Security" -> {return new SecurityTicket(id, severity, title, cveInformation);}
            case "BestPractice" -> {return new BestPracticeTicket(id, severity, title, cveInformation);}
            default -> {return null;}
        }
    }
}