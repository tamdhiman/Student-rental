import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class Homeowner extends User {
    private final List<Room> rentRooms = new ArrayList<>();
    private final List<RentalListing> rentListings = new ArrayList<>();
    private final List<RentalBooking> rentHistory = new ArrayList<>();
    private final List<RentalRequest> rentalRequests = new ArrayList<>();

    public Homeowner(int user_id, String name, String email, String contact_number, String password, Map<String, String> security_answers) {
        super(user_id, name, email, contact_number, password, security_answers);
    }

    public void addRoom(String roomName, String roomDescription) {
        int roomId = RoomRepo.generateRoomId();
        Room newRoom = new Room(roomId, roomName, roomDescription, this.getUserId());
        rentRooms.add(newRoom);
        RoomRepo.addRoom(newRoom);
    }

    public void addListing(List<Room> rooms, String city, String address, List<DateRange> availableDates, double price) { 
    int listingId = RentalListingRepo.generateListingId();
    RentalListing newListing = new RentalListing(listingId, rooms, city, address, availableDates, price, this);

    rentListings.add(newListing);              // local list
    RentalListingRepo.addListing(newListing);  // persist globally
}



    public List<Room> getRooms() {
        return List.copyOf(rentRooms);
    }

    public List<RentalListing> getListings() {
        return List.copyOf(rentListings);
    }

    public List<RentalBooking> getRentHistory() {
        return List.copyOf(rentHistory);
    }

    public List<RentalRequest> getRentalRequests() {
        return List.copyOf(rentalRequests);
    }


}