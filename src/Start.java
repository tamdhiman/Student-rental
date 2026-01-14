/*

 * Classname
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */
//package java.awt;import java.awt.peer.CanvasPeer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public class Start {
   // Whenever I stop running the terminal, and come back I lose my progress. Help me update UserRepo so that the valus stay even after
   Scanner sc = new Scanner(System.in);
   User currentUser = null;
   public static void main(String[] args) {
      Start mainInstance = new Start();
      mainInstance.runApp();
   }


   public void runApp(){
      System.out.println("Welcome! "); //Add message and maybe some pizzazz?

      //Add pause

      while (true) {
         System.out.println("Select an option by entering the corresponding number");
         System.out.println("1. Create an account");
         System.out.println("2. Login into account");
         System.out.println("3. Exit this simulation");

         String input = sc.next();
         int userChoice;
         try {
            userChoice = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 3) ");
            continue;
         }

         /* Shorthand operation */
         switch (userChoice) {
               case 1 -> createAccount();
               case 2 -> login();
               case 3 -> exit();
               default -> System.out.println("Please choose from the preseneted options");
         }/* Do not need a break because of enhanced switch expression */
      } /* After method like  create account performed, comes back here */
   }


   private void createAccount(){ /* Internal helper method */
      
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

      /* USER TYPE SELECTION */

      while (true) {
         System.out.println("Select your role:");
         System.out.println("1. Student");
         System.out.println("2. Homeowner");
         System.out.println("3. Admin");
         System.out.println("4. Return to previous page");
         
         String input = sc.next();
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
            case 3 -> {
                  System.out.println("Admin creation disabled.");
                  continue;
            }
            case 4 -> exit();
            default -> {
                  System.out.println("Invalid role. Try again.");
                  continue; 
            }
         }
         break;
      }
      if (newUser != null) {
         UserRepo.addUser(newUser);
         System.out.println("Account created successfully!!");
      }
   }

   private void login(){
      while(true){
         System.out.print("Enter your name or email:");
         System.out.println("--Enter exit to return--");
         String loginCredentials = sc.nextLine();  
          if (loginCredentials.equalsIgnoreCase("exit")) {
            return;
        }
        User tempUser = null;
        tempUser = UserRepo.findUser(loginCredentials);

        if (tempUser == null ){
         System.out.println("User not found. Try again.");
         continue;
        }
        
        System.out.print("Enter password: "); //Add forgotten password through security answers
        System.out.print("--Enter HELP to reset passoword--");
        String password = sc.nextLine();

        if (password.equalsIgnoreCase("HELP")) {
         if (resetPassword(tempUser)) {
            System.out.println("Password reset successful! Please log in again.");
            return;
         } 
         else {
            System.out.println("Security verification failed :(");
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
        runUserPage();
        // Add logic to open the corresponding functions?
        break;
      }
   }

   private void exit(){
      System.out.println("Exiting simulation now...");
      //Add pause
      System.exit(0); /* Status 0 means normal termination */
   }

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
   private boolean resetPassword(User user) {
      String question = user.getRandomSecurityQuestion(); // I am getting the same random question if I do not change the user name
      System.out.println("Security question:");
      System.out.println(question);

      System.out.print("What is the answer?: ");
      String answer = sc.nextLine();

      if (!user.verifySecurityAnswer(question, answer)) {
         return false;
      }
      String newPassword;
      do {
         newPassword = getPasswordInput("Enter a new password:");
         if (user.authenticateUser(newPassword)) {
               System.out.println("New password must be different from the old one.");
         }
      } while (user.authenticateUser(newPassword));
      user.setPassword(newPassword);
      System.out.println("Password successfully reset!");
      return true;
   }

   /*User Pages/Applications */

   public void runUserPage(){
      if (currentUser instanceof Student student) {
        studentPage(student);
      } else if (currentUser instanceof Homeowner homeowner) {
         homeownerPage(homeowner);
      } else if (currentUser instanceof Administration admin) {
         adminPage(admin);
      }
   }
   // Should I add these pages here? Or should I do method overloading in all these cases so that each has different functionality but the same function name?
   public void studentPage(Student student){

   }
   public void homeownerPage(Homeowner homeowner){
      
      while (true) {
         System.out.println("\n--- Homeowner Main Page ---");
         System.out.println("What would you like to do?");
         System.out.println("1. Create/View your Rooms");
         System.out.println("2. Create/View a Rental Listing");
         System.out.println("3. View a Rental Request ");
         System.out.print("4: Return to previous page ");

         String input = sc.next();
         int userChoice;
         try {
            userChoice = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 4) ");
            continue;
         }

         /* Shorthand operation */
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

   public void adminPage(Administration admin){

   }

   /* HOMEOWNER PAGE */

   public void homeownerRoomPage(Homeowner homeowner) {

    while (true) {
        System.out.println("\n--- Room Management ---");
        System.out.println("1. Add a new Room");
        System.out.println("2. View your Rooms");
        System.out.println("3. Return to Homeowner Page");
        System.out.print("Enter your choice: ");
        
        String input = sc.next();
        int userChoice;
        try {
         userChoice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 - 4) ");
            continue;
        }

        switch (userChoice) {
            case 1 -> {
               sc.nextLine(); // Consume leftover newline
               String roomName;
               do {
                     System.out.print("Enter room name (cannot be empty): ");
                     roomName = sc.nextLine().trim();
               } while (roomName.isEmpty());

               String roomDescription;
               do {
                     System.out.print("Enter room description (cannot be empty): ");
                     roomDescription = sc.nextLine().trim();
               } while (roomDescription.isEmpty());

               // Add room to homeowner and RoomRepo
               homeowner.addRoom(roomName, roomDescription);
               System.out.println("Room added successfully!");
            }

            case 2 -> {
               List<Room> rooms = homeowner.getRooms();
               if (rooms.isEmpty()) {
                     System.out.println("You have no rooms yet.");
               } else {
                     System.out.println("\n--- Your Rooms ---");
                     for (int i = 0; i < rooms.size(); i++) {
                        Room r = rooms.get(i);
                        System.out.println((i + 1) + ". " + "Name: " + r.getRoomName() 
                                          + " | Description: " + r.getDescription() 
                                          + " | Room ID: " + r.getRoomId());
                     }
               }
            }
            case 3 -> {
                System.out.println("Returning to Homeowner Dashboard...");
                return;
            }
            default -> System.out.println("Invalid option.");
        }
    }
}

// Work on this
private void rentalListingPage(Homeowner homeowner) {
    while (true) {
        System.out.println("\n--- Rental Listing Management ---");
        System.out.println("1. Create a new Rental Listing");
        System.out.println("2. View your Rental Listings");
        System.out.println("3. Return to Homeowner Dashboard");
        System.out.print("Enter your choice: ");

        String input = sc.next();
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            continue;
        }

        switch (choice) {
            case 1 -> addListingPage(homeowner);
            case 2 -> {
                List<RentalListing> listings = homeowner.getListings();
                if (listings.isEmpty()) {
                    System.out.println("You have no rental listings yet.");
                } else {
                    System.out.println("--- Your Rental Listings ---");
                    for (int i = 0; i < listings.size(); i++) {
                        System.out.println((i + 1) + ". " + listings.get(i));
                    }
                }
            }
            case 3 -> {
                System.out.println("Returning to Homeowner Dashboard...");
                return;
            }
            default -> System.out.println("Invalid option.");
        }
    }
}
private void addListingPage(Homeowner homeowner){

   if (homeowner.getRooms().isEmpty()) {
      System.out.println("You have no rooms to create a listing. Add rooms first!");
      return;
   }

   sc.nextLine(); 

   System.out.print("Enter city: ");
   String city = sc.nextLine().trim();
   System.out.print("Enter address: ");
   String address = sc.nextLine().trim();

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

   List<Room> roomsToList = new ArrayList<>(homeowner.getRooms());

   // Available Dates , I want to add multiple ranges of available dates
   List<DateRange> availableDates = new ArrayList<>();
   getAvailableDates(availableDates); //I want to take the dates range start and end from user

   int listingId = RentalListingRepo.generateListingId();
   RentalListing listing = new RentalListing(listingId, roomsToList, city, address, availableDates, price, homeowner);

   homeowner.addListing(roomsToList, city, address, availableDates, price);
   RentalListingRepo.addListing(listing);       

   System.out.println("Rental Listing added successfully!");
}

// Work on this

public void rentalRequestsPage(Homeowner homeowner) {

    List<RentalRequest> requests = homeowner.getRentalRequests();

    if (requests.isEmpty()) {
        System.out.println("No rental requests at the moment.");
        return;
    }

    while (true) {
        System.out.println("\n--- Rental Requests ---");

        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + ". " + requests.get(i));
        }

        System.out.print("Enter request number to respond or 0 to return: ");
        String input = sc.next();

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

        if (selected.getCurrentStatus() != RequestStatus.PENDING) {
            System.out.println("This request has already been processed.");
            continue;
        }

        System.out.println("1. Accept");
        System.out.println("2. Reject");
        System.out.print("Choose an option: ");
        String decision = sc.next();

        switch (decision) {
            case "1" -> {
                selected.updateHistory(RequestStatus.ACCEPTED);
                System.out.println("Request accepted.");
            }
            case "2" -> {
                selected.updateHistory(RequestStatus.REJECTED);
                System.out.println("Request rejected.");
            }
            default -> System.out.println("Invalid option.");
        }
    }
}


public void getAvailableDates(List<DateRange> availableDates) {
    sc.nextLine(); // consume newline

    while (true) {
        System.out.println("Enter start date (yyyy-mm-dd) or 'done' to finish:");
        String startInput = sc.nextLine().trim();
        if (startInput.equalsIgnoreCase("done")) break;

        System.out.println("Enter end date (yyyy-mm-dd):");
        String endInput = sc.nextLine().trim();

        try {
            String[] startParts = startInput.split("-");
            String[] endParts = endInput.split("-");

            Date startDate = new Date(
                Integer.parseInt(startParts[0]) - 1900,
                Integer.parseInt(startParts[1]) - 1,
                Integer.parseInt(startParts[2])
            );
            Date endDate = new Date(
                Integer.parseInt(endParts[0]) - 1900,
                Integer.parseInt(endParts[1]) - 1,
                Integer.parseInt(endParts[2])
            );

            if (startDate.before(new Date())) {
                System.out.println("Start date cannot be before today!");
                continue;
            }

            // Optional: check for overlaps with existing ranges
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

}
