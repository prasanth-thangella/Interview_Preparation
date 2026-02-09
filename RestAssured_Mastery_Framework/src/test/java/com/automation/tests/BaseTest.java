package com.automation.tests;

import com.automation.reporting.SetupListener;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({ SetupListener.class }) // Attach Extent Report Listener
public class BaseTest {

    @BeforeSuite
    public void setup() {
        // Add Allure Listener to Rest Assured to capture API details in Allure Report
        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
