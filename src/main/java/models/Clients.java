package models;

import java.util.ArrayList;
import java.util.Objects;

public class Clients {
    private String username;
    private String clientName;
    private String clientPhone;
    private String password;
    private int client_id;

    public Clients(String username, String clientName, String clientPhone, String password){
        this.username = username;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.password = password;
    }

    public int getClient_id() {
        return client_id;
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

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clients clients = (Clients) o;
        return client_id == clients.client_id &&
                username.equals(clients.username) &&
                clientName.equals(clients.clientName) &&
                clientPhone.equals(clients.clientPhone) &&
                password.equals(clients.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, clientName, clientPhone, password, client_id);
    }
}
