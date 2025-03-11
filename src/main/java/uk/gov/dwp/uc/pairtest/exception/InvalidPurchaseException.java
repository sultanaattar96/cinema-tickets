package uk.gov.dwp.uc.pairtest.exception;

public class InvalidPurchaseException extends RuntimeException {
	private static final long serialVersionUID = 1L; //added to ignore warning but not needed.
    public InvalidPurchaseException(String message) {
        super(message);
    }
}
