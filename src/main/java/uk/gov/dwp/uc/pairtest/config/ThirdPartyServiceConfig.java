package uk.gov.dwp.uc.pairtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thirdparty.paymentgateway.*;
import thirdparty.seatbooking.*;

@Configuration
public class ThirdPartyServiceConfig {

    @Bean
    public TicketPaymentService ticketPaymentService() {
        return (accountId, amount) -> 
            System.out.println("Payment processed: Account " + accountId + " charged £" + amount);
    }

    @Bean
    public SeatReservationService seatReservationService() {
        return (accountId, totalSeats) -> 
            System.out.println("Seat reservation successful: " + totalSeats + " seats reserved for account " + accountId);
    }
}
