package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bookings {

    private int booking_id;
    private LocalDate date;
    private LocalDateTime from;
    private LocalDateTime to;
    private int client_id;
    private int stylist_id;

    public Bookings(int booking_id, LocalDate date, LocalDateTime from, LocalDateTime to, int client_id, int stylist_id) {
        this.booking_id = booking_id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.client_id = client_id;
        this.stylist_id = stylist_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getStylist_id() {
        return stylist_id;
    }
}
