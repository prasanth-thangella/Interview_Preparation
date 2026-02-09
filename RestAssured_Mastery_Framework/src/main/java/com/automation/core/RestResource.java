package com.automation.core;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.automation.utils.ConfigManager;

public class RestResource {

    // Initialize Base URI globally or per request
    static {
        RestAssured.baseURI = ConfigManager.getConfig().baseUri();
    }

    private static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .contentType("application/json")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .relaxedHTTPSValidation()
                .log().all(); // Log all requests
    }

    public static Response post(String path, Object payload) {
        return getRequestSpec()
                .body(payload)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response get(String path) {
        return getRequestSpec()
                .when()
                .get(path)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response put(String path, Object payload) {
        return getRequestSpec()
                .body(payload)
                .when()
                .put(path)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response delete(String path) {
        return getRequestSpec()
                .when()
                .delete(path)
                .then()
                .log().all()
                .extract().response();
    }
}
