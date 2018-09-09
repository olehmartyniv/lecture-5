package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        actions.openRandomProduct();

        ProductData product = actions.getOpenedProductInfo();
        ProductData productInCart = actions.orderCreation();

        Assert.assertEquals(product.getName(), productInCart.getName());
        Assert.assertEquals(1, productInCart.getQty());
        Assert.assertEquals(product.getPrice(), productInCart.getPrice());

        WebElement checkout = driver.findElement(By.xpath("//div[@class='checkout cart-detailed-actions card-block']/div/a"));
        checkout.click();
        // proceed to order creation, fill required information

        // place new order and validate order summary

        // check updated In Stock value
    }

}
