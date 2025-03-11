package uk.gov.dwp.uc.pairtest.ticketservice;

import uk.gov.dwp.uc.pairtest.domain.PurchaseResponse;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public interface TicketService {

    PurchaseResponse purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException;

	//int calculateSeats(TicketRequestDTO request);

	//int calculateTotalCost(TicketRequestDTO request);

}
