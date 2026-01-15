/*

 * DateRange
 * 
 * 14-01-2026
 * 
 */

import java.io.Serializable;
import java.util.Date;

public class DateRange implements Serializable {

    /* DATERANGE ATTRIBUTES */
    private Date start;
    private Date end;

    /* DATERANGE CONSTRUCTOR */
    public DateRange(Date start, Date end) {
        if (start.after(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        this.start = start;
        this.end = end;
    }

    /* GETTER METHODS */
    public Date getStart() { return start; }
    public Date getEnd() { return end; }

    /* HELPER METHODS */
    @Override
    public String toString() {
        return start + " to " + end;
    }
}
