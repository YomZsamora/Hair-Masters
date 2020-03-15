package dao;

import models.Authors;
import models.Clients;

import java.util.List;

public interface ClientDao {
    // LIST
    List<Clients> getAll();

    // CREATE
    void add(Clients client);

    // READ
    Clients findById(int id);

    // UPDATE
//    void update(int id, String authorsName);

    // DELETE
    void deleteById(int id);
    void clearAllTasks();
}
