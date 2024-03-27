package com.furreverhome.Furrever_Home.integrationtests;

import com.furreverhome.Furrever_Home.ApiTestUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.given;

public class AuthenticationIntegrationTest {

    private final String baseURL = "http://localhost:8080";

    @Test
    public void testShelterSigninSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "hal@shel.com", "jay1234");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseURL+"/api/auth/signin");

        String token = response.jsonPath().getString("token");
        String userRole = response.jsonPath().getString("userRole");
        assertNotNull(token);
        assertEquals("SHELTER", userRole);
    }

    @Test
    public void testPetAdopterSigninSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "savanpatel5666@gmail.com", "jay1234");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseURL+"/api/auth/signin");

        String token = response.jsonPath().getString("token");
        String userRole = response.jsonPath().getString("userRole");
        assertNotNull(token);
        assertEquals("PETADOPTER", userRole);
    }

    @Test
    public void testSigninUserNotVerfiedThenNoToken(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "pateljay1502@gmail.com", "jay1234");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseURL+"/api/auth/signin");

        String token = response.jsonPath().getString("token");
        boolean verifedStatus = response.jsonPath().getBoolean("verified");
        assertNull(token);
        assertFalse(verifedStatus);
    }

    @Test
    public void testSigninShelterVerfiedButNotApprovedThenNoToken(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "haldd@shel.com", "jay1234");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseURL+"/api/auth/signin");

        String message = response.jsonPath().getString("message");
        assertEquals("Admin approval pending..", message);
    }
}
