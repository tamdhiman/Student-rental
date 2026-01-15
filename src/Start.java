/*

 * Start
 * 
 * 14-01-2026
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class Start {
   
   /* START ATTRIBUTES */
   private User currentUser = null; //Need?
   private Scanner sc = new Scanner (System.in);

/* MAIN METHOD */
   public static void main(String[] args) {
      Start mainInstance = new Start();
      mainInstance.runApp();
   }

/* STUDENT RENTAL APPLICATION */

   public void runApp(){
      System.out.println("Welcome to student rentals! We hope you find your home away from home here!!"); 
      pause(1000);

      while (true) {

         /* Default/Startup Page */ 
         System.out.println("Select an option by entering the corresponding number");
         System.out.println("1. Create an account");
         System.out.println("2. Login into account");
         System.out.println("3. Exit this simulation");
         pause(2000);
         String input = sc.nextLine();
         int userChoice;
         try {
            userChoice = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 3) ");
            continue;
         }

         switch (userChoice) {
               case 1 -> createAccount();
               case 2 -> login();
               case 3 -> exit();
               default -> System.out.println("Please choose from the preseneted options");
         }
      } 
   }

   /* APPLICATION HELPER METHODS? */

   /* ACCOUNT CREATION */
   private void createAccount(){ 
      
      /* User Input for Account Information */
      int newUserId = UserRepo.generateUserId();
      String name = getInput("Your name:");
      String email = getEmailInput("Email:");
      String contactNumber = getContactNumberInput("Contact Number:");
      String password = getPasswordInput("Password:");
      
      Map<String, String> answers = new HashMap<>();
      for (String question : SecurityPolicy.getSecurityQuestions()) {
         String answer = getInput(question); 
         answers.put(question, answer);
      }
      User newUser = null;

      /* User Type Selection */

      while (true) {
         System.out.println("Select your role:");
         System.out.println("1. Student");
         System.out.println("2. Homeowner");
         System.out.println("3. Return to previous page");

         
         String input = sc.nextLine();
         int userChoice;
         try {
            userChoice = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 3) ");
            continue;
         }
         
         switch (userChoice) {
            case 1 -> newUser = new Student(newUserId, name, email, contactNumber, password, answers);
            case 2 -> newUser = new Homeowner(newUserId, name, email, contactNumber, password, answers);
            case 3 -> exit();
            default -> {
                  System.out.println("Invalid role. Try again.");
                  continue; 
            }
         }
         break;
      }

      /* Saving User to Repository */
      if (newUser != null) {
         UserRepo.addUser(newUser);
         System.out.println("Account created successfully!!");
      }
   }

   /* ACCOUNT LOGIN */
   private void login(){
      
      /* User Input for Login Information */

      while(true){
         System.out.print("Enter your name or email:");
         System.out.println("--Enter exit to return--");
         String loginCredentials = sc.nextLine();  
         
         // Return to the previous menu
         if (loginCredentials.equalsIgnoreCase("exit")) {
         return;
        }

        User tempUser = null;
        tempUser = UserRepo.findUser(loginCredentials);

        if (tempUser == null ){
         System.out.println("User not found. Try again.");
         continue;
        }
        
        System.out.print("Enter password: "); 
        System.out.println("--Enter HELP to reset passoword--");
        String password = sc.nextLine();

        // Reset password 
        if (password.equalsIgnoreCase("HELP")) {
         if (resetPassword(tempUser)) {
            System.out.println("Password reset successful! Please log in again.");
            return;
         } 
         else {
            System.out.println("Password reset failed :(");
            continue;
         }
         }

        if (!tempUser.authenticateUser(password)) {
            System.out.println("Incorrect password.");
            continue;
        }

        currentUser = tempUser;
        System.out.println("Login successful!");
        System.out.println("Welcome " + tempUser.getName());
        pause(2000);

        runUserPage();
        break;
      }
   }

   /* APPLICATION PAGES */

   private void runUserPage(){
      if (currentUser instanceof Student student) {
        studentPage(student);
      } 
      else if (currentUser instanceof Homeowner homeowner) {
         homeownerPage(homeowner);
      }
      }

   /* HOMEOWNER PAGES */


   private void homeownerPage(Homeowner homeowner){
      
      // Main Page Menu
      while (true) {
         System.out.println("\n--- Homeowner Main Page ---");
         System.out.println("What would you like to do?");
         System.out.println("1. Create/View your Rooms");
         System.out.println("2. Create/View a Rental Listing");
         System.out.println("3. View a Rental Request ");
         System.out.print("4: Return to previous page ");
         pause(2000);

         String input = sc.nextLine();
         int userChoice;
         try {
            userChoice = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 4) ");
            continue;
         }

         
         switch (userChoice) {
               case 1 -> homeownerRoomPage(homeowner);
               case 2 -> rentalListingPage(homeowner);
               case 3 -> rentalRequestsPage(homeowner);
               case 4 -> {
                        System.out.println("Returning to main menu...");
                        return;}
               default -> System.out.println("Please choose from the preseneted options");
         }
      }
      
   }

   // Manages Rooms for Homeowner
   private void homeownerRoomPage(Homeowner homeowner) {

      // Room Management Menu
      while (true) {
         System.out.println("\n--- Room Management ---");
         System.out.println("1. Add a new Room");
         System.out.println("2. View your Rooms");
         System.out.println("3. Return to Homeowner Page");
         System.out.print("Enter your choice: ");
         pause(2000);

         String input = sc.nextLine();
         int userChoice;
         try {
         userChoice = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 3) ");
            continue;
         }

         switch (userChoice) {

            // Creating a room
            case 1 -> {
               String roomName;
               do {
                     System.out.print("Enter room name: ");
                     roomName = sc.nextLine().trim();
               } while (roomName.isEmpty());

               String roomDescription;
               do {
                     System.out.print("Enter room description: ");
                     roomDescription = sc.nextLine().trim();
               } while (roomDescription.isEmpty());

              
               homeowner.addRoom(roomName, roomDescription);
               System.out.println("Room added successfully!");
            }

            // Viewing Rooms
            case 2 -> {
               Map<Integer, Room> roomMap = RoomRepo.getRoomsByHomeowner(homeowner);
               List<Room> rooms = new ArrayList<>(roomMap.values());

               if (rooms.isEmpty()) {
                     System.out.println("You have no rooms yet.");

               } else {
                     System.out.println("\n--- Your Rooms ---");
                     for (int i = 0; i < rooms.size(); i++) {
                        Room r = rooms.get(i);
                        System.out.println((i + 1) + ". " + "Name: " + r.getRoomName() + " | Description: " + r.getDescription() + " | Room ID: " + r.getRoomId());
                        pause(2000);
                     }
               }
            }
            //Return to previous menu
            case 3 -> {
                  System.out.println("Returning to Homeowner Dashboard...");
                  return;
            }
            default -> System.out.println("Invalid option.");
         }
      }
}

// Manages Rental Listings for Homeowner
private void rentalListingPage(Homeowner homeowner) {

   // Rental Listing Menu
   while (true) {
      System.out.println("\n--- Rental Listing Management ---");
      System.out.println("1. Create a new Rental Listing");
      System.out.println("2. View your Rental Listings");
      System.out.println("3. Return to Homeowner Dashboard");
      System.out.print("Enter your choice: ");

      String input = sc.nextLine();
      int choice;
      try {
         choice = Integer.parseInt(input);
      } catch (NumberFormatException e) {
         System.out.println("Please enter a valid number (1 - 3)");
         continue;
      }

      switch (choice) {
         case 1 -> addListingPage(homeowner); 

         // Displays all Rental Listings
         case 2 -> {
            List<RentalListing> listings = new ArrayList<>(RentalListingRepo.getListingsByHomeowner(homeowner).values());
            if (listings.isEmpty()) {
               System.out.println("You have no rental listings yet.");
            } else {
               System.out.println("--- Your Rental Listings ---");
               for (int i = 0; i < listings.size(); i++) {
                  System.out.println((i + 1) + ". " + listings.get(i));
               }
            }
         }
         //Returns to previous menu
         case 3 -> {
            System.out.println("Returning to Homeowner Dashboard...");
            return;
         }
         default -> System.out.println("Invalid option.");
      }
   }
}

// Creates a rental listing
private void addListingPage(Homeowner homeowner) {

   // Get only avai;able rooms owned by this homeowner
   List<Room> availableRooms = RoomRepo.getAvailableRoomsByHomeowner(homeowner);

   if (availableRooms.isEmpty()) {
      System.out.println("You have no available rooms to create a listing.");
      return;
   }

   /* CITY INPUT */
   String city;
   do {
      System.out.print("Enter city: ");
      city = sc.nextLine().trim();
   } while (city.isEmpty());

   /* ADDRESS INPUT */
   String address;
   do {
      System.out.print("Enter address: ");
      address = sc.nextLine().trim();
   } while (address.isEmpty());

   /* PRICE INPUT */
   double price;
   while (true) {
      System.out.print("Enter price per night: ");
      try {
         price = Double.parseDouble(sc.nextLine());
         if (price < 0) throw new NumberFormatException();
         break;
      } catch (NumberFormatException e) {
         System.out.println("Invalid price. Enter a positive number.");
      }
   }

   /* ROOM SELECTION */
   System.out.println("\n--- Available Rooms ---");
   for (int i = 0; i < availableRooms.size(); i++) {
      System.out.println((i + 1) + ". " + availableRooms.get(i));
   }

   System.out.print("Select room numbers (comma-separated): ");
   String[] selections = sc.nextLine().split(",");

   List<Room> selectedRooms = new ArrayList<>();

   try {
      for (String s : selections) {
         int index = Integer.parseInt(s.trim()) - 1;
         if (index < 0 || index >= availableRooms.size()) {
            throw new NumberFormatException();
         }
         selectedRooms.add(availableRooms.get(index));
      }
   } catch (NumberFormatException e) {
      System.out.println("Invalid room selection. Listing cancelled.");
      return;
   }

   if (selectedRooms.isEmpty()) {
      System.out.println("You must select at least one room.");
      return;
   }

   /* AVAILABLE DATES */
   List<DateRange> availableDates = new ArrayList<>();
   getAvailableDates(availableDates);

   if (availableDates.isEmpty()) {
      System.out.println("You must add at least one available date range.");
      return;
   }

   /* CREATE LISTING */
   int listingId = RentalListingRepo.generateListingId();
   RentalListing listing = new RentalListing(
         listingId,
         selectedRooms,
         city,
         address,
         availableDates,
         price,
         homeowner
   );

   RentalListingRepo.addListing(listing);

   /* ASSIGN ROOMS TO LISTING */
   for (Room room : selectedRooms) {
      room.assignToListing(listingId);
   }

   System.out.println("Rental Listing added successfully!");
}



/* Manages Rental Requests for Homeowner */
private void rentalRequestsPage(Homeowner homeowner) {

   List<RentalRequest> requests = RentalRequestRepo.getRequestsByHomeowner(homeowner);

   if (requests.isEmpty()) {
      System.out.println("No rental requests at the moment.");
      pause(1000);
      return;
   }

   while (true) {
      System.out.println("\n--- Rental Requests ---");

      for (int i = 0; i < requests.size(); i++) {
         System.out.println((i + 1) + ". " + requests.get(i));
      }

      System.out.print("Enter request number to respond or 0 to return: ");
      String input = sc.nextLine();
      pause(1000);

      int choice;
      try {
         choice = Integer.parseInt(input);
      } catch (NumberFormatException e) {
         System.out.println("Invalid input.");
         continue;
      }

      if (choice == 0) {
         System.out.println("Returning to Homeowner Dashboard...");
         return;
      }

      if (choice < 1 || choice > requests.size()) {
         System.out.println("Invalid choice.");
         continue;
      }

      RentalRequest selected = requests.get(choice - 1);

      if (selected.getCurrentStatus() != RequestStatus.UNDECIDED) {
         System.out.println("This request has already been processed.");
         continue;
      }

      System.out.println("1. Accept");
      System.out.println("2. Reject");
      System.out.println("3. Undecided");
      System.out.print("Choose an option: ");
      String decision = sc.nextLine();

      switch (decision) {
         case "1" -> {
               selected.updateHistory(RequestStatus.ACCEPTED);
               RentalRequestRepo.updateRequest(selected);  
               System.out.println("Request accepted.");
         }
         case "2" -> {
               selected.updateHistory(RequestStatus.REJECTED);
               RentalRequestRepo.updateRequest(selected);  
               System.out.println("Request rejected.");
         }
         default -> System.out.println("Invalid option.");
      }
   }
}

// Gets a range of available dates for a room
private void getAvailableDates(List<DateRange> availableDates) {
   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   while (true) {
      System.out.println("Enter start date (yyyy-mm-dd) or 'exit' to finish:");
      String startInput = sc.nextLine().trim();
      if (startInput.equalsIgnoreCase("exit")) break;

      System.out.println("Enter end date (yyyy-mm-dd):");
      String endInput = sc.nextLine().trim();

      try {
            
            LocalDate startLocal = LocalDate.parse(startInput, formatter);
            LocalDate endLocal = LocalDate.parse(endInput, formatter);

            if (startLocal.isBefore(LocalDate.now())) {
                System.out.println("Start date cannot be before today!");
                continue;
            }

            if (endLocal.isBefore(startLocal)) {
                System.out.println("End date cannot be before start date!");
                continue;
            }

            
            Date startDate = Date.from(startLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

         // Checks for overlaps with existing ranges
         boolean overlaps = false;
         for (DateRange dr : availableDates) {
               if (!(endDate.before(dr.getStart()) || startDate.after(dr.getEnd()))) {
                  overlaps = true;
                  break;
               }
         }
         if (overlaps) {
               System.out.println("This range overlaps with an existing range. Try again.");
               continue;
         }

         availableDates.add(new DateRange(startDate, endDate));
         System.out.println("Date range added!");

      } catch (Exception e) {
         System.out.println("Invalid date format. Use yyyy-mm-dd.");
      }
   }
}

/* STUDENT PAGES */


private void studentPage(Student student){
   // Student Main Menu

   while (true) {
      System.out.println("\n--- Student Main Menu ---");
      System.out.println("1. Search Rental Listings");
      System.out.println("2. View your Rental Requests");
      System.out.println("3. View Rental History");
      System.out.println("4. Return to previous menu");
      System.out.print("Enter your choice: ");

      String input = sc.nextLine();
      int choice;
      try {
         choice = Integer.parseInt(input);
      } catch (NumberFormatException e) {
         System.out.println("Please enter a valid number (1 - 4)");
         continue;
      }

      switch (choice) {
         case 1 -> searchListingsPage(student);
         case 2 -> viewStudentRequests(student);
         case 3 -> viewRentalHistory(student);
         case 4 -> { return; }
         default -> System.out.println("Invalid option.");
      }
   }
}

// Shows all the Listings
private void searchListingsPage(Student student){

   List<RentalListing> listings =
         new ArrayList<>(RentalListingRepo.getAllListings().values());

   if (listings.isEmpty()) {
      System.out.println("No rental listings available.");
      return;
   }

   System.out.println("\n--- Available Rental Listings ---");
   for (int i = 0; i < listings.size(); i++) {
      System.out.println((i + 1) + ". " + listings.get(i));
   }
   pause(3000);

    while (true) {
      System.out.println("\n--- Listing Action ---");
      System.out.println("1. Select a listing");
      System.out.println("2. Filter the listings");
      System.out.println("3. Return to previous menu");
      System.out.print("Enter your choice: ");

      String input = sc.nextLine();
      int choice;
      try {
         choice = Integer.parseInt(input);
      } catch (NumberFormatException e) {
         System.out.println("Please enter a valid number (1 - 3)");
         continue;
      }
      switch (choice) {
         case 1 -> {
            if (choice < 1 || choice > listings.size()) {
            System.out.println("Invalid listing selection.");
            }
            else{
               RentalListing selectedListing = listings.get(choice - 1);
               student.createRequest(selectedListing);
               System.out.println("Rental request submitted successfully!");
            }
         }
         case 2 -> filterListings(listings);
         case 3 -> { return; }
         default -> System.out.println("Invalid option.");
      }
   }

}

private void filterListings(List<RentalListing> listings) {

   // Filer Listings Menu
   System.out.println("\n--- Filter Listings ---");
   System.out.println("Choose filtering method:");
   System.out.println("1. Quick filter (City only)");
   System.out.println("2. Advanced filter (City + Max Price)");
   System.out.print("Enter your choice: ");

   String input = sc.nextLine();
   int choice;

   try {
      choice = Integer.parseInt(input);
   } catch (NumberFormatException e) {
      System.out.println("Invalid option.");
      return;
   }

   List<RentalListing> filtered = new ArrayList<>();

   switch (choice) {

      // First Match Search (Greedy)
      case 1 -> {
         System.out.print("Enter city: ");
         String city = sc.nextLine().trim();
         for (RentalListing listing : listings) {
         if (listing.getCity().equalsIgnoreCase(city)) {
            filtered.add(listing);
            break; // EARLY EXIT
         }
}
      }

      // Complete Search (Exhaustive/Brute Force?)
      case 2 -> {
         System.out.print("Enter city: ");
         String city = sc.nextLine().trim();

         double maxPrice;
         try {
               System.out.print("Enter maximum price per night: ");
               maxPrice = Double.parseDouble(sc.nextLine());
         } catch (NumberFormatException e) {
               System.out.println("Invalid price.");
               return;
         }

         for (RentalListing listing : listings) {
         if (
            listing.getCity().equalsIgnoreCase(city) &&
            listing.getPrice() <= maxPrice
         ) {
            filtered.add(listing);
         }
      }
      }

      default -> {
         System.out.println("Invalid selection.");
         return;
      }
   }

   // Display results
   if (filtered.isEmpty()) {
      System.out.println("No listings matched your criteria.");
      return;
   }

   System.out.println("\n--- Filtered Listings ---");
   for (int i = 0; i < filtered.size(); i++) {
      System.out.println((i + 1) + ". " + filtered.get(i));
   }
}


// Displays Student Requests
private void viewStudentRequests(Student student) {

   List<RentalRequest> requests = RentalRequestRepo.getRequestsByStudent(student);

   if (requests.isEmpty()) {
      System.out.println("You have no rental requests.");
      return;
   }

   System.out.println("\n--- Your Rental Requests ---");
   for (int i = 0; i < requests.size(); i++) {
      System.out.println((i + 1) + ". " + requests.get(i));
   }

   System.out.println("\nEnter request number to view history or 0 to return:");
   int choice;

   try {
      choice = Integer.parseInt(sc.nextLine());
   } catch (NumberFormatException e) {
      System.out.println("Invalid input.");
      return;
   }

   if (choice == 0) return;

   if (choice < 1 || choice > requests.size()) {
      System.out.println("Invalid selection.");
      return;
   }

   RentalRequest request = requests.get(choice - 1);

   System.out.println("\n--- Request History ---");
   for (RentalRequest.RequestHistory h : request.getRequestHistory()) {
      System.out.println(
               h.getDate() + " → " + h.getStatus()
      );
   }
}

// Displays Rental History
private void viewRentalHistory(Student student) {

    List<RentalBooking> history = student.getRentalHistory();

    if (history.isEmpty()) {
        System.out.println("No rental history yet.");
        return;
    }

    System.out.println("\n--- Your Rental History ---");
    for (int i = 0; i < history.size(); i++) {
        System.out.println((i + 1) + ". " + history.get(i));
    }
}

/* HELPER METHODS */


   // Exit Application
   private void exit(){
      System.out.println("Exiting simulation now...");
      pause(1000);
      System.exit(0); // Status 0 indicates normal termination
   }

   // Reset Password
   private boolean resetPassword(User user) {
      
      String question = user.getRandomSecurityQuestion();
      System.out.println("Security question:");
      System.out.println(question);

      System.out.print("What is the answer?: ");
      String answer = sc.nextLine();

      if (!user.verifySecurityAnswer(question, answer)) {
         return false;
      }

      // Ensure new password is different from the previous one
      String newPassword;
      do {
         newPassword = getPasswordInput("Enter a new password:");
         if (user.authenticateUser(newPassword)) {
               System.out.println("New password must be different from the old one.");
         }
      } while (user.authenticateUser(newPassword));

      // Set new password (make sure it shows up in the repo)
      user.setPassword(newPassword);
      System.out.println("Password successfully reset!");
      return true;
   }
   
   // Creates pauses in application using Threads
   private void pause(int time){
   try {
      Thread.sleep(time);
   } 
   catch (InterruptedException e) {
      e.printStackTrace();
   }
}
   /* INPUT HELPER METHODS*/
   
   private String getInput(String prompt){ 
   String userInput;
   do{
      System.out.println(prompt);
      userInput = sc.nextLine();
   }while(userInput.isEmpty());
   return userInput;
   }

   private String getEmailInput (String prompt){
      String email;
      do {
         email = getInput(prompt);
      } 
      while (!email.contains("@") || !email.contains("."));
      return email;
   }

   private String getContactNumberInput(String prompt){
      String number;
      do {
         number = getInput(prompt);
      } 
      while (!number.matches("\\d{8,15}")); /*Any digit, must be 8 - 15 digits long. Should I make it just 10 for UK? */
      return number;

   }

   private String getPasswordInput(String prompt){
      String password;
      do {
         password = getInput(prompt);
      } 
      while (
         password.length() < 6 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*") /* Atleast 6 alpha, one capital and one digit */
      );

      return password;
   }


}
