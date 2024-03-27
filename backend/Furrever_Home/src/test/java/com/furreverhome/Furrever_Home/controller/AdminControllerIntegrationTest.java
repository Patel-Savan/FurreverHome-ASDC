package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.ApiTestUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

// Correct static imports for io.restassured.RestAssured and org.hamcrest.Matchers
import static io.restassured.RestAssured.given;

class AdminControllerIntegrationTest {

    private String token;

    @BeforeEach
    public void setUp() {
        token = ApiTestUtils.obtainAccessToken("admin@gmail.com", "Jp@32padhiyar");
    }


    @Test
    public void testGetAllShelterSuccessThenOK(){
//        when().get("http://localhost:8080/api/admin/shelters").then().statusCode(200);
//        given().auth().preemptive().basic("admin@gmail.com", "Jp@32padhiyar")
//                .when().get("http://localhost:8080/api/admin/shelters")
//                .then().statusCode(200);
        given().header("Authorization", "Bearer " + token)
                .when().get("http://localhost:8080/api/admin/shelters")
                .then().statusCode(200);
    }
}





//package com.furreverhome.Furrever_Home.controller;
//
//import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
//import com.furreverhome.Furrever_Home.enums.Role;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test") // Activates the "test" profile
//public class AdminControllerIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void getAllShelters_ReturnsSheltersList() {
//        ResponseEntity<?> response = restTemplate.getForEntity("/api/admin/shelters", ShelterResponseDto[].class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
////        assertThat(response.getBody().length).isGreaterThan(0); // Adjust assertion based on your data setup
//    }
//
//    @Test
//    public void changeVerifiedStatus_UpdatesStatus() {
//        // Assuming there's a shelter with email "test@example.com" and we want to change its status.
//        // This test might require setting up test data that matches these criteria.
//        String email = "test@example.com";
//        String status = "Approve"; // Assuming 'verified' is a valid status.
//        ResponseEntity<?> response = restTemplate.getForEntity("/api/admin/shelter/{email}/{status}", Void.class, email, status);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        // Additional assertions can be made here if the service method has noticeable side effects (e.g., querying the database to confirm the status change).
//    }
//
//    // Additional tests as needed...
//}
