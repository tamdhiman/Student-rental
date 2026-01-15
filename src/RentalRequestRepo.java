/*

 * RentalRequestRepo
 * 
 * 14-01-2026
 * 
 */

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RentalRequestRepo {

    private static int nextRequestId = 1;
    private static Map<Integer, RentalRequest> requestDatabase = new HashMap<>();
    private static final String fileName = "rental_requests.dat";

    static { // Static initialization to load requests at runtime start
        loadData();
    }

    /* HELPER METHODS */

    public static int generateRequestId() {
        return nextRequestId++;
    }

    public static void addRequest(RentalRequest request) {
        requestDatabase.put(request.getRequestId(), request);
        saveData();
    }

    public static void updateRequest(RentalRequest request) {
    requestDatabase.put(request.getRequestId(), request);
    saveData();
}


    /* GETTER METHODS */
    
    public static RentalRequest getRequestById(int requestId) {
        return requestDatabase.get(requestId);
    }

    public static List<RentalRequest> getAllRequests() {
        return List.copyOf(requestDatabase.values());
    }

    public static List<RentalRequest> getRequestsByStudent(Student student) {
        return requestDatabase.values()
                .stream()
                .filter(r -> r.getStudent().getUserId() == student.getUserId())
                .collect(Collectors.toList());
    }

   public static List<RentalRequest> getRequestsByHomeowner(Homeowner homeowner) {
    return requestDatabase.values()
            .stream()
            .filter(r -> r.getHomeowner().getUserId() == homeowner.getUserId())
            .collect(Collectors.toList());
}


    /* FILE HANDLING */

    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {

            out.writeObject(requestDatabase);
            out.writeInt(nextRequestId);

        } catch (IOException e) {
            System.out.println("Failed to save rental request data.");
        }
    }

    @SuppressWarnings("unchecked") // To block unchecked cast warnings
    private static void loadData() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {

            requestDatabase = (Map<Integer, RentalRequest>) in.readObject();
            nextRequestId = in.readInt();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load rental request data.");
        }
    }
}
