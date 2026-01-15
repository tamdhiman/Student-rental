/*

 * RentalListingRepo
 * 
 * 14-01-2026
 * 
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RentalListingRepo {

    private static int nextListingId = 1;
    private static Map<Integer, RentalListing> listingDatabase = new HashMap<>();
    private static final String fileName = "rental_listings.dat";

    static { // Static initialization to load listings at runtime start
        loadData();
    }

    /* HELPER METHODS */

    public static int generateListingId() {
        return nextListingId++;
    }
    public static void addListing(RentalListing listing) {
        listingDatabase.put(listing.getListingId(), listing);
        saveData();
    }

    /* GETTER METHODS */
    public static RentalListing getListingById(int id) {
        return listingDatabase.get(id);
    }

    public static Map<Integer, RentalListing> getAllListings() {
        return Map.copyOf(listingDatabase);
    }

    public static Map<Integer, RentalListing> getListingsByHomeowner(Homeowner homeowner) {
        Map<Integer, RentalListing> result = new HashMap<>();
        for (RentalListing listing : listingDatabase.values()) {
            if (listing.getHomeowner().equals(homeowner)) {
                result.put(listing.getListingId(), listing);
            }
        }
        return result;
    }

    /* FILE HANDLING */

    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {

            out.writeObject(listingDatabase);
            out.writeInt(nextListingId);

        } catch (IOException e) {
            System.out.println("Failed to save rental listing data.");
        }
    }

    @SuppressWarnings("unchecked") // To block unchecked cast warnings
    private static void loadData() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {

            listingDatabase = (Map<Integer, RentalListing>) in.readObject();
            nextListingId = in.readInt();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load rental listing data.");
        }
    }
}
