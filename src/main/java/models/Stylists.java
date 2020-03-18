package models;

import java.util.Objects;

public class Stylists {

    private int stylist_id;
    private String stylistEmail;
    private String stylistName;
    private String stylistPhone;
    private String stylistDept;
    private String password;
    private boolean role;

    public Stylists(String stylistEmail, String stylistName, String stylistPhone, String stylistDept, String password){
        this.stylistEmail = stylistEmail;
        this.stylistName = stylistName;
        this.stylistPhone = stylistPhone;
        this.stylistDept = stylistDept;
        this.password = password;
        this.role = true;
    }

    public void setStylist_id(int stylist_id) {
        this.stylist_id = stylist_id;
    }

    public int getStylist_id() {
        return stylist_id;
    }

    public String getStylistEmail() {
        return stylistEmail;
    }

    public String getStylistName() {
        return stylistName;
    }

    public String getStylistPhone() {
        return stylistPhone;
    }

    public String getStylistDept() {
        return stylistDept;
    }

    public String getPassword() {
        return password;
    }

    public boolean getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stylists stylists = (Stylists) o;
        return stylist_id == stylists.stylist_id &&
                Objects.equals(stylistEmail, stylists.stylistEmail) &&
                Objects.equals(stylistName, stylists.stylistName) &&
                Objects.equals(stylistPhone, stylists.stylistPhone) &&
                Objects.equals(stylistDept, stylists.stylistDept) &&
                Objects.equals(password, stylists.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stylist_id, stylistEmail, stylistName, stylistPhone, stylistDept, password);
    }
}
