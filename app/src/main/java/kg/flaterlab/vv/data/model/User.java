package kg.flaterlab.vv.data.model;


public class User {
    private String username;
    private String name;
    private String uid;
    private boolean isOnline;
    private String email;

    public User() {
    }

    public User(String username, String name, String uid) {
        this.username = username;
        this.name = name;
        this.uid = uid;
    }

    public User(String username, String name, String uid, boolean isOnline, String email) {
        this.username = username;
        this.name = name;
        this.uid = uid;
        this.isOnline = isOnline;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getEmail() {
        return email;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

