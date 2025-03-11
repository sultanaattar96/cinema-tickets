package uk.gov.dwp.uc.pairtest.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uk.gov.dwp.uc.pairtest.domain.PurchaseResponse;
import uk.gov.dwp.uc.pairtest.domain.TicketType;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.ticketservice.TicketService;
import uk.gov.dwp.uc.pairtest.ticketservice.TicketServiceImpl;

@Controller
public class TicketController {
	
	// Atomic counter for thread-safe auto-increment
    private static final AtomicLong accountIdCounter = new AtomicLong(1000);
	
    private final TicketService ticketService;
    private final TicketServiceImpl ticketServiceImpl;

    public TicketController(TicketService ticketService, TicketServiceImpl ticketServiceImpl) {
        this.ticketService = ticketService;
        this.ticketServiceImpl = ticketServiceImpl;
    }
    @GetMapping("/")
    public String showForm() {
        return "index";  // Loads the ticket purchase form
    }

    @PostMapping("/purchase")
    public String purchaseTickets(
            @RequestParam int adults, 
            @RequestParam int children, 
            @RequestParam int infants, 
            Model model) {
    	
    	// Generate auto-incremented account ID
        Long accountId = accountIdCounter.incrementAndGet();
        System.out.println("Generated Account ID: " + accountId);

        try {
            //Create ticket requests
            TicketTypeRequest[] requests = {
                new TicketTypeRequest(TicketType.ADULT, adults),
                new TicketTypeRequest(TicketType.CHILD, children),
                new TicketTypeRequest(TicketType.INFANT, infants)
            };

            //call service & get response
            //PurchaseResponse response = ticketService.purchaseTickets(accountId, requests);
            
            // Call service method (does not return data)
            ticketService.purchaseTickets(accountId, requests);
            
            // Retrieve calculated data
            //Using this method as can not change the TicketService, and need data to show on UI.
            PurchaseResponse purchaseData = ticketServiceImpl.getPurchaseResponse(accountId);
            
            model.addAttribute("totalCost", purchaseData.getTotalCost());
            model.addAttribute("totalSeats", purchaseData.getTotalSeats());
            model.addAttribute("accountId", accountId);

            return "result"; // Redirects to the result page
        } catch (InvalidPurchaseException e) {
            model.addAttribute("error", e.getMessage());
            return "index"; // Reload form with error message
        }
    }
}
