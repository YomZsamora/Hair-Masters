package dao;

import models.Clients;

import java.util.List;

public interface ClientDao {
    // LIST
    List<Clients> getAll();

    // CREATE
    void add(Clients client);

    // READ
    Clients findById(int id);

    // READ
    Clients findByUsername(String username);

    // READ
    Clients loginClient(String username, String password);

    // UPDATE
//    void update(int id, String authorsName);

    // DELETE
    void deleteById(int id);
    void clearAllTasks();
}
