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
import java.util.Map;
import java.util.Scanner;

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

         int userChoice = sc.nextInt();
         sc.nextLine();

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
         System.out.println("4. Exit this simulation");
         
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
        runUserPage(tempUser);
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

      String newPassword = getPasswordInput("Enter a new password:"); //the new password should not be equal to the last/current password
      user.setPassword(newPassword);
      return true;
   }

   /*User Pages/Applications */

   //Should I pass the user in argument or use one currentUser. What is better practise and why?
   public void runUserPage(User user){
      //Get the type of User and run corresponding method
   }

   public void homeownerPage(User user){
      // Allow them to 
   }

}
