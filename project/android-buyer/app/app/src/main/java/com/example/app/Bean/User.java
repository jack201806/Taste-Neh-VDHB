package com.example.app.Bean;

public class User {

    private int id;
    private String username;
//    private String password;
    private String pwd;
    private String phone;
    private String user_icon_src;
//    private int role;

//    public User(int id, String username, String password, int role) {
//        super();
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }

    public User(int id, String username, String pwd, String phone, String user_icon_src) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.phone = phone;
        this.user_icon_src = user_icon_src;
    }

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getRole() {
//        return role;
//    }
//
//    public void setRole(int role) {
//        this.role = role;
//    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_icon_src() {
        return user_icon_src;
    }

    public void setUser_icon_src(String user_icon_src) {
        this.user_icon_src = user_icon_src;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", user_icon_src='" + user_icon_src + '\'' +
                '}';
    }
}
