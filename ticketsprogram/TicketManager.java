package ticketsprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TicketManager {
    private static TicketManager instance;
    private final TicketFactory ticketFactory;
    // normal tickets are tickets without cve information
    Map<String, List<Ticket>> normalTickets;
    // advanced tickets are tickets with cve information
    Map<String, List<AdvancedTicket>> advancedTickets;
    private int ticketsCounter;
    private final Logger logger;

    private TicketManager() {
        ticketsCounter = 1;
        initTicketsLists();
        ticketFactory = new TicketFactory();
        logger = Logger.getLogger(TicketManager.class.getName());
    }

    private void initTicketsLists() {
        // normal tickets
        normalTickets = new HashMap<>();
        normalTickets.put("Configuration", new ArrayList<>());

        // advanced tickets
        advancedTickets = new HashMap<>();
        advancedTickets.put("Security", new ArrayList<>());
        advancedTickets.put("BestPractice", new ArrayList<>());
    }

    public static TicketManager getInstance() {
        if(instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    private void incrementTicketsCounter() {
        ticketsCounter++;
    }

    public void createNormalTicket(String ticketType, TicketSeverityEnum severity, String title) {
        if(!normalTickets.containsKey(ticketType)) return;

        List<Ticket> tickets = normalTickets.get(ticketType);
        tickets.add(ticketFactory.createNormalTicket(ticketType, ticketsCounter, severity, title));
        incrementTicketsCounter();
    }

    public void createAdvancedTicket(
            String ticketType, TicketSeverityEnum severity, String title, String cveInformation) {
        if(!advancedTickets.containsKey(ticketType)) return;

        List<AdvancedTicket> tickets = advancedTickets.get(ticketType);
        tickets.add(ticketFactory.createAdvancedTicket(ticketType, ticketsCounter, severity, title, cveInformation));
        incrementTicketsCounter();
    }

    public void logSeverityStatistics() {
        for(String ticketType: normalTickets.keySet()) {
            List<Ticket> tempTickets = normalTickets.get(ticketType);
            logger.info("There are ".concat(String.valueOf(tempTickets.size()))
                    .concat(" ").concat(ticketType).concat(" tickets"));
        }

        for(String ticketType: advancedTickets.keySet()) {
            List<AdvancedTicket> tempTickets = advancedTickets.get(ticketType);
            logger.info("There are ".concat(String.valueOf(tempTickets.size()))
                    .concat(" ").concat(ticketType).concat(" tickets"));
        }
    }

    public void logCVEStatistics() {
        Map<String, Integer> cveMap = new HashMap<>();

        for(String ticketType: advancedTickets.keySet()) {
            for(AdvancedTicket ticket: advancedTickets.get(ticketType)) {
                if (!cveMap.containsKey(ticket.getCVEInformation())) {
                    cveMap.put(ticket.getCVEInformation(), 1);
                } else {
                    int tempCount = cveMap.get(ticket.getCVEInformation());
                    cveMap.replace(ticket.getCVEInformation(), tempCount + 1);
                }
            }
        }

        for(String cve: cveMap.keySet()) {
            String countString = String.valueOf(cveMap.get(cve));
            logger.info("There are ".concat(countString).concat(" ").concat(cve).concat(" types"));
        }
    }
}