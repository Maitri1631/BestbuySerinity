package com.bestbuyserinity.Bestbuyinfo;

import com.bestbuyserinity.constants.EndPoints;
import com.bestbuyserinity.cucumber.testbase.TestBase;
import com.bestbuyserinity.model.StorePojo;
import com.bestbuyserinity.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yecht.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.hasValue;
import static org.mockito.BDDMockito.then;

@RunWith(SerenityRunner.class)
public class StoreCurdTest extends TestBase {


    static String name="maitri"+ TestUtils.getRandomValue();

    static  String type="bigbox"+TestUtils.getRandomValue();

    static String address="ste anne"+TestUtils.getRandomValue();

    static String address2=" "+TestUtils.getRandomValue();

    static String city="coffer"+TestUtils.getRandomValue();

    static String state="ontario"+TestUtils.getRandomValue();

    static  String zip="p3fg3"+TestUtils.getRandomValue();
    static String hours="Mon: 10-9;"+TestUtils.getRandomValue();

    static Object storesID;

    @Title("This will create a new store")
    @Test
    public void test001(){

        StorePojo pojo=new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress(address);
        pojo.setAddress2(address2);
        pojo.setCity(city);
        pojo.setState(state);
        pojo.setZip(zip);
        pojo.setHours(hours);



       SerenityRest.given()
                .header("Content-Type","application/json")
                .body(pojo)
                .post(EndPoints.CREATE_NEW_STORE)
                .then().log().all().statusCode(201);



    }

    @Title("Verify if store was created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String,?>storeMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_Stores)
                .then().statusCode(200).extract().path(part1+name+part2);
        Assert.assertThat(storeMapData, hasValue(name) );

        String name1="abcd";
        storesID=  storeMapData.get("id");
        System.out.println("ID= "+storesID);
        System.out.println("name = "+storeMapData);
    }

    @Title("Update the store and verify the updated information")
    @Test
    public void test003(){
        name=name+"karan";


        StorePojo pojo=new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress(address);
        pojo.setAddress2(address2);
        pojo.setCity(city);
        pojo.setState(state);
        pojo.setZip(zip);
        pojo.setHours(hours);


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesID)
                .body(pojo)
                .when()
                .put(EndPoints.UPDATE_Stores_BY_ID)
                .then().log().all().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String,?>storeMapData =SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_Stores)
                .then().statusCode(200).extract().path(part1+ name + part2);
        Assert.assertThat(storeMapData, hasValue(name) );


    }
    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){
        SerenityRest.given()
                .pathParam("storesID",storesID)
                .when()
                .delete(EndPoints.DELETE_Stores_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("storesID",storesID)
                .when()
                .get(EndPoints.GET_SINGLE_Stores_BY_ID)
                .then().log().all().statusCode(404);

    }

}
