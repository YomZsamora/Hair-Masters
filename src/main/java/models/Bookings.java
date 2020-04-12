package models;

import dates.DateConversions;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Bookings {

    private final static ZoneId TZ = ZoneId.of("Africa/Nairobi");

    private int booking_id;
    private Date booking_date;
    private String start_at;
    private String end_at;
    private String client;
    private String stylist;
    private String stringBookingDate;

    public Bookings(String booking_date, String start_at, String end_at, String client, String stylist) {
        this.booking_date = setBooking_date(booking_date);
        this.start_at = start_at;
        this.end_at = end_at;
        this.client = client;
        this.stylist = stylist;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public Date setBooking_date(String booking_date) {
        DateConversions convertedDate =  new DateConversions(booking_date);
        setStringBookingDate(convertedDate.getBooking_date());
        return convertedDate.getBooking_date();
    }

    public String getStringBookingDate() {
        return stringBookingDate;
    }

    public void setStringBookingDate(Date convertedDate) {
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
//        LocalDate date = convertedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        String stringBookingDate = dateFormat.format(java.sql.Date.valueOf(date));
        String stringBookingDate = DateFormat.getDateInstance(DateFormat.FULL).format(convertedDate);
        this.stringBookingDate = stringBookingDate;
    }

    public String getStart_at() {
        return start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public String getClient() {
        return client;
    }

    public String getStylist() {
        return stylist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookings bookings = (Bookings) o;
        return booking_id == bookings.booking_id &&
                client == bookings.client &&
                stylist == bookings.stylist &&
                Objects.equals(booking_date, bookings.booking_date) &&
                Objects.equals(start_at, bookings.start_at) &&
                Objects.equals(end_at, bookings.end_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id, booking_date, start_at, end_at, client, stylist);
    }
}
