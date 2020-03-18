package dao;

import models.Stylists;

import java.util.List;

public interface StylistDao {

    // LIST
    List<Stylists> getAll();

    // CREATE
    void add(Stylists stylist);

    // READ
    Stylists findById(int id);

    // READ
    Stylists findByEmail(String email);

    // READ
    Stylists loginStylist(String username, String password);

    // UPDATE
//    void update(int id, String authorsName);

    // DELETE
    void deleteById(int id);
    void clearAllStylists();
}
