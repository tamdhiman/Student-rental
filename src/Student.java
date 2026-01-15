/*

 * Student
 * 
 * 14-01-2026
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Student extends User {

    /* STUDENT ATTRIBUTES */
    private final List<RentalRequest> rentalRequests = new ArrayList<>();
    private final List<RentalBooking> rentalHistory = new ArrayList<>();

    /* STUDENT CONSTRUCTOR */

    public Student(int userId, String name, String email, String contactNumber, String password, Map<String, String> securityAnswers) {
        super(userId, name, email, contactNumber, password, securityAnswers);
    }


    /* HELPER METHODS */

    public void createRequest(RentalListing listing) {
    if (listing == null) {
        throw new IllegalArgumentException("Listing cannot be null");
    }

    int requestId = RentalRequestRepo.generateRequestId();

    RentalRequest request = new RentalRequest(
        requestId,
        this,
        listing,
        listing.getHomeowner()
    );
    rentalRequests.add(request);

    RentalRequestRepo.addRequest(request);
    }

    public void addRentalHistory(RentalBooking booking) {
        rentalHistory.add(booking);
    }

    /* GETTER METHODS */
    public List<RentalRequest> getRentalRequests() {
        return List.copyOf(rentalRequests);
    }

    public List<RentalBooking> getRentalHistory() {
        return List.copyOf(rentalHistory);
    }
}