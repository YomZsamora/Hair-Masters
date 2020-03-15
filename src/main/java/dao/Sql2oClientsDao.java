package dao;

import models.Clients;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oClientsDao implements ClientDao {

    private final Sql2o sql2o;

    public Sql2oClientsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Clients> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM clients") //raw sql
                    .executeAndFetch(Clients.class); //fetch a list
        }
    }

    @Override
    public void add(Clients client) {
        String sql = "INSERT INTO clients (username, clientName, clientPhone, password) VALUES (:username, :clientName, :clientPhone, :password)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(client) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            client.setClient_id(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }

    }

    @Override
    public Clients findById(int client_id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM clients WHERE client_id = :client_id")
                    .addParameter("client_id", client_id) //key/value pair, key must match above
                    .executeAndFetchFirst(Clients.class); //fetch an individual item
        }
    }


    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllTasks() {

    }
}
