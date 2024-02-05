package com.bezkoder.spring.hibernate.manytomany.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredAssertionsTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        System.out.println("Base URI is set to http://localhost:8080/api");
    }

    @Test
    public void invalidURLNoFailure() {
        Response response = given()
                .get("/tutorial");
        System.out.println(response.body().prettyPrint());
    }

    @Test
    public void invalidURLAssertion() {
        given().get("/tutorial")
                .then()
                .statusCode(200);
    }

    @Test
    public void headerAssertion() {
        given()
                .when().
                get("/tutorials")
                .then()
                .statusCode(200).log().all()
                .header("Content-Type", String.valueOf(ContentType.JSON));
    }
}