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

    public static String passwordGenerator(){
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password(8,15);
        return password;
    }

    private static void createRequest(AuthData user) {
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
    
    public static AuthData Generate(String status) {
        String login = loginGenerator();
        String password = passwordGenerator();
        AuthData authData = new AuthData(login,password,status);
        createRequest(authData);
        return authData;
    }
}
