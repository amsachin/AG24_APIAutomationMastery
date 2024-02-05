package com.bezkoder.spring.hibernate.manytomany.e2e;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JSONPathDemoTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        System.out.println("Base URI is set to http://localhost:8080/api");
    }

    @Test
    public void jsonPathRoot() {
        Response response = given()
                .when()
                .get("/tutorials")
                .then().log().body()
                .statusCode(200)
                .extract().response();

        // Extract the entire JSON response as a list of objects
        List<Map<String, Object>> responseList = response.body().jsonPath().getList(".");
        // Print the entire JSON response
        System.out.println("*".repeat(50));
        System.out.println("Entire JSON response");
        System.out.println("*".repeat(50));
        System.out.println("All Tags: " + responseList);

        // Extract all tags using JSONPath
        List<String> allTags = response.jsonPath().getList("tags");
        System.out.println("*".repeat(50));
        System.out.println("Print the list of tags");
        // Print the list of tags
        System.out.println("*".repeat(50));
        System.out.println("All Tags: " + allTags);

        // Extract all tags names using JSONPath
        List<String> allTagsNames = response.jsonPath().getList("tags.name");

        System.out.println("*".repeat(50));
        // Print the list of tags names
        System.out.println("Print the list of tags names");
        System.out.println("*".repeat(50));
        System.out.println("All Tags Names: " + allTagsNames);


        // Search for the "API" tag name within the "tags" array using JSONPath
        List<String> titlesWithSpringTag = response.jsonPath().getList("findAll { it.tags.name.contains('API') }.title");

        // Print the titles that have the "API" tag
        System.out.println("Titles with 'API' tag: " + titlesWithSpringTag);
    }

    @Test
    public void jsonPathGetAllTags() {
        Response response = given()
                .when()
                .get("/tutorials")
                .then()
                .statusCode(200)
                .extract().response();

        // Extract all tags using JSONPath
        List<Map<String, Object>> responseList = response.body().jsonPath().getList(".");

        // Iterate through the response objects and collect the tags from each object
        for (Map<String, Object> responseObject : responseList) {
            System.out.println("*".repeat(50));
            System.out.println(responseObject);
            List<Object> tagsList = (List<Object>) responseObject.get("tags");
            System.out.println("-".repeat(30));
            System.out.println("tagsList.size():" + tagsList.size());
            System.out.println(tagsList);
        }
    }
}
