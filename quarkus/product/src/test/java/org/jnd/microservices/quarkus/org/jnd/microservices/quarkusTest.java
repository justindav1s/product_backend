package org.jnd.microservices.quarkus.org.jnd.microservices;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class quarkusTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/products/all")
          .then()
             .statusCode(200)
             .body(is("hello")).and();
    }

}