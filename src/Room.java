/*

 * Room
 * 
 * 14-01-2026
 * 
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

    /* ROOM ATTRIBUTES */
   private int roomId;
   private String roomName;
   private String description;
   private Homeowner owner; 
   private List<RentalBooking> rentalHistory; 
   private Integer listingId;

    /* ROOM CONSTRUCTOR */
   public Room(int roomId, String roomName, String description,  Homeowner owner ){
      this.roomId = roomId;
      this.roomName = roomName;
      this.description = description;
      this.owner = owner;
      this.rentalHistory = new ArrayList<>();
      this.listingId = null;
   }

   /* HELPER METHODS */

   public void addBooking(RentalBooking booking){
        rentalHistory.add(booking);
    }

    public boolean isAvailable() {
      return listingId == null;
   }

   public void assignToListing(int listingId) {
      this.listingId = listingId;
   }
   public void removeFromListing() {
      this.listingId = null;
   }
   //  Prints Formatted Room Information
   @Override
    public String toString() {
        return "Room ID: " + roomId + ", Name: " + roomName + ", Description: " + description;
    }



   /* GETTER METHODS */
   public int getRoomId(){ 
        return this.roomId; 
    }

    public String getRoomName() { 
        return this.roomName; 
    }
    public String getDescription() { 
        return this.description; 
    }
    public Homeowner getOwner() { 
        return this.owner; 
    } 
    public List<RentalBooking> getRentalHistory(){
        return List.copyOf(rentalHistory);
    }
    public Integer getListingId() { 
        return listingId; 
    }
}
