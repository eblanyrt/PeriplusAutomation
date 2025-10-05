package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class PeriplusMathius {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void goToPeriplus() {
        driver.get("https://www.periplus.com");
    }

    @Test(priority = 2)
    public void login() throws InterruptedException {
        driver.findElement(By.linkText("Sign In")).click();
        Thread.sleep(2000);
        driver.findElement(By.name("email")).sendKeys("example@mail.com");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.id("button-login")).click();
    }

    @Test(priority = 3)
    public void findBook() throws InterruptedException {
        WebElement searchInput = driver.findElement(By.name("filter_name"));
        searchInput.clear();
        searchInput.sendKeys("jojo's bizarre adventure");
        Thread.sleep(2000);
        searchInput.sendKeys(Keys.ENTER);
    }

    @Test(priority = 4)
    public void clickBook() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.className("product-img")).click();
    }

    @Test(priority = 5)
    public void addToCart() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.className("btn-add-to-cart")).click();
    }

    @Test(priority = 6)
    public void showCart() throws InterruptedException {
        Thread.sleep(5000);

        // Coba tutup modal kalau ada
        try {
            WebElement closeModal = driver.findElement(By.cssSelector("#Notification-Modal .close"));
            closeModal.click();
            Thread.sleep(1000);
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            System.out.println("Modal tidak ditemukan atau sudah tertutup.");
        }

        WebElement showCartClick = driver.findElement(By.id("show-your-cart"));
        showCartClick.click();
    }

    String titleText;
    String productUrl;

    @Test(priority = 7)
    public void checkCartContent() {
        WebElement productElement = driver.findElement(By.xpath("//p[@class='product-name limit-lines']/a"));
        titleText = productElement.getText();
        productUrl = productElement.getAttribute("href");
    }

    @Test(priority = 8)
    public void validateContent() {
        Assert.assertTrue(titleText.contains("Jojo's Bizarre Adventure"), "❌ Produk tidak ditemukan di cart.");
        System.out.println("✅ Judul produk di cart: " + titleText);
        System.out.println("✅ Link produk: " + productUrl);
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
