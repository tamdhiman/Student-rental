import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RentalRequestRepo {

    private static Map<Integer, RentalRequest> requestDatabase = new HashMap<>();
    private static int nextRequestId = 1;
    private static final String fileName = "rental_requests.dat";

    /* Load data when class is first used */
    static {
        loadData();
    }

    /* ID generation */
    public static int generateRequestId() {
        return nextRequestId++;
    }

    /* Add a new request */
    public static void addRequest(RentalRequest request) {
        requestDatabase.put(request.getRequestId(), request);
        saveData();
    }

    /* Find by ID */
    public static RentalRequest getRequestById(int requestId) {
        return requestDatabase.get(requestId);
    }

    /* Get all requests (admin use later) */
    public static List<RentalRequest> getAllRequests() {
        return List.copyOf(requestDatabase.values());
    }

    /* Requests made by a student */
    public static List<RentalRequest> getRequestsByStudent(Student student) {
        return requestDatabase.values()
                .stream()
                .filter(r -> r.getStudent().getUserId() == student.getUserId())
                .collect(Collectors.toList());
    }

    /* Requests received by a homeowner */
    public static List<RentalRequest> getRequestsByHomeowner(Homeowner homeowner) {
        return requestDatabase.values()
                .stream()
                .filter(r -> r.getHomeowner().getUserId() == homeowner.getUserId())
                .collect(Collectors.toList());
    }

    /* Persist data */
    private static void saveData() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(requestDatabase);
            out.writeInt(nextRequestId);

        } catch (IOException e) {
            System.out.println("Failed to save rental request data.");
        }
    }

    /* Load data */
    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            requestDatabase = (Map<Integer, RentalRequest>) in.readObject();
            nextRequestId = in.readInt();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load rental request data.");
        }
    }
}
