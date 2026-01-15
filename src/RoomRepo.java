/*

 * RoomRepo
 * 
 * 14-01-2026
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomRepo {

    private static int nextRoomId = 1;
    private static Map<Integer, Room> roomDatabase = new HashMap<>();
    private static final String fileName = "rooms.dat";

    static { // Static initialization to load rooms from disk at runtime start
        loadData();
    }

    /* HELPER METHODS */

    public static int generateRoomId() {
        return nextRoomId++;
    }

    public static void addRoom(Room room) {
        roomDatabase.put(room.getRoomId(), room);
        saveData();
    }

   /* GETTER METHODS */

    public static Room getRoomById(int roomId) {
        return roomDatabase.get(roomId);
    }

    public static Map<Integer, Room> getAllRooms() {
        return Map.copyOf(roomDatabase);
    }

    // Get all rooms related to a homeowner
    public static Map<Integer, Room> getRoomsByHomeowner(Homeowner homeowner) {
        Map<Integer, Room> result = new HashMap<>();
        for (Room room : roomDatabase.values()) {
            if (room.getOwner().equals(homeowner)) {
                result.put(room.getRoomId(), room);
            }
        }
        return result;
    }

    public static List<Room> getAvailableRoomsByHomeowner(Homeowner homeowner) {
    List<Room> result = new ArrayList<>();

    for (Room room : roomDatabase.values()) {
        if (room.getOwner().equals(homeowner) && room.isAvailable()) {
            result.add(room);
        }
    }
    return result;
}
    /* FILE HANDLING */

    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {

            out.writeObject(roomDatabase);
            out.writeInt(nextRoomId);

        } catch (IOException e) {
            System.out.println("Failed to save room data.");
        }
    }

    @SuppressWarnings("unchecked") // To block any unchecked cast warnings
    private static void loadData() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {

            roomDatabase = (Map<Integer, Room>) in.readObject();
            nextRoomId = in.readInt();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load room data.");
        }
    }
}
