import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserRepo {
    private static int nextUserId = 1;
    private static  Map<Integer, User> userDatabase = new HashMap<>();
    private static final String fileName = "users.dat";
    
    static { //Explain static
        loadData();
    }


    public static int generateUserId() {
        return nextUserId++;
    }
    public static void addUser(User newUser) {
        userDatabase.put(newUser.getUserId(), newUser);
        saveData();
    }

    /* Find User Methods */
    public static User findUser(String credentials) {
    for (User user : userDatabase.values()) {
        if (user.getEmail().equalsIgnoreCase(credentials)) {
            return user;
        }
        else if(user.getName().equalsIgnoreCase(credentials)){
            return user;
        }
    }
    return null;
    }
    public static User findUserById(int userId) {
        return userDatabase.get(userId);
    }

    public static User findUserAdmin(int userId, User requesting_user) {
       if (requesting_user instanceof Administration) {
           return userDatabase.get(userId);
       }
       /*
       throw new SecurityException("Access denied");
        */
       // Neccessary?
       if (requesting_user.getUserId() == userId) {
            return requesting_user;
       }
       return null;
    }
    // We have a list in Admin, use?
    public static Map<Integer, User> findAllUsers(User requester) {
        if (requester instanceof Administration) {
            return Map.copyOf(userDatabase);
        }
        throw new SecurityException("Access denied");
    }


    /* FILE HANDLING */


    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {

            out.writeObject(userDatabase);
            out.writeInt(nextUserId);

        } catch (IOException e) {
            System.out.println("Failed to save user data.");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {

            userDatabase = (Map<Integer, User>) in.readObject();
            nextUserId = in.readInt();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load user data.");
        }
    }

}
