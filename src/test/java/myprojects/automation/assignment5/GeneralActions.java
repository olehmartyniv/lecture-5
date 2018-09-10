package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static myprojects.automation.assignment5.utils.DataConverter.*;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openRandomProduct() {
        driver.findElement(By.className("all-product-link")).click();

        List<WebElement> products = driver.findElements(By.xpath("//h1[@class='h3 product-title']/a"));
        products.get((int) ((Math.random() * products.size()))).click();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");

        driver.findElement(By.xpath("//a[@href='#product-details']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='product-quantities']/span"))));

        String name = driver.findElement(By.xpath("//h1[@itemprop='name']")).getText();
        int qty = parseStockValue(driver.findElement(By.xpath("//div[@class='product-quantities']/span")).getText());
        float price = parsePriceValue(driver.findElement(By.xpath("//span[@itemprop='price']")).getText());

        driver.findElement(By.xpath("//button[@class='btn btn-primary add-to-cart']")).click();

        WebElement purchase = driver.findElement(By.xpath("//a[@class='btn btn-primary']"));
        wait.until(ExpectedConditions.visibilityOf(purchase));
        purchase.click();

        return new ProductData(name, qty, price);
    }

    /**
     * Proceed to order creation.
     *
     * @return
     */
    public ProductData orderCreation() {
        String name = driver.findElement(By.xpath("//div[@class='product-line-info']/a[@class='label']")).getText();
        int qty = parseStockValue(driver.findElement(By.name("product-quantity-spin")).getAttribute("value"));
        float price = parsePriceValue(driver.findElement(By.xpath("//span[@class='product-price']")).getText());

        driver.findElement(By.xpath("//div[@class='checkout cart-detailed-actions card-block']/div/a")).click();

        return new ProductData(name.toUpperCase(), qty, price);
    }

    /**
     * Fill required information.
     *
     * @return
     */
    public ProductData checkoutConfirmation() {
        driver.findElement(By.name("firstname")).sendKeys("John");
        driver.findElement(By.name("lastname")).sendKeys("Doe");
        driver.findElement(By.name("email")).sendKeys("john.doe" + (int) (Math.random()* 100) + "@mail.com");
        driver.findElement(By.name("continue")).click();

        driver.findElement(By.name("address1")).sendKeys("2121 W Harrison St");
        driver.findElement(By.name("postcode")).sendKeys("60612");
        driver.findElement(By.name("city")).sendKeys("Chicago");
        driver.findElement(By.name("confirm-addresses")).click();

        driver.findElement(By.name("confirmDeliveryOption")).click();
        driver.findElement(By.id("payment-option-1")).click();
        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
        driver.findElement(By.xpath("//div[@id='payment-confirmation']/div/button")).click();

        String name = driver.findElement(By.xpath("//div[@class='col-sm-4 col-xs-9 details']/span")).getText().replaceFirst(" - Size :(.*)", "").toUpperCase();
        int qty = parseStockValue(driver.findElement(By.xpath("//div[@class='col-xs-2']")).getText());
        float price = parsePriceValue(driver.findElement(By.xpath("//div[@class='col-xs-5 text-xs-right bold']")).getText());

        return new ProductData(name, qty, price);
    }
}
