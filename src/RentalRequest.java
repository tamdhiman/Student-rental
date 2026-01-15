/*

 * RentalListing
 * 
 * 14-01-2026
 * 
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalRequest implements Serializable{

    /* RENTAL REQUEST ATTRIBUTES */
    private int requestId;
    private RentalListing listing;
    private Student student;
    private Homeowner homeowner;
    private List<RequestHistory> requestHistory;
    private RequestStatus currentStatus;
    
    /*  INNER CLASS REQUEST HISTORY */

    public static class RequestHistory implements Serializable {
        private int id;
        private String status;
        private Date date;

        public RequestHistory(int id, String status, Date date) {
            this.id = id;
            this.status = status;
            this.date = date;
        }

        // Getter Methods
        public int getId() { return id; }
        public String getStatus() { return status; }
        public Date getDate() { return date; }

        // Setter Methods
        public void setId(int id) { this.id = id; }
        public void setStatus(String status) { this.status = status; }
        public void setDate(Date date) { this.date = date; }
    }


    /* RENTAL REQUEST CONSTRUCTOR */
    public RentalRequest(int requestId, Student student, RentalListing listing, Homeowner homeowner) {
        this.requestId = requestId;
        this.student = student;
        this.listing = listing;
        this.homeowner = homeowner;
        this.requestHistory = new ArrayList<>();

        this.currentStatus = RequestStatus.UNDECIDED;

        // Record initial status as Undecided
        updateHistory(RequestStatus.UNDECIDED);

    }

    /* HELPER METHODS */

    public static RentalRequest createRequest(int requestId, Student student, RentalListing listing, Homeowner homeowner) {
        return new RentalRequest(requestId, student, listing, homeowner);
    }
    
    public void updateHistory(RequestStatus status) {
        this.currentStatus = status;
        int historyId = requestHistory.size() + 1;
        requestHistory.add(new RequestHistory(historyId, status.name(), new Date()));
    }

    
    @Override
    public String toString() {
        return "Request ID: " + requestId +
               ", Student: " + student.getName() +
               ", Listing ID: " + listing.getListingId() +
               ", Status: " + currentStatus;
    }

    /* SETTER METHODS */

    public void setListing(RentalListing listing) { 
        this.listing = listing; 
    }
    public void setStudent(Student student) { 
        this.student = student; 
    }
    public void setHomeowner(Homeowner homeowner) { 
        this.homeowner = homeowner; 
    }


    /* GETTER METHODS */

    public int getRequestId() { 
        return requestId; 
    }
    public RentalListing getListing() { 
        return listing; 
    }
    public Student getStudent() { 
        return student; 
    }
    public Homeowner getHomeowner() { 
        return homeowner; 
    }
    public List<RequestHistory> getRequestHistory() { 
        return requestHistory; 
    }
    public void setRequestId(int request_id) { 
        this.requestId = request_id; 
    }
    public RequestStatus getCurrentStatus() {
        return currentStatus;
    }

}

