package ticketsprogram;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TicketManager {
    private static TicketManager instance;
    private TicketFactory ticketFactory;
    private List<SecurityTicket> securityTickets;
    private List<Ticket> configurationTickets;
    private List<BestPracticeTicket> bestPracticeTickets;
    private int ticketsCounter;
    private final Logger logger;

    private TicketManager() {
        ticketsCounter = 1;
        securityTickets = new ArrayList<>();
        configurationTickets = new ArrayList<>();
        bestPracticeTickets = new ArrayList<>();
        ticketFactory = new TicketFactory();
        logger = Logger.getLogger(TicketManager.class.getName());
    }

    public static TicketManager getInstance() {
        if(instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    public int getTicketsCounter() {
        return ticketsCounter;
    }

    private void incrementTicketsCounter() {
        ticketsCounter++;
    }

    public void createConfigurationTicket(TicketSeverityEnum severity, String title) {
        configurationTickets.add(ticketFactory.createTicket("Configuration", ticketsCounter, severity, title));
        incrementTicketsCounter();
    }

    public void createSecurityTicket(TicketSeverityEnum severity, String title, String cveInformation) {
        securityTickets.add((SecurityTicket) ticketFactory.createTicket("Security", ticketsCounter, severity, title, cveInformation));
        incrementTicketsCounter();
    }

    public void createBestPracticeTicket(TicketSeverityEnum severity, String title, String cveInformation) {
        bestPracticeTickets.add((BestPracticeTicket) ticketFactory.createTicket("BestPractice", ticketsCounter, severity, title, cveInformation));
        incrementTicketsCounter();
    }

    public void logSeverityStatistics() {
        logger.info("There are ".concat(String.valueOf(securityTickets.size()))
                .concat(" ").concat("security").concat(" tickets"));
        logger.info("There are ".concat(String.valueOf(configurationTickets.size()))
                .concat(" ").concat("configuration").concat(" tickets"));
        logger.info("There are ".concat(String.valueOf(bestPracticeTickets.size()))
                .concat(" ").concat("best practice").concat(" tickets"));
    }

    public void logCVEStatistics() {
        Map<String, Integer> cveMap = new HashMap<>();
        for(SecurityTicket ticket: securityTickets) {
            if(!cveMap.containsKey(ticket.getCVEInformation())) {
                cveMap.put(ticket.getCVEInformation(), 1);
            } else {
                int tempCount = cveMap.get(ticket.getCVEInformation());
                cveMap.replace(ticket.getCVEInformation(), tempCount + 1);
            }
        }

        for(BestPracticeTicket ticket: bestPracticeTickets) {
            if(!cveMap.containsKey(ticket.getCVEInformation())) {
                cveMap.put(ticket.getCVEInformation(), 1);
            } else {
                int tempCount = cveMap.get(ticket.getCVEInformation());
                cveMap.replace(ticket.getCVEInformation(), tempCount + 1);
            }
        }

        for(String cve: cveMap.keySet()) {
            String countString = String.valueOf(cveMap.get(cve));
            logger.info("There are ".concat(countString).concat(" ").concat(cve).concat(" types"));
        }
    }
}
