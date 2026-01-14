import java.util.HashMap;
import java.util.Map;

public class UserRepo {
    private static int nextUserId = 1;
    private static final Map<Integer, User> userDatabase = new HashMap<>();

    public static int generateUserId() {
        return nextUserId++;
    }

    public static void addUser(User newUser) {
        userDatabase.put(newUser.getUserId(), newUser);
    }

    /* Find User Methods */
    public static User findUserCredentials(String credentials) {
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

}
