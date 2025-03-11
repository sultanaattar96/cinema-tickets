# cinema-tickets
A Java-based ticket purchasing system that calculates payments, reserves seats, and enforces business rules

## Features
- Allows users to purchase Adult, Child, and Infant tickets.
- Ensures compliance with business rules (e.g., infants do not get seats, child tickets require an adult ticket, max 25 tickets per purchase).
- Integrates with **TicketPaymentService** for payment processing.
- Integrates with **SeatReservationService** for seat reservations.
- Handles invalid purchase requests gracefully.
- Tickets available: **Adult (£25), Child (£15), Infant (£0)**
- **Maximum of 25 tickets can be purchased at a time.**
- **Infants do not require a seat.**
- **Child and Infant tickets cannot be purchased without an Adult ticket.**
- A simple UI (optional)

## Project Structure
```
/cinema-tickets
│── src/main/java/uk/gov/dwp/uc/pairtest
│   ├── ticketservice/TicketServiceImpl.java
│   ├── domain/TicketTypeRequest.java
│   ├── domain/PurchaseResponse.java
│   ├── exception/InvalidPurchaseException.java
│   ├── config/ThirdPartyServiceConfig.java
│── src/test/java/uk/gov/dwp/uc/pairtest/ticketservice
│   ├── TicketServiceImplTest.java
│── README.md
```

## Installation and Setup
1. Clone this repository:
   ```bash
   git clone https://github.com/sultanaattar96/cinema-tickets.git
   cd cinema-tickets
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the tests:
   ```bash
   mvn test
   ```
### Running Locally (Spring Boot with Tomcat)
```sh
mvn spring-boot:run
```
Start Tomcat and access the UI.

## Test Cases

| Test Case | Expected Outcome | Status |
|-----------|----------------|--------|
| Purchase 1 Adult Ticket | Payment successful, 1 seat reserved | ✅ |
| Purchase 1 Child Ticket (No Adult) | **Fails** (Child cannot be alone) | ✅ |
| Purchase 2 Adult, 3 Child | Payment successful, 5 seats reserved | ✅ |
| Purchase 1 Infant, 1 Adult | Payment successful, **1 seat reserved (Infant does not get a seat)** | ✅ |
| Purchase 30 Tickets (Exceeds Limit) | **Fails** (Max limit is 25) | ✅ |

## Testing Screenshots

![Home Page](https://github.com/user-attachments/assets/7588a6de-5b16-4443-934f-758b334d8a1c)
![Test Screenshot 1](https://github.com/user-attachments/assets/74d4b2f0-415d-4f52-aa10-7b9b9efa2fad)
![Test Screenshot Purchase 1](https://github.com/user-attachments/assets/d6c9f106-d50a-4d3f-8683-05c6734471dd)

## Author
Sultana Attar
