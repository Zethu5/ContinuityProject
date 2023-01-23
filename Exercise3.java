import ticketsprogram.TicketManager;
import ticketsprogram.TicketSeverityEnum;

import java.util.Random;
import java.util.logging.Logger;

public class Exercise3 {
    public static void main(String[] args) {
        TicketManager ticketManager = TicketManager.getInstance();
        String[] ticketTypes = {"Security", "Configuration", "BestPractice"};
        String[] cveTypes = {"001", "002", "003", "004", "005", "006", "007", "008", "009", "010"};
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
                case "Security" -> {
                    ticketManager.createSecurityTicket(
                            severity,
                            "Security ".concat(String.valueOf(i)),
                            "CVE-2023-".concat(cveType)
                    );
                }
                case "Configuration" -> {
                    ticketManager.createConfigurationTicket(
                            severity,
                            "Configuration ".concat(String.valueOf(i))
                    );
                }
                case "BestPractice" -> {
                    ticketManager.createBestPracticeTicket(
                            severity,
                            "BestPractice ".concat(String.valueOf(i)),
                            "CVE-2023-".concat(cveType)
                    );
                }
            }
        }

        ticketManager.logSeverityStatistics();
        ticketManager.logCVEStatistics();
    }
}
