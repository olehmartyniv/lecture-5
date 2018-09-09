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
        WebElement allProducts = driver.findElement(By.className("all-product-link"));
        allProducts.click();

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

        WebElement about = driver.findElement(By.xpath("//a[@href='#product-details']"));
        about.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='product-quantities']/span"))));

        String name = driver.findElement(By.xpath("//h1[@itemprop='name']")).getText();
        int qty = parseStockValue(driver.findElement(By.xpath("//div[@class='product-quantities']/span")).getText());
        float price = parsePriceValue(driver.findElement(By.xpath("//span[@itemprop='price']")).getText());

        return new ProductData(name, qty, price);
    }

    /**
     * Proceed to order creation, fill required information.
     *
     * @return
     */
    public ProductData orderCreation() {
        WebElement addToCart = driver.findElement(By.xpath("//button[@class='btn btn-primary add-to-cart']"));
        addToCart.click();

        WebElement purchase = driver.findElement(By.xpath("//a[@class='btn btn-primary']"));
        wait.until(ExpectedConditions.visibilityOf(purchase));
        purchase.click();

        String name = driver.findElement(By.xpath("//div[@class='product-line-info']/a[@class='label']")).getText();
        int qty = parseStockValue(driver.findElement(By.name("product-quantity-spin")).getAttribute("value"));
        float price = parsePriceValue(driver.findElement(By.xpath("//span[@class='product-price']")).getText());

        return new ProductData(name, qty, price);
    }
}
