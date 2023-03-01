package com.bestbuyserinity.cucumber.testbase;

import com.bestbuyserinity.constants.Path;
import com.bestbuyserinity.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;


public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
        //RestAssured.basePath  = Path.PRODUCTS; // dont need for bestBuy
        //http://localhost:8080/student
    }

}
