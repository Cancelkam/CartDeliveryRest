package ru.netology.auth;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class AuthDataGenerator {
    public static String loginGenerator(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        return login;
    }

    public static String wrongLoginGenerator(){
        Faker faker = new Faker(new Locale("ru"));
        String login = faker.name().firstName();
        return login;
    }

    public static String passwordGenerator(){
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password(8,15);
        return password;
    }

    public static String wrongPasswordGenerator(){
        Faker faker = new Faker(new Locale("ru"));
        String password = faker.internet().password(3,5);
        return password;
    }

    public static AuthData generateValidAuthData() {
        String login = loginGenerator();
        String password = passwordGenerator();
        AuthData authData = new AuthData(login,password,"active");
        userRegistration(authData);
        return authData;
    }

    public static AuthData generateBlockedAuthData() {
        String login = loginGenerator();
        String password = passwordGenerator();
        AuthData authData = new AuthData(login,password,"blocked");
        userRegistration(authData);
        return authData;
    }

    public static AuthData generateWrongLoginAuthData() {
        String login = wrongLoginGenerator();
        String password = passwordGenerator();
        AuthData authData = new AuthData(login,password,"active");
        return authData;
    }

    public static AuthData generateWrongPasswordAuthData() {
        String login = loginGenerator();
        String password = wrongPasswordGenerator();
        AuthData authData = new AuthData(login,password,"active");
        return authData;
    }

    public static AuthData generateWrongLoginAndPasswordAuthData() {
        String login = wrongLoginGenerator();
        String password = wrongPasswordGenerator();
        AuthData authData = new AuthData(login,password,"active");
        return authData;
    }

    private static void userRegistration(AuthData user) {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        given()
                .spec(requestSpec)
                .body(user)
                .when() // "когда"
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}
