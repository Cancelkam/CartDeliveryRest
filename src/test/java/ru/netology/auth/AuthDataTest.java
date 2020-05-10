package ru.netology.auth;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthDataTest {

    static class AuthTest {

        @Test
        void shouldBeActiveUser() {
            AuthData user = AuthDataGenerator.Generate("active");
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
        }

        @Test
        void shouldBeBlockedUser() {
            AuthData user = AuthDataGenerator.Generate("blocked");
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $(withText("Пользователь заблокирован"));
        }
        @Test
        void shouldBeWrongPassword() {
            AuthData user = AuthDataGenerator.Generate("active");
            user.setPassword("123");
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $(withText("Неверно указан логин или пароль"));
        }

        @Test
        void shouldBeWronglogin() {
            AuthData user = AuthDataGenerator.Generate("active");
            user.setLogin("Вася");
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $(withText("Неверно указан логин или пароль"));
        }
    }
}