import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RoomRepo {
    private static Map<Integer, Room> roomDatabase = new HashMap<>();
    private static int nextRoomId = 1; // Auto-increment room IDs
    private static final String fileName = "rooms.dat";

    // Load data on startup
    static {
        loadData();
    }

    /** Generate a new unique Room ID */
    public static int generateRoomId() {
        return nextRoomId++;
    }

    /** Add a room to the repo and save */
    public static void addRoom(Room room) {
        roomDatabase.put(room.getRoomId(), room);
        saveData();
    }

    /** Get a room by its ID */
    public static Room getRoomById(int roomId) {
        return roomDatabase.get(roomId);
    }

    /** Get all rooms (read-only) */
    public static Map<Integer, Room> getAllRooms() {
        return Map.copyOf(roomDatabase);
    }

    /** Get all rooms belonging to a particular homeowner */
    public static Map<Integer, Room> getRoomsByHomeowner(Homeowner homeowner) {
        Map<Integer, Room> result = new HashMap<>();
        for (Room room : roomDatabase.values()) {
            if (room.getOwnerId() == homeowner.getUserId()) {
                result.put(room.getRoomId(), room);
            }
        }
        return result;
    }

    /** Save all rooms to disk */
    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(roomDatabase);
            out.writeInt(nextRoomId);
        } catch (IOException e) {
            System.out.println("Failed to save room data.");
        }
    }

    /** Load rooms from disk */
    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            roomDatabase = (Map<Integer, Room>) in.readObject();
            nextRoomId = in.readInt();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load room data.");
        }
    }
}
