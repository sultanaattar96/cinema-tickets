package uk.gov.dwp.uc.pairtest.domain;

import java.util.Objects;

/**
 * Immutable Object
 */

public final class TicketTypeRequest {
    private final TicketType ticketType;
    private final int quantity;

    //Just to ensures that ticketType is not null before assign.
    public TicketTypeRequest(TicketType ticketType, int quantity) {
        this.ticketType = Objects.requireNonNull(ticketType, "Ticket type cannot be null");
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getQuantity() {
        return quantity;
    }
    
    //Just adding for a readable representation of the object.
    @Override
    public String toString() {
        return "TicketTypeRequest{ticketType=" + ticketType + ", quantity=" + quantity + "}";
    }
}
