package com.bestbuyserinity.Bestbuyinfo;

import com.bestbuyserinity.Bestbuysteps.ProductSteps;
import com.bestbuyserinity.cucumber.testbase.TestBase;
import com.bestbuyserinity.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCurdTestWithsteps extends TestBase {
    static String name = "maitri" + TestUtils.getRandomValue();
    static String type = "geg" + TestUtils.getRandomValue();
    static String model = "fwerfw" + TestUtils.getRandomValue();
    static int productsId;

    @Steps
    ProductSteps productSteps;

    @Title("create a new product")
    @Test
    public void test001() {

        ValidatableResponse response =productSteps.createProduct( name,  type,  model);
        response.statusCode(201);

    }


    @Title("verify if product is created")
    @Test
    public void test002() {
        HashMap<String,Object> productMapData =productSteps.getProductInfoByName(name);
        Assert.assertThat(productMapData,hasValue(name));
        productsId= (int) productMapData.get("id");
        System.out.println(productsId);

    }

    @Title("update the product information")
    @Test
    public void test003() {
        name = name + "test";

        productSteps.updateProduct(name,productsId);
        HashMap<String,Object> productMap=productSteps.getProductInfoByName(name);
        Assert.assertThat(productMap,hasValue(name));

    }
    @Title("Delete product info by productID and verify its deleted")
    @Test
    public void test004(){

        productSteps.deleteProductInfoByID(productsId).statusCode(200);
        productSteps.getProductsInfoByproductsId(productsId).statusCode(404);

    }

}
