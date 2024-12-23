package com.example.LMS.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class loginData {

    public loginData() {}
    public loginData(String username, String password ,String email) {
        this.username = username;
        this.password = password;
        this.email = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getUsername() {
        return  username;
    }

    private String username;
    private String password;
    private String  email;

    public void setPassword(String password) {
        this.password = password;
    }
}