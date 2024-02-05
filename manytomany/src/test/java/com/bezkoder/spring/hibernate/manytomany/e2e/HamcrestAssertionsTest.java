package com.bezkoder.spring.hibernate.manytomany.e2e;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestAssertionsTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        System.out.println("Base URI is set to http://localhost:8080/api");
    }

    @Test
    public void matchersAnyOfHamcrest() {
        given()
                .when()
                .get("/tutorials")
                .then()
                .statusCode(Matchers.anyOf(equalTo(200), equalTo(204))).log().all();
    }

    @Test
    public void bodyObjectAssertionWithHamcrest() {
        Response response = given()
                .when()
                .get("/tutorials")
                .then()
                .statusCode(200).log().all()
                .body("[0]", hasValue("API Test Automation"))
                .extract().response();
        List<Map<String, Object>> responseList = response.body().jsonPath().getList(".");
        assertThat("ID validation with numerical matcher", responseList.get(0).get("id"), equalTo(1));
        assertThat("Entry validation", responseList.get(0), hasEntry("title", "APIs Test Automation"));
        assertThat("Title validation with hasKey matcher",responseList.get(0), hasKey("title"));
        assertThat("Title mismatch", responseList.get(0), hasValue("API Test Automation") );
    }

    // STOP THE APPLICATION
    // ADD A TUTORIAL & ADD A TAG
    @Test
    public void completeHamcrestAssertionUsingObject() {
        // Simulate a response as a list of objects
        List<Map<String, Object>> expectedResponseList = new ArrayList<>();
        // Create expected Tutorial object
        Map<String, Object> expectedResponseObject = new HashMap<>();
        expectedResponseObject.put("id", 1);
        expectedResponseObject.put("title", "API Test Automation");
        expectedResponseObject.put("description", "Comprehensive API Test Automation: From Introduction to Expertise");
        expectedResponseObject.put("published", true);
        // Create expected tags object
        List<Map<String, Object>> expectedTagsList = new ArrayList<>();
        Map<String, Object> tag = new HashMap<>();
        tag.put("id", 1);
        tag.put("name1", "API");
        // Add the created tag object to the tutorial
        expectedTagsList.add(tag);
        expectedResponseObject.put("tags", expectedTagsList);
        expectedResponseList.add(expectedResponseObject);
        System.out.println("*".repeat(50));
        System.out.println("EXPECTED OBJECT");
        System.out.println(expectedResponseList);
        System.out.println("*".repeat(50));
        System.out.println("ACTUAL RESPONSE RETURNED FROM THE SERVER");
        Response response = given()
                .when()
                .get("/tutorials")
                .then().log().body()
                .statusCode(200).extract().response();

        System.out.println("*".repeat(50));
        // Get the actual response list
        List<Map<String, Object>> actualResponseList = response.body().jsonPath().getList(".");

        // Iterate over each expected item and check if it exists in the actual list
        for (Map<String, Object> expectedItem : expectedResponseList) {
            System.out.println("*".repeat(15) + expectedItem);
            assertThat("Response object validation", actualResponseList, hasItem(expectedItem));
        }
        System.out.println("Assertion Passed!");
    }
}