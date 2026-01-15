/*

 * RentalBooking
 * 
 * 14-01-2026
 * 
 */

import java.io.Serializable;
import java.util.Date;

public class RentalBooking implements Serializable {

   /* RENTAL BOOKING ATTRIBUTES */

   private int bookingId;
   private Student student;
   private RentalListing listing;
   private Date startDate;
   private Date endDate;
   private double totalPrice;

   /* RENTAL BOOKING CONSTRUCTOR */
   public RentalBooking(int bookingId, Student student, RentalListing listing, Date startDate, Date endDate) {
      if (student == null || listing == null) {
         throw new IllegalArgumentException("Student and Listing cannot be null");
      }
      if (startDate.after(endDate)) {
         throw new IllegalArgumentException("Start date cannot be after end date");
      }

      this.bookingId = bookingId;
      this.student = student;
      this.listing = listing;
      this.startDate = startDate;
      this.endDate = endDate;

      long diffInMillis = endDate.getTime() - startDate.getTime();
      long nights = (diffInMillis / (1000 * 60 * 60 * 24)) + 1; 
      this.totalPrice = listing.getPrice() * nights;
   }

   /* GETTER METHODS */
   public int getBookingId() { 
      return bookingId; 
   }
   public Student getStudent() { 
      return student; 
   }
   public RentalListing getListing() { 
      return listing; 
   }
   public Date getStartDate() { 
      return startDate; 
   }
   public Date getEndDate() { 
      return endDate; 
   }
   public double getTotalPrice() { 
      return totalPrice; 
   }

   /* FORMATTER METHOD */
   @Override
   public String toString() {
      return "Booking ID: " + bookingId +
            ", Student: " + student.getName() +
            ", Listing ID: " + listing.getListingId() +
            ", From: " + startDate +
            ", To: " + endDate +
            ", Total Price: $" + totalPrice;
   }
}
