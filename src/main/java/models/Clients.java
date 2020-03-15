package models;

import java.util.ArrayList;

public class Clients {
    private String username;
    private String clientName;
    private String clientPhone;
    private String password;
    private static ArrayList<Clients> allClients = new ArrayList<>();

    public Clients(String username, String clientName, String clientPhone, String password){
        this.username = username;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.password = password;
        allClients.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<Clients> getAllClients() {
        return allClients;
    }

}
