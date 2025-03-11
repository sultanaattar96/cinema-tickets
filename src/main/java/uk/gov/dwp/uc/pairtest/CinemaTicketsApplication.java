package uk.gov.dwp.uc.pairtest;

import org.springframework.boot.SpringApplication;   
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketType;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.ticketservice.*;*/

@SpringBootApplication
public class CinemaTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaTicketsApplication.class, args);
		System.out.println("Hi Sultana!");
		
		/*
		 *I was using this part for testing!
		 *
		 * TicketPaymentService paymentService = (accountId, amount) -> 
        System.out.println("Payment Successful: Account " + accountId + " charged £" + amount);
    
	    SeatReservationService reservationService = (accountId, totalSeats) -> 
	    System.out.println("Seat Reservation Successful: " + totalSeats + " seats reserved for account " + accountId);
	
	    TicketService ticketService = new TicketServiceImpl(paymentService, reservationService);
	
	    try {
	        TicketTypeRequest adult = new TicketTypeRequest(TicketType.ADULT, 1);
	        TicketTypeRequest child = new TicketTypeRequest(TicketType.CHILD, 1);
	        ticketService.purchaseTickets(1L, adult, child);
	        System.out.println("Valid purchase successful!");
	    } catch (IllegalArgumentException e) {
	        System.err.println("Exception caught *****: " + e.getMessage());
	    }
	
	    try {
	        TicketTypeRequest invalidChild = new TicketTypeRequest(TicketType.CHILD, 1);
	        ticketService.purchaseTickets(1L, invalidChild);
	        System.out.println("This should NOT be printed!");
	    } catch (IllegalArgumentException e) {
	        System.err.println("Exception caught ***** Testing: " + e.getMessage());
	    }*/
	}
	
}
