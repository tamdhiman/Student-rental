import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public abstract class User {

    /* USER ATTRIBUTES */

    protected final int userId;
    protected String[] name;
    protected String email;
    protected String contactNumber;
    protected String passwordHash;
    protected Map<String, String> securityAnswers;

    /* USER CONSTRUCTOR */

    public User(int userId, String firstName, String lastName, String email, String contactNumber, String password, Map<String, String> securityAnswers) {
        this.userId = userId;
        this.name = new String[]{firstName, lastName};
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

    private boolean authenticateUser(String password) {
        String inputPasswordHash = createPasswordHash(password);
        return this.passwordHash.equals(inputPasswordHash);
   }

    /* SETTER METHODS */

    protected void setFirstName(String name) {
        this.name[0] = name;
    }
    protected void setLastName(String name) {
        this.name[1] = name;
    }
    protected void setEmail(String email) {
        this.email = email;
    }
    protected void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /* GETTER METHODS */

    public int getUserId() {
        return userId;
    }
    public String[] getName() {
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