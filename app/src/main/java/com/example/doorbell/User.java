package com.example.doorbell;

public class User {
    String login_id , name , email_id , password , phone_no , address ,  role ,status, DP;


    public User() {
    }

    public User(String login_id, String name, String email_id, String password, String phone_no, String address, String role, String status, String DP) {
        this.login_id = login_id;
        this.name = name;
        this.email_id = email_id;
        this.password = password;
        this.phone_no = phone_no;
        this.address = address;
        this.role = role;
        this.status = status;
        this.DP = DP;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDP() {
        return DP;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }
}
