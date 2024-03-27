package com.furreverhome.Furrever_Home;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiTestUtils {

    public static String obtainAccessToken(String email, String password) {
        // Set base URI before running the test
        RestAssured.baseURI = "http://localhost:8080";

        // Define the body of the request
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(RestAssured.baseURI+"/api/auth/signin");

        // Check if the response body is not empty and is a proper JSON
        if(response.body().asString().trim().isEmpty() || !response.contentType().contains("application/json")) {
            throw new IllegalStateException("Response body is empty or not JSON: " + response.body().asString());
        }

        System.out.println(response.asString());
        // Extract token from response
        String token = response.jsonPath().getString("token");

        if(token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("Token was not found in the response body.");
        }

        return token;
    }
}
