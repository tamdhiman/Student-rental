import java.io.Serializable;
import java.util.Date;

public class DateRange implements Serializable {
    private Date start;
    private Date end;

    public DateRange(Date start, Date end) {
        if (start.after(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        this.start = start;
        this.end = end;
    }

    public Date getStart() { return start; }
    public Date getEnd() { return end; }

    @Override
    public String toString() {
        return start + " to " + end;
    }
}
