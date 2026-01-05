import java.util.Date;

public class RentalRequest {
    int user_id;
    int room_id;
    Date start_date;
    Date end_date;
    public RentalRequest(int user_id, int room_id, Date start_date, Date end_date) {
        this.user_id = user_id;
        this.room_id = room_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
