import java.util.Date;

public class RentalRequest {
    int userId;
    int roomId;
    Date startDate;
    Date endDate;
    public RentalRequest(int userId, int roomId, Date startDate, Date endDate) {
        this.userId = userId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
