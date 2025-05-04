package usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import usecase.utils.WebDriverHandler;

@TestMethodOrder(MethodOrderer.class)
public class RoutingTest extends BarNavigator {

  private WebDriverWait wait;
  private static final String BASE_URL = "http://localhost:3000";

  @BeforeEach
  @SneakyThrows
  void setUp() {
    driver = WebDriverHandler.getWebDriver();
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    driver.get(BASE_URL);
  }

  @AfterEach
  void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  @Order(0)
  void whenNavigatingBetweenRoutes_thenCorrectComponentIsRendered() {
    goToCart();
    WebElement cartHeader = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
    assertThat(cartHeader.getText()).isEqualTo("Shopping Cart");

    goToPayment();
    WebElement paymentHeader = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
    assertThat(paymentHeader.getText()).isEqualTo("Payment");

    goToProducts();
    WebElement productsHeader = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
    assertThat(productsHeader.getText()).isEqualTo("Product List");
  }

  @Test
  @Order(1)
  void whenLeavingPaymentPage_thenInputResets() {
    goToPayment();

    WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")
    ));
    inputField.sendKeys("12345");

    goToProducts();
    goToPayment();

    inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")
    ));
    assertThat(inputField.getText()).isEmpty();
  }
}