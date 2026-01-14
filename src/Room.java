import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
   private int roomId;
   private String roomName;
   private String description;
   private int ownerId; // Homeowner who owns this room
   private List<RentalBooking> rentalHistory; 


   public Room(int roomId, String roomName, String description, int ownerId){
      this.roomId = roomId;
      this.roomName = roomName;
      this.description = description;
      this.ownerId = ownerId;
      this.rentalHistory = new ArrayList<>(); //Variable must provide either dimension expressions or an array initializer
   }
   public int getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public String getDescription() { return description; }
    public int getOwnerId() { return ownerId; }

    public void addBooking(RentalBooking booking){
        rentalHistory.add(booking);
    }

    public List<RentalBooking> getRentalHistory(){
        return List.copyOf(rentalHistory);
    }

   // Why ovveride?
   @Override
    public String toString() {
        return "Room ID: " + roomId + ", Name: " + roomName + ", Description: " + description;
    }
}
