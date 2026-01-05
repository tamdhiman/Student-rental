import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Homeowner extends User {
    private final List<Room> rent_rooms = new ArrayList<>();
    private final List<RentalListing> rent_listings = new ArrayList<>();
    private final List<RentalBooking> rent_history = new ArrayList<>();
    private final List<RentalRequest> rental_requests = new ArrayList<>();

    public Homeowner(int user_id, String first_name, String last_name, String email, String contact_number, String password, Map<String, String> security_answers) {
        super(user_id, first_name, last_name, email, contact_number, password, security_answers);
    }

    public void add_room() {
        Room new_room = new Room();
        rent_rooms.add(new_room);
    }

    public void add_listing() {
        RentalListing new_listing = new RentalListing();
        rent_listings.add(new_listing);
    }

    public List<Room> getRooms() {
        return rent_rooms;
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
