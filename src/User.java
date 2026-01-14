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
        //Fix
        this.securityAnswers = securityAnswers;
    }
    /* HELPER METHODS */

    public String createPasswordHash(String password) {
        /* Temporary hashing, might change according to the LO */
        try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found");
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

    public void resetPassword(String password){ //Is this helpful/protects user?
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
    //Required?
    protected String getPasswordHash() {
        return passwordHash;
    }
    protected Map<String, String> getSecurityAnswers() {
        return securityAnswers;
    }
}
//check version control