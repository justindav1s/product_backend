package org.jnd.microservices.quarkus.org.jnd.microservices;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.jnd.microservices.quarkus.product.model.Product;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@QuarkusTest
public class ProductTests {

  private Log log = LogFactory.getLog(ProductTests.class);

  @Test
  public void testRootEndpoint() {
    given().when().get("/products/").then().statusCode(200).body(is("OK"));
  }

  @Test
  public void testHealthEndpoint() {
    given().when().get("/products/health").then().statusCode(200).body(is("OK"));
  }

  @Test
  public void testProductsAllEndpoint() {
    given().when().get("/products/all").then().statusCode(200).body("$.size()", is(18));
  }

  @Test
  public void testProductsAllDataEndpoint() {
    Response response = given().when().get("/products/all").then().statusCode(200).extract().response();

    log.info(response.asString());

    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(response.asString());
      Product[] products = mapper.readValue(response.asString(), Product[].class);
      log.info(node.get(0));
      log.info(products[0]);
      assertThat(products[0].getName(), is("socks"));
      assertThat(products[1].getName(), is("jacket"));

    } catch (IOException e) {
      e.printStackTrace();
    }
            
  } 

  @Test
  public void testProductTypesDataEndpoint() {
    Response response = given().when().get("/products/types").then().statusCode(200).extract().response();

    log.info(response.asString());

    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(response.asString());
      String[] types = mapper.readValue(response.asString(), String[].class);
      log.info(node.get(0));
      log.info(types[0]);
      assertThat(types[0], is("food"));
    } catch (IOException e) {
      e.printStackTrace();
    }
            
  } 

}