/*

 * User
 * 
 * 14-01-2026
 * 
 */

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class User implements Serializable {

    /* USER ATTRIBUTES */

    private static final long serialVersionUID = 1L;
    private static final Random randomGenerator = new Random();
    protected final int userId;
    protected String name;
    protected String email;
    protected String contactNumber;
    protected String passwordHash;
    protected Map<String, String> securityAnswers;

    /* USER CONSTRUCTOR */

    public User(int userId, String name,  String email, String contactNumber, String password, Map<String, String> securityAnswers) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.passwordHash = createPasswordHash(password);
        this.securityAnswers = securityAnswers;
    }

    /* HELPER METHODS */

    // Converts password (stored in bytes) to password hash (stored in hex string). Transforms input by performing some bitwise opperations
    public String createPasswordHash(String password) {
        try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing Algo not found");
        }
    }

    public boolean authenticateUser(String password) {
        String inputPasswordHash = createPasswordHash(password);
        return this.passwordHash.equals(inputPasswordHash);
   }

    public String getRandomSecurityQuestion() {
        List<String> questions = List.copyOf(securityAnswers.keySet());
        return questions.get(randomGenerator.nextInt(questions.size()));
    }

   public boolean verifySecurityAnswer(String question, String answer) {
        return securityAnswers.get(question).equalsIgnoreCase(answer);
    }

    public void resetPassword(String password){ 
        setPassword(password);
    }

    /* SETTER METHODS */

    protected void setName(String name){
        this.name = name;
    }
    protected void setEmail(String email) {
        this.email = email;
    }
    protected void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    protected void setPassword(String password){
        this.passwordHash = createPasswordHash(password);
    }

    /* GETTER METHODS */

    public int getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    protected String getPasswordHash() {
        return passwordHash;
    }
    protected Map<String, String> getSecurityAnswers() {
        return securityAnswers;
    }
}
