package com.bezkoder.spring.hibernate.manytomany;

import com.bezkoder.spring.hibernate.manytomany.model.Tag;
import com.bezkoder.spring.hibernate.manytomany.model.Tutorial;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class EntityDemoTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        System.out.println("Base URI is set to http://localhost:8080/api");
    }

    @Test
    public void TagEntityTest() throws JsonProcessingException {
        // Creating Request Body using
        Tag tag = new Tag();
        tag.setName("NewTag");

        Tutorial tutorial = new Tutorial();
        tutorial.setTitle("New Tutorial");
        tutorial.setDescription("Description of the new tutorial");
        tutorial.setPublished(true);

        // Converting a Java class object to a JSON payload as string
        ObjectMapper objectMapper = new ObjectMapper();;
        String requestJson = objectMapper.writeValueAsString(tutorial);

        System.out.println("*".repeat(20) + "\n" + requestJson + "*".repeat(20) + "\n");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestJson)
                .when()
                .post("/tutorials");

        // Validating Response Body using TUTORIAL ENTITY
        Tutorial responseTutorial = response.as(Tutorial.class);
        MatcherAssert.assertThat(responseTutorial.getTitle(), Matchers.equalTo("New Tutorial"));
        MatcherAssert.assertThat(responseTutorial.getDescription(), Matchers.equalTo("Description of the new tutorial"));

        // Validating Response Body using invalid TAG ENTITY
         Tag responseTag = response.as(Tag.class);
    }
}
