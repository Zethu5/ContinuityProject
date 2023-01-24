import ticketsprogram.TicketManager;
import ticketsprogram.TicketSeverityEnum;

import java.util.Random;

public class Exercise3 {
    public static void main(String[] args) {
        TicketManager ticketManager = TicketManager.getInstance();
        String[] ticketTypes = {"Security", "Configuration", "BestPractice"};
        String[] cveTypes = {"0001", "0002", "0003", "0004", "0005", "0006", "0007", "0008", "0009", "0010"};
        TicketSeverityEnum[] severityTypes = {
                TicketSeverityEnum.Low,
                TicketSeverityEnum.Medium,
                TicketSeverityEnum.High,
                TicketSeverityEnum.Critical
        };

        for(int i = 0; i < 1000; i++) {
            String ticketType = ticketTypes[new Random().nextInt(ticketTypes.length)];
            TicketSeverityEnum severity = severityTypes[new Random().nextInt(severityTypes.length)];
            String cveType = cveTypes[new Random().nextInt(cveTypes.length)];

            switch(ticketType) {
                case "Security" -> ticketManager.createAdvancedTicket(
                        ticketType,
                        severity,
                        "Security ".concat(String.valueOf(i)),
                        "CVE-2023-".concat(cveType)
                );
                case "Configuration" -> ticketManager.createNormalTicket(
                        ticketType,
                        severity,
                        "Configuration ".concat(String.valueOf(i))
                );
                case "BestPractice" -> ticketManager.createAdvancedTicket(
                        ticketType,
                        severity,
                        "BestPractice ".concat(String.valueOf(i)),
                        "CVE-2023-".concat(cveType)
                );
            }
        }

        ticketManager.logSeverityStatistics();
        ticketManager.logCVEStatistics();
    }
}
