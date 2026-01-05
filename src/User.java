import java.util.Map;

public abstract class User {

    /* USER ATTRIBUTES */

    protected final int user_id;
    protected String[] name;
    protected String email;
    protected String contact_number;
    protected String password_hash;
    protected Map<String, String> security_answers;

    /* USER CONSTRUCTOR */

    public User(int user_id, String first_name, String last_name, String email, String contact_number, String password, Map<String, String> security_answers) {
        this.user_id = user_id;
        this.name = new String[]{first_name, last_name};
        this.email = email;
        this.contact_number = contact_number;
        this.password_hash = createPasswordHash(password);
        //Fix
        this.security_answers = security_answers;
    }
    /* HELPER METHODS */

    public String createPasswordHash(String password) {
        //Implement hashing algo
        return null;
    }

    public String authenticateUser(String password) {
        //Implement authentication?
        return null;
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
    protected void setContactNumber(String contact_number) {
        this.contact_number = contact_number;
    }

    /* GETTER METHODS */

    public int getUserId() {
        return user_id;
    }
    public String[] getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getContact_number() {
        return contact_number;
    }
    //Required?
    protected String getPasswordHash() {
        return password_hash;
    }
    protected Map<String, String> getSecurity_answers() {
        return security_answers;
    }
}
//check version control