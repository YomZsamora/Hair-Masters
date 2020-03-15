package dao;

import models.Authors;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oAuthorsDao implements Authorsdao {

    private final Sql2o sql2o;

    public Sql2oAuthorsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Authors> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM authors") //raw sql
                    .executeAndFetch(Authors.class); //fetch a list
        }
    }

    @Override
    public void add(Authors authors) {
        String sql = "INSERT INTO authors (authorName) VALUES (:authorName)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(authors) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            authors.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }

    }

    @Override
    public Authors findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM authors WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Authors.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String authorsName) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllTasks() {

    }
}
