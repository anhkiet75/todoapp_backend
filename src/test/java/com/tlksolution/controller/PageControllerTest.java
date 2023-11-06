package com.tlksolution.controller;

import com.tlksolution.model.Page;
import com.tlksolution.repository.PageRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})

public class PageControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    PageRepository pageRepository;

    // static, all tests share this postgres container
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        pageRepository.deleteAll();


        Page b1 = new Page("test page 1");
        Page b2 = new Page("test page 2");
        Page b3 = new Page("test page 3");
        Page b4 = new Page("test page 4");

        b1.addSubPage(b2);
        b1.addSubPage(b3);

        pageRepository.saveAll(List.of(b1, b2, b3, b4));
    }

    @Test
    void testFindAll() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/pages")
                .then()
                .statusCode(200)    // expecting HTTP 200 OK
                .contentType(ContentType.JSON) // expecting JSON response content
                .body(".", hasSize(4));
    }


    @Test
    public void testDeleteById() {
        Long id = 1L; // replace with a valid ID
        given()
                .pathParam("id", id)
                .when()
                .delete("/api/pages/{id}")
                .then()
                .statusCode(204); // expecting HTTP 204 No Content
    }

    @Test
    public void testCreate() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"test create page\", \"parent_id\": \"1\"}")
                .when()
                .post("/api/pages");
        JsonPath jsonPath = response.jsonPath();
        Integer responseId =((Integer)jsonPath.get("id"));

        response.then()
                .statusCode(201) // expecting HTTP 201 Created
                .contentType(ContentType.JSON); // expecting JSON response content

        // find the new saved book
        given()
                .pathParam("id", responseId)
                .when()
                .get("/api/pages/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(
                        "id", equalTo(responseId),
                        "title", equalTo("test create page")
                );
    }

    /**
     Page b4 = new Page(3L, "test page 4", 2L);
     */
    @Test
    public void testUpdate() {
        Page page1 = pageRepository.findAll().get(0);
        Page page4 = pageRepository.findAll().get(3);
        Long pageId = page4.getId();

        page4.setTitle("updated title");
        page4.setParentPage(page1);
        given()
                .contentType(ContentType.JSON)
                .body(page4)
                .when()
                .put("/api/pages")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        // get the updated book
        Page page = pageRepository.findById(pageId).orElseThrow();
        System.out.println(page);

        assertEquals(pageId, page.getId());
        assertEquals("updated title", page.getTitle());
//        assertTrue(page.getParentPage().equals(page1));
        assertTrue(page1.getSubPage().contains(page));
    }

}
