import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private final List<RentalRequest> rental_requests = new ArrayList<>();
    private final List<RentalBooking> rental_history = new ArrayList<>();

    public Student(int user_id, String first_name, String last_name, String email, String contact_number, String password, Map<String, String> security_answers) {
        super(user_id, first_name, last_name, email, contact_number, password, security_answers);
    }


    public void create_request(int room_id, Date start_date, Date end_date) throws IllegalArgumentException{
        // Add more conditionals
        if(start_date.after(end_date)){
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        rental_requests.add(new RentalRequest(this.user_id, room_id, start_date, end_date));
    }

    /* UPDATE HISTORY */
    public void add_rental_history(RentalBooking booking) {
        rental_history.add(booking);
    }
    public List<RentalRequest> getRentalRequests() {
        return List.copyOf(rental_requests);
    }

    public List<RentalBooking> getRentalHistory() {
        return List.copyOf(rental_history);
    }
}