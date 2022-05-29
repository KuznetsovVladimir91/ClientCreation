package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {


    private static Faker faker = new Faker(new Locale("en"));


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static String getLogin() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password();
    }


    public static void Registration(RegistrationInfo info) {

        given()
                .spec(requestSpec)
                .body(new RegistrationInfo(info.getLogin(), info.getPassword(), info.getStatus()))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationInfo getUser(String status) {
        return new RegistrationInfo(getLogin(), getPassword(), status);
    }

    public static RegistrationInfo getRegisteredUser(String status) {
        RegistrationInfo registeredUser = getUser(status);
        Registration(registeredUser);
        return registeredUser;
    }


}
