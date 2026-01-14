import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Student extends User {
    private final List<RentalRequest> rentalRequests = new ArrayList<>();
    private final List<RentalBooking> rentalHistory = new ArrayList<>();

    public Student(int userId, String name, String email, String contactNumber, String password, Map<String, String> securityAnswers) {
        super(userId, name, email, contactNumber, password, securityAnswers);
    }


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

    // Add to homeowner as well
    listing.getHomeowner().addRentalRequest(request);

    // Persist globally
    RentalRequestRepo.addRequest(request);
}

    /* UPDATE HISTORY */
    public void addRentalHistory(RentalBooking booking) {
        rentalHistory.add(booking);
    }
    public List<RentalRequest> getRentalRequests() {
        return List.copyOf(rentalRequests);
    }

    public List<RentalBooking> getRentalHistory() {
        return List.copyOf(rentalHistory);
    }
}