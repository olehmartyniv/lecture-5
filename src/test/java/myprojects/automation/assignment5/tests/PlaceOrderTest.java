package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.utils.Properties;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        driver.get(Properties.getBaseUrl());
        if (isMobileTesting) Assert.assertTrue(driver.findElement(By.id("_mobile_cart")).isDisplayed());
        else Assert.assertTrue(driver.findElement(By.id("_desktop_cart")).isDisplayed());
    }

    @Test
    public void createNewOrder() {
        // TODO implement order creation test

        // open random product

        // save product parameters

        // add product to Cart and validate product information in the Cart

        // proceed to order creation, fill required information

        // place new order and validate order summary

        // check updated In Stock value
    }

}
