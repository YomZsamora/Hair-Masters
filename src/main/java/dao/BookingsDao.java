package dao;

import models.Bookings;

import java.util.Date;
import java.util.List;

public interface BookingsDao {

    // LIST
    List<Bookings> getAll(Date todaysDate);

    // LIST
    List<Bookings> getAllFromStylist(Date todaysDate, String stylistName);

    // CREATE
    void add(Bookings booking);

    // READ
    Bookings findById(int id);


    // UPDATE
//    void update(int id, String authorsName);

    // DELETE
    void deleteFrom(Date todaysDate);
    void clearAllBookings();
}
