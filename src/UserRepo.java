import java.util.HashMap;
import java.util.Map;

public class UserRepo {
    private static int next_user_id = 1;
    private static final Map<Integer, User> user_database = new HashMap<>();

    public static int generateUserId() {
        return next_user_id++;
    }

    public static void addUser(User current_user) {
        user_database.put(current_user.getUser_id(), current_user);
    }

    public static User findUser(int user_id, User requesting_user) {
       if (requesting_user instanceof Administration) {
           return user_database.get(user_id);
       }
       /*
       throw new SecurityException("Access denied");
        */
       // Neccessary?
       if (requesting_user.getUserId() == user_id) {
            return requesting_user;
       }
       return null;
    }
    // We have a list in Admin, use?
    public static Map<Integer, User> findAllUsers(User requester) {
        if (requester instanceof Administration) {
            return Map.copyOf(user_database);
        }
        throw new SecurityException("Access denied");
    }

}
