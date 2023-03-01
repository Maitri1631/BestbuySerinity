package com.bestbuyserinity.Bestbuyinfo;

import com.bestbuyserinity.Bestbuysteps.StoreSteps;
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

public class StoreCurdTestwithSteps extends TestBase {
    static String name="maitri"+ TestUtils.getRandomValue();

    static  String type="bigbox"+TestUtils.getRandomValue();
    static int storesID;

    @Steps
    StoreSteps storeSteps;

    @Title(" create a new store")
    @Test
    public void test001() {


        ValidatableResponse response =storeSteps.CreateStore(name,type);
        response.statusCode(201);


    }

    @Title("verify if store is created")
    @Test
    public void test002() {
        HashMap<String,Object> storeMapData =storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMapData,hasValue(name));
        storesID= (int) storeMapData.get("id");
        System.out.println(storesID);

    }

    @Title("update the store information")
    @Test
    public void test003() {
        name = name + "king";

        storeSteps.UpdateStore(name,storesID);
        HashMap<String,Object> storeMap=storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMap,hasValue(name));

    }


    @Title("Delete store info by StoreID and verify its deleted")
    @Test
    public void test004(){

        storeSteps.deleteStoreInfoByID(storesID).statusCode(200);
        storeSteps.getstoreInfoBystoreId(storesID).statusCode(404);

    }

}
