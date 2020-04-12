package dao;

import models.Stylists;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oStylistsDao implements StylistDao {

    private final Sql2o sql2o;

    public Sql2oStylistsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Stylists> getAll(String value) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM stylists WHERE role = :value") //raw sql
                    .addParameter("value", value)
                    .executeAndFetch(Stylists.class); //fetch a list
        }
    }

    @Override
    public Stylists loginStylist(String stylistEmail, String password) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM stylists WHERE stylistEmail = :stylistEmail AND password = :password")
                    .addParameter("stylistEmail", stylistEmail) //key/value pair, key must match above
                    .addParameter("password", password) //key/value pair, key must match above
                    .executeAndFetchFirst(Stylists.class); //fetch an individual item
        }
    }

    @Override
    public void add(Stylists stylist) {
        String sql = "INSERT INTO stylists (stylistEmail, stylistName, stylistPhone, stylistDept, password, role) VALUES (:stylistEmail, :stylistName, :stylistPhone, :stylistDept, :password, :role)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(stylist) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            stylist.setStylist_id(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }

    }

    @Override
    public Stylists findById(int stylist_id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM stylists WHERE stylist_id = :stylist_id")
                    .addParameter("stylist_id", stylist_id) //key/value pair, key must match above
                    .executeAndFetchFirst(Stylists.class); //fetch an individual item
        }
    }

    @Override
    public Stylists findByEmail(String stylistEmail) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM stylists WHERE stylistEmail = :stylistEmail")
                    .addParameter("stylistEmail", stylistEmail) //key/value pair, key must match above
                    .executeAndFetchFirst(Stylists.class); //fetch an individual item
        }
    }


    @Override
    public void deleteById(int stylist_id) {
        String sql = "DELETE from stylists WHERE stylist_id=:stylist_id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("stylist_id", stylist_id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAllStylists() {

    }
}
