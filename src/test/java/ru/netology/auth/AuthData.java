package ru.netology.auth;


import lombok.Data;

@Data
    public class AuthData {
        private String login;
        private String password;
        private String status;

    public AuthData(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }
}
