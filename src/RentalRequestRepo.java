import java.util.ArrayList;
import java.util.List;

public class RentalRequestRepo {

    private List<RentalRequest> rentalRequests;

    public RentalRequestRepo() {
        this.rentalRequests = new ArrayList<>();
    }

    // Add a new rental request
    public void addRequest(RentalRequest request) {
        rentalRequests.add(request);
    }

    // Get all requests
    public List<RentalRequest> getAllRequests() {
        return rentalRequests;
    }

    // Find a request by request_id
    public RentalRequest findRequestById(int requestId) {
        for (RentalRequest request : rentalRequests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }

    // Update request status
   public void updateRequestStatus(int requestId, RequestStatus status) {
    RentalRequest request = findRequestById(requestId);
    if (request != null) {
        request.updateHistory(status);
    }
}

    // Remove a request
    public void removeRequest(int requestId) {
        rentalRequests.removeIf(r -> r.getRequestId() == requestId);
    }
}
