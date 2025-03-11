package uk.gov.dwp.uc.pairtest.domain;

public class PurchaseResponse {
    private final int totalCost;
    private final int totalSeats;

    public PurchaseResponse(int totalCost, int totalSeats) {
        this.totalCost = totalCost;
        this.totalSeats = totalSeats;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}
