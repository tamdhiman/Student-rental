/*

 * Homeowner
 * 
 * 14-01-2026
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Homeowner extends User {

    /* HOMEOWNER ATTRIBUTES */
    private final List<Room> rentRooms = new ArrayList<>();
    private final List<RentalListing> rentListings = new ArrayList<>();
    private final List<RentalBooking> rentHistory = new ArrayList<>();

    /* HOMEOWNER CONSTRUCTOR */
    public Homeowner(int userId, String name, String email, String contactNumber, String password, Map<String, String> securityAnswers) {
        super(userId, name, email, contactNumber, password, securityAnswers);
    }


    /* ADD METHODS */
    public void addRoom(String roomName, String roomDescription) {
        int roomId = RoomRepo.generateRoomId();
        Room newRoom = new Room(roomId, roomName, roomDescription, this);
        rentRooms.add(newRoom);
        RoomRepo.addRoom(newRoom);
    }

    public void addListing(List<Room> rooms, String city, String address, List<DateRange> availableDates, double price) { 
    int listingId = RentalListingRepo.generateListingId();
    RentalListing newListing = new RentalListing(listingId, rooms, city, address, availableDates, price, this);

    rentListings.add(newListing);              // local list
    RentalListingRepo.addListing(newListing);  // persist globally
    }



    /* GETTER METHODS */
    public List<Room> getRooms() {
        return List.copyOf(rentRooms);
    }

    public List<RentalListing> getListings() {
    return new ArrayList<>(RentalListingRepo.getListingsByHomeowner(this).values());
    }

    public List<RentalBooking> getRentHistory() {
        return List.copyOf(rentHistory);
    }

    public List<RentalRequest> getRentalRequests() {
        return  RentalRequestRepo.getRequestsByHomeowner(this);
    }


}