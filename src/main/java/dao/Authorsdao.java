package dao;

import models.Authors;

import java.util.List;

public interface Authorsdao {
    // LIST
    List<Authors> getAll();

    // CREATE
    void add(Authors task);

    // READ
    Authors findById(int id);

    // UPDATE
    void update(int id, String authorsName);

    // DELETE
    void deleteById(int id);
    void clearAllTasks();
}
