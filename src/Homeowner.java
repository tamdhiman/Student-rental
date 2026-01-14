import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Homeowner extends User {
    private final List<Room> rentRooms = new ArrayList<>();
    private final List<RentalListing> rent_listings = new ArrayList<>();
    private final List<RentalBooking> rent_history = new ArrayList<>();
    private final List<RentalRequest> rental_requests = new ArrayList<>();

    public Homeowner(int user_id, String name, String email, String contact_number, String password, Map<String, String> security_answers) {
        super(user_id, name, email, contact_number, password, security_answers);
    }

    public void add_room() {
        Room new_room = new Room();
        rentRooms.add(new_room);
    }

    public void add_listing() {
        RentalListing new_listing = new RentalListing();
        rent_listings.add(new_listing);
    }

    public List<Room> getRooms() {
        return rentRooms;
    }

    public List<RentalListing> getListings() {
        return rent_listings;
    }

    public List<RentalBooking> getRentHistory() {
        return rent_history;
    }

    public List<RentalRequest> getRentalRequests() {
        return rental_requests;
    }
}
