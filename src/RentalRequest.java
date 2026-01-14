
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalRequest implements Serializable{

    private int requestId;
    private RentalListing listing;
    private Student student;
    private Homeowner homeowner;
    private List<RequestHistory> requestHistory;
    private RequestStatus currentStatus;
    
    // Inner class for request history
    public static class RequestHistory implements Serializable {
        private int id;
        private String status;
        private Date date;

        public RequestHistory(int id, String status, Date date) {
            this.id = id;
            this.status = status;
            this.date = date;
        }

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }
    }


    // Constructor
    public RentalRequest(int requestId, Student student, RentalListing listing, Homeowner homeowner) {
        this.requestId = requestId;
        this.student = student;
        this.listing = listing;
        this.homeowner = homeowner;
        this.requestHistory = new ArrayList<>();

        this.currentStatus = RequestStatus.PENDING;

        // Record initial status
        updateHistory(RequestStatus.PENDING);

    }

    // Static factory method similar to UML
    public static RentalRequest createRequest(int requestId, Student student, RentalListing listing, Homeowner homeowner) {
        return new RentalRequest(requestId, student, listing, homeowner);
    }


    // Getters and setters
    public int getRequestId() { return requestId; }
    public void setRequestId(int request_id) { this.requestId = request_id; }

    public RentalListing getListing() { return listing; }
    public void setListing(RentalListing listing) { this.listing = listing; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Homeowner getHomeowner() { return homeowner; }
    public void setHomeowner(Homeowner homeowner) { this.homeowner = homeowner; }

    public List<RequestHistory> getRequestHistory() { return requestHistory; }

    public void updateHistory(RequestStatus status) {
        this.currentStatus = status;
        int historyId = requestHistory.size() + 1;
        requestHistory.add(new RequestHistory(historyId, status.name(), new Date()));
    }

    public RequestStatus getCurrentStatus() {
        return currentStatus;
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId +
               ", Student: " + student.getName() +
               ", Listing ID: " + listing.getListingId() +
               ", Status: " + currentStatus;
    }
}

