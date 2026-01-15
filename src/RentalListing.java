/*

 * RentalListing
 * 
 * 14-01-2026
 * 
 */


import java.io.Serializable;
import java.util.List;

public class RentalListing implements Serializable {

   /* RENTAL LISTING ATTRIBUTES */
   private int listingId;
   private List<Room> rooms;
   private String city;
   private String address;
   private List<DateRange> availableDates;
   private double price;
   private Homeowner homeowner;

   /* RENTAL LISTING CONSTRUCTOR */
   public RentalListing(int listingId, List<Room> rooms, String city, String address, List<DateRange> availableDates, double price, Homeowner homeowner) {
      if (homeowner == null) {
         throw new IllegalArgumentException("A rental listing must have a homeowner!");
      }
      this.listingId = listingId;
      this.rooms = rooms;
      this.city = city;
      this.address = address;
      this.availableDates = availableDates;
      this.price = price;
      this.homeowner = homeowner;
   }

   /* SETTER METHODS */
   public void setRooms(List<Room> rooms) { 
      this.rooms = rooms; 
   }
   public void setAvailableDates(List<DateRange> availableDates) { 
      this.availableDates = availableDates; 
   }
   public void setPrice(double price) { 
      this.price = price; 
   }


   /* GETTER METHODS */
   public int getListingId() { 
      return listingId; 
   }
   public List<Room> getRooms() { 
      return rooms; 
   }
   public String getCity() { 
      return city; 
   }
   public String getAddress() { 
      return address; 
   }
   public List<DateRange> getAvailableDates() { 
      return availableDates; 
   }
   public double getPrice() { 
      return price; 
   }
   public Homeowner getHomeowner() { 
      return homeowner; 
   }


   @Override // Display Rental Listings in a Formatted Manner
   public String toString() {
      StringBuilder dateList = new StringBuilder(); 
      if (availableDates != null && !availableDates.isEmpty()) {
         for (int i = 0; i < availableDates.size(); i++) {
            dateList.append(availableDates.get(i));
            if (i < availableDates.size() - 1) {
                  dateList.append(", ");
            }
         }
      } else {
         dateList.append("No available dates");
      }
      return "Listing ID: " + listingId +
            ", City: " + city +
            ", Address: " + address +
            ", Price: $" + price +
            ", Rooms: " + rooms.size() +
            ", Homeowner: " + homeowner.getName() +
            ", Available Dates: [" + dateList + "]";
   }
}
