package com.devices;

import com.devices.Fixtures.DeviceFixtures;
import com.devices.dto.DeviceCreationRequest;
import com.devices.entities.Device;
import com.devices.exception.DeviceNotFoundException;
import com.devices.repository.DeviceRepository;
import com.devices.service.IDeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DeviceControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Perform find all when no id or param was not provided")
    public void findAll() throws Exception {
        loadTest();
        when()
                .get("/devices")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("page.size", equalTo(5))
                .body("content", not(empty()));
    }

    @Test
    @DisplayName("Perform find by id and return only one element")
    public void findById() throws Exception {
        loadTest();
        when()
                .get("/devices/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("page.size", equalTo(5))
                .body("content", not(empty()))
                .body("content", hasSize(1));
    }

    @Test
    @DisplayName("Perform find by brand and return only one element")
    public void findByBrand() throws Exception {
        loadTest();
        when()
                .get("/devices?brand=test")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("page.size", equalTo(5))
                .body("content", not(empty()))
                .body("content", hasSize(1));
    }

    @Test
    @DisplayName("Perform find by id that doesn't exist and return empty array")
    public void emptyCollection() throws Exception {
        loadTest();
        when()
                .get("/devices/3")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("page.size", equalTo(5))
                .body("content", empty());
    }

    @Test
    @DisplayName("Perform find by brand that doesn't exist and return empty array")
    public void emptyCollectionByBrand() throws Exception {
        loadTest();
        when()
                .get("/devices?brand=xpto")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("page.size", equalTo(5))
                .body("content", empty());
    }

    @Test
    @DisplayName("Perform post request to create a new element")
    public void insertNewElement() throws JsonProcessingException {

        DeviceCreationRequest newDeviceRequest = DeviceFixtures.createNewDeviceRequest("end2end", "end2end");

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(newDeviceRequest))
        .when()
                .post("/devices")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", not(empty()))
                .body("id", notNullValue())
                .body("brand", equalTo("end2end"))
                .body("name", equalTo("end2end"));
    }

    @Test
    @DisplayName("Perform post request with invalid payload should throw exception")
    public void inertInvalidPayload() throws JsonProcessingException {

        DeviceCreationRequest newDeviceRequest = DeviceFixtures.createNewDeviceRequest("", "");

        given()
                .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsString(newDeviceRequest))
                .when()
        .post("/devices")
                .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors", not(empty()))
                .body("errors", hasSize(2));

    }

    @Test
    @DisplayName("Perform put request with valid payload should update entity")
    public void updatePayload() throws JsonProcessingException {
        loadTest();
        DeviceCreationRequest newDeviceRequest = DeviceFixtures.createNewDeviceRequest("newValue", "newValue");

        given()
                .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsString(newDeviceRequest))
                .when()
        .put("/devices/1")
                .then()
        .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("newValue"))
                .body("brand", equalTo("newValue"));

        Device device = deviceRepository.findById(1).get();
        assertThat(device.getName(), equalTo("newValue"));
        assertThat(device.getBrand(), equalTo("newValue"));

    }

    @Test
    @DisplayName("Perform put request with invalid payload should throw bad request")
    public void updateInvalidPayload() throws JsonProcessingException {
        loadTest();
        DeviceCreationRequest newDeviceRequest = DeviceFixtures.createNewDeviceRequest("", "");

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(newDeviceRequest))
            .when()
                .put("/devices/1")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors", not(empty()))
                .body("errors", hasSize(2));

        Device device = deviceRepository.findById(1).get();
        assertThat(device.getName(), equalTo("test"));
        assertThat(device.getBrand(), equalTo("test"));

    }

    @Test
    @DisplayName("Perform put request with invalid id should throw not found")
    public void updateNonexistentPayload() throws JsonProcessingException {
        loadTest();
        DeviceCreationRequest newDeviceRequest = DeviceFixtures.createNewDeviceRequest("newValue", "newValue");

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(newDeviceRequest))
                .when()
                .put("/devices/10")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        Device device = deviceRepository.findById(1).get();
        assertThat(device.getName(), equalTo("test"));
        assertThat(device.getBrand(), equalTo("test"));

    }


    @Test
    @DisplayName("Perform patch request with valid payload should update the field")
    public void updatePartialPayload() throws JsonProcessingException {
        loadTest();

        given()
                .contentType(ContentType.JSON)
                .body("[{\n" +
                        "        \"op\": \"replace\",\n" +
                        "        \"path\": \"/name\",\n" +
                        "        \"value\": \"xpto\"\n" +
                        "    }]")
                .when()
                .patch("/devices/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("xpto"))
                .body("brand", equalTo("test"));

        Device device = deviceRepository.findById(1).get();
        assertThat(device.getName(), equalTo("xpto"));
        assertThat(device.getBrand(), equalTo("test"));

    }

    @Test
    @DisplayName("Perform put request with invalid payload should throw bad request")
    public void updatePartialInvalidPayload() throws JsonProcessingException {
        loadTest();
        DeviceCreationRequest newDeviceRequest = DeviceFixtures.createNewDeviceRequest("", "");

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(newDeviceRequest))
            .when()
                .patch("/devices/1")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors", not(empty()))
                .body("errors", hasSize(2));

        Device device = deviceRepository.findById(1).get();
        assertThat(device.getName(), equalTo("test"));
        assertThat(device.getBrand(), equalTo("test"));

    }

    @Test
    @DisplayName("Perform put request with invalid id should throw not found")
    public void updatePartialNonexistentPayload() throws JsonProcessingException {
        loadTest();
        given()
                .contentType(ContentType.JSON)
                .body("[{\n" +
                        "        \"op\": \"replace\",\n" +
                        "        \"path\": \"/name\",\n" +
                        "        \"value\": \"xpto\"\n" +
                        "    }]")
            .when()
                .patch("/devices/10")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        Device device = deviceRepository.findById(1).get();
        assertThat(device.getName(), equalTo("test"));
        assertThat(device.getBrand(), equalTo("test"));

    }

    @Test
    @DisplayName("When perform delete by ID should return ok")
    public void deleteById() {
        loadTest();
        when()
            .delete("/devices/1")
            .then()
                .statusCode(HttpStatus.OK.value());

        Assertions.assertThrows(DeviceNotFoundException.class, () -> {
            deviceRepository.findById(1).orElseThrow(DeviceNotFoundException::new);
        });
    }

    private void loadTest() {
        deviceRepository.save(new Device(1, "test", "test", LocalDateTime.now()));
    }
}
