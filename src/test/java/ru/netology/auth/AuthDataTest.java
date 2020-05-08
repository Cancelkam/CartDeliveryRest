package ru.netology.auth;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class AuthDataTest {

    static class AuthTest {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        @BeforeAll
        static void setUpAll() {
            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(new AuthData("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
    }
        @BeforeAll
        static void setUpAllSad() {
        given()
                    .spec(requestSpec)
                    .body(new AuthData("lena", "password1", "blocked"))
                .when() // "когда"
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
    }

        @Test
        void shouldBeHappyPath() {
            open("http://localhost:9999");
            $("input[name='login']").setValue("vasya");
            $("input[name='password']").setValue("password");
            $("button>.button__content").click();
        }

        @Test
        void shouldBeSadPath() {
            open("http://localhost:9999");
            $("input[name='login']").setValue("lena");
            $("input[name='password']").setValue("password1");
            $("button>.button__content").click();
            $(withText("Пользователь заблокирован"));
        }
        @Test
        void shouldBeWrongPassword() {
            open("http://localhost:9999");
            $("input[name='login']").setValue("vasya");
            $("input[name='password']").setValue("password2");
            $("button>.button__content").click();
            $(withText("Неверно указан логин или пароль"));
        }

        @Test
        void shouldBeWronglogin() {
            open("http://localhost:9999");
            $("input[name='login']").setValue("lena1");
            $("input[name='password']").setValue("password1");
            $("button>.button__content").click();
            $(withText("Неверно указан логин или пароль"));
        }
    }
}