package dao;

import dates.CurrentDate;
import models.Bookings;
import models.Stylists;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Sql2oBookingsDao implements BookingsDao {

    private final Sql2o sql2o;

    public Sql2oBookingsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Bookings> getAll(Date todaysDate) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM bookings WHERE booking_date >= :todaysDate ORDER BY booking_date ASC") //raw sql
                    .addParameter("todaysDate", todaysDate)
                    .executeAndFetch(Bookings.class); //fetch a list
        }
    }

    @Override
    public List<Bookings> getAllFromStylist(Date todaysDate, String stylistName) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM bookings WHERE booking_date >= :todaysDate AND stylist = :stylistName ORDER BY booking_date ASC") //raw sql
                    .addParameter("todaysDate", todaysDate)
                    .addParameter("stylistName", stylistName)
                    .executeAndFetch(Bookings.class); //fetch a list
        }
    }

    @Override
    public void add(Bookings booking) {
        String sql = "INSERT INTO bookings (booking_date, start_at, end_at, client, stylist) VALUES (:booking_date, :start_at, :end_at, :client, :stylist)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .addParameter("booking_date", booking.getBooking_date())
                    .addParameter("start_at", booking.getStart_at())
                    .addParameter("end_at", booking.getEnd_at())
                    .addParameter("client", booking.getClient())
                    .addParameter("stylist", booking.getStylist())
//                    .bind(booking)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            booking.setBooking_id(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }

    }

    @Override
    public List<Bookings> findByDate(Date date) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM bookings WHERE booking_date = :date") //raw sql
                    .addParameter("date", date)
                    .executeAndFetch(Bookings.class); //fetch a list
        }
    }

    @Override
    public void deleteFrom(Date todaysDate) {
        String sql = "DELETE from bookings WHERE booking_date < :todaysDate";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("todaysDate", todaysDate)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllBookings() {

    }
}
