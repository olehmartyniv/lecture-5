package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.ProductData;
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
        actions.openRandomProduct();
        String productURL = driver.getCurrentUrl();

        ProductData product = actions.getOpenedProductInfo();
        ProductData productInCart = actions.orderCreation();

        Assert.assertEquals(productInCart.getName(), product.getName());
        Assert.assertEquals(productInCart.getQty(), 1);
        Assert.assertEquals(productInCart.getPrice(), product.getPrice());

        ProductData productInOrder = actions.checkoutConfirmation();
        String actualMessage = driver.findElement(By.xpath("//h3[@class='h1 card-title']")).getText().substring(1);

        if (driver.findElement(By.id("order-confirmation")).getAttribute("class").matches("(.*)lang-uk(.*)")) {
            String expectedMessage = "ВАШЕ ЗАМОВЛЕННЯ ПІДТВЕРДЖЕНО";
            Assert.assertEquals(actualMessage, expectedMessage);
        } else {
            String expectedMessage = "ВАШ ЗАКАЗ ПОДТВЕРЖДЁН";
            Assert.assertEquals(actualMessage, expectedMessage);
        }

        Assert.assertEquals(productInOrder.getName(), product.getName());
        Assert.assertEquals(productInOrder.getQty(), 1);
        Assert.assertEquals(productInOrder.getPrice(), product.getPrice());

        driver.get(productURL);
        ProductData productChecked = actions.getOpenedProductInfo();

        Assert.assertEquals(productChecked.getName(), product.getName());
        Assert.assertEquals(productChecked.getQty(), product.getQty() - 1);
        Assert.assertEquals(productChecked.getPrice(), product.getPrice());
    }

}
