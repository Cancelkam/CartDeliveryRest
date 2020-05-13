package ru.netology.auth;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthDataTest {

    static class AuthTest {

        @Test
        void shouldBeActiveUser() {
            AuthData user = AuthDataGenerator.generateValidAuthData();
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $("[data-test-id='error-notification']").shouldBe(hidden);
        }

        @Test
        void shouldBeBlockedUser() {
            AuthData user = AuthDataGenerator.generateBlockedAuthData();
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"));
        }

        @Test
        void shouldBeWrongPassword() {
            AuthData user = AuthDataGenerator.generateWrongPasswordAuthData();
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
        }

        @Test
        void shouldBeWronglogin() {
            AuthData user = AuthDataGenerator.generateWrongLoginAuthData();
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
        }

        @Test
        void shouldBeWrongloginAndPassword() {
            AuthData user = AuthDataGenerator.generateWrongLoginAndPasswordAuthData();
            open("http://localhost:9999");
            $("input[name='login']").setValue(user.getLogin());
            $("input[name='password']").setValue(user.getPassword());
            $("button>.button__content").click();
            $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
        }
    }
}