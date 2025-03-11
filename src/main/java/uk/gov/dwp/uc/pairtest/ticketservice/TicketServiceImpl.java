package uk.gov.dwp.uc.pairtest.ticketservice;

import uk.gov.dwp.uc.pairtest.domain.PurchaseResponse;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import org.springframework.stereotype.Service;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    private static final int MAX_TICKETS = 25;
    private static final int ADULT_PRICE = 25;
    private static final int CHILD_PRICE = 15;
    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;
    
    // Store last processed data for retrieval by accountId
    // Using this method as can not change the TicketService, and need to send calculated data to controller to show on UI.
    private final ConcurrentHashMap<Long, PurchaseResponse> purchaseDataStore = new ConcurrentHashMap<>();

    public TicketServiceImpl(TicketPaymentService ticketPaymentService, SeatReservationService seatReservationService) {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) {
        //Throws InvalidPurchaseException for invalid account IDs
    	if (accountId == null || accountId <= 0) {
            throw new InvalidPurchaseException("Invalid account ID");
        }

        //Log ticket requests
        Arrays.stream(ticketTypeRequests).forEach(request -> logger.info("Processing: {}", request));

        int totalAmount = 0;
        int totalSeats = 0;
        int adultTickets = 0;

        //Clear Error Handling
        //Throws InvalidPurchaseException for invalid no adult tickets, or exceeding the ticket limit.
        for (TicketTypeRequest request : ticketTypeRequests) {
            switch (request.getTicketType()) {
                case ADULT:
                    totalAmount += request.getQuantity() * ADULT_PRICE;
                    totalSeats += request.getQuantity();
                    adultTickets += request.getQuantity();
                    break;
                case CHILD:
                    if (adultTickets == 0) throw new InvalidPurchaseException("Child tickets cannot be purchased without an adult ticket");
                    totalAmount += request.getQuantity() * CHILD_PRICE;
                    totalSeats += request.getQuantity();
                    break;
                case INFANT:
                    if (adultTickets == 0) throw new InvalidPurchaseException("Infant tickets cannot be purchased without an adult ticket");
                    break;
            }
        }

        if (totalSeats > MAX_TICKETS) {
            throw new InvalidPurchaseException("Cannot purchase more than 25 tickets at a time");
        }

        //Log calculated values
        logger.info("Total amount to pay: £{}", totalAmount);
        logger.info("Total seats to reserve: {}", totalSeats);

        //Process payment and seat reservation
        ticketPaymentService.makePayment(accountId, totalAmount);
        seatReservationService.reserveSeat(accountId, totalSeats);

        //return new PurchaseResponse(totalAmount, totalSeats);
        
        //Store the calculated data
        purchaseDataStore.put(accountId, new PurchaseResponse(totalAmount, totalSeats));
    }
    
    public PurchaseResponse getPurchaseResponse(Long accountId) {
        return purchaseDataStore.get(accountId);
    }
}
