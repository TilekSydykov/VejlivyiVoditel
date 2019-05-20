package kg.flaterlab.vv.models;


public class User {
    private String name;
    private String surname;
    private String status;
    private boolean isOnline;
    String email;

    public User() {
    }

    public User(String name, String surname, String status, String email, boolean isOnline) {
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.isOnline = isOnline;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStatus() {
        return status;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

