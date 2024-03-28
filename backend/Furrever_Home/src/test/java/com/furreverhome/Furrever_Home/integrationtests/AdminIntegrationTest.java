package com.furreverhome.Furrever_Home.integrationtests;

import com.furreverhome.Furrever_Home.ApiTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Correct static imports for io.restassured.RestAssured and org.hamcrest.Matchers
import static io.restassured.RestAssured.given;

class AdminIntegrationTest {

//    private String token;
//
//    @BeforeEach
//    public void setUp() {
//        token = ApiTestUtils.obtainAccessToken("admin@gmail.com", "Jp@32padhiyar");
//    }
//
//
//    @Test
//    public void testGetAllShelterSuccessThenOK(){
////        when().get("http://localhost:8080/api/admin/shelters").then().statusCode(200);
////        given().auth().preemptive().basic("admin@gmail.com", "Jp@32padhiyar")
////                .when().get("http://localhost:8080/api/admin/shelters")
////                .then().statusCode(200);
//        given().header("Authorization", "Bearer " + token)
//                .when().get("http://localhost:8080/api/admin/shelters")
//                .then().statusCode(200);
//    }
}