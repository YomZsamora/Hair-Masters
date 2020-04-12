package dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversions {

    private Date booking_date;
    private Date dayOfBooking;

    public DateConversions(String date){
        this.booking_date = convertToUtilDate(date);
    }

    public Date convertToUtilDate(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-d");
        try {
            dayOfBooking = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOfBooking;
    }

    public Date getBooking_date() {
        return booking_date;
    }
}
