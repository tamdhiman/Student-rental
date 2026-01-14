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
      String firstName = getInput("First name:");
      String lastName = getInput("Last name:");
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

         int userChoice = sc.nextInt();
         sc.nextLine();
         
         switch (userChoice) {
            case 1 -> newUser = new Student(newUserId, firstName, lastName, email, contactNumber, password, answers);
            case 2 -> newUser = new Homeowner(newUserId, firstName, lastName, email, contactNumber, password, answers);
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
      //Go where?
   }

   private void login(){
      while(true){
         System.out.println("Enter your name or email");
         String loginUsername = sc.nextLine(); //add so it ignores capitalization 
         //checks UserRepo to get the User object
         
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


   private void checkSecurityQuestions(){
      //Add logic
   }

   private void resetPassword(){
      // Add logic
   }
}
