package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.ticketservice.TicketService;
import uk.gov.dwp.uc.pairtest.ticketservice.TicketServiceImpl;

import static org.mockito.Mockito.*;

import uk.gov.dwp.uc.pairtest.domain.TicketType;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CinemaTicketsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private TicketPaymentService paymentService;
    private SeatReservationService reservationService;
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        paymentService = mock(TicketPaymentService.class);
        reservationService = mock(SeatReservationService.class);
        ticketService = new TicketServiceImpl(paymentService, reservationService);
    }

    @Test
    void shouldProcessValidPurchase() {
        TicketTypeRequest adult = new TicketTypeRequest(TicketType.ADULT, 2);
        TicketTypeRequest child = new TicketTypeRequest(TicketType.CHILD, 1);
        
        ticketService.purchaseTickets(1L, adult, child);

        verify(paymentService).makePayment(1L, 65);
        verify(reservationService).reserveSeat(1L, 3);
    }

    @Test
    void shouldThrowExceptionForInvalidAccount() {
        TicketTypeRequest adult = new TicketTypeRequest(TicketType.ADULT, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.purchaseTickets(0L, adult);
        });

        assertEquals("Invalid account ID", exception.getMessage());
    }

    @Test
    void shouldRejectChildTicketWithoutAdult() {
        TicketTypeRequest child = new TicketTypeRequest(TicketType.CHILD, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.purchaseTickets(1L, child);
        });

        assertEquals("Child tickets cannot be purchased without an adult ticket", exception.getMessage());
    }

    @Test
    void shouldRejectMoreThan25Tickets() {
        TicketTypeRequest adult = new TicketTypeRequest(TicketType.ADULT, 26);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.purchaseTickets(1L, adult);
        });

        assertEquals("Cannot purchase more than 25 tickets at a time", exception.getMessage());
    }
    
    

}
