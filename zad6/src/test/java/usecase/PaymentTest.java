package usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.Duration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import usecase.model.Product;
import usecase.utils.ApiClient;
import usecase.utils.WebDriverHandler;

@TestMethodOrder(OrderAnnotation.class)
public class PaymentTest extends BarNavigator {

  private WebDriverWait wait;
  private static final ApiClient API_CLIENT = new ApiClient();
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

  @AfterAll
  static void cleanUp() {
    API_CLIENT.clearCart();
    API_CLIENT.deleteAllItems();
  }

  @Test
  @Order(0)
  void whenEnteringPayment_thenInputIsEmptyAndHasPlaceholder() {
    goToPayment();

    WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")
    ));
    assertThat(inputField.getText()).isEmpty();
    String placeholderText = inputField.getDomAttribute("placeholder");
    assertThat(placeholderText).isNotEmpty();
    assertThat(placeholderText).isEqualTo("1234 5678 9876 5432");
  }

  @Test
  @Order(1)
  void whenSubmitEmptyPaymentForm_thenValidationErrorsAreShown() {
    goToPayment();

    WebElement submitButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.cssSelector("#root > div > main > div > button")));
    submitButton.click();

    WebElement cardNumberError = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#root > div > main > div > p:nth-child(5)")));
    assertThat(cardNumberError.getText()).isEqualTo("Please enter a card number.");
    assertThat(cardNumberError.isDisplayed()).isTrue();
  }

  @Test
  @Order(2)
  void whenPaymentIsSuccessful_thenCartIsEmptied() {
    goToPayment();

    WebElement cardNumberField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")));
    cardNumberField.sendKeys("1234567887654321");

    WebElement submitButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
    submitButton.click();

    goToCart();

    WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > p")));
    assertThat(emptyCartMessage.getText()).isEqualTo("Your cart is currently empty.");
  }

  @Test
  @Order(3)
  void whenSubmittingValidPayment_thenSuccessMessageIsShown() {
    goToPayment();
    WebElement cardNumberField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")));
    cardNumberField.sendKeys("1234567887654321");

    WebElement submitButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.cssSelector("#root > div > main > div > button")));
    submitButton.click();

    WebElement cardNumberError = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#root > div > main > div > p:nth-child(5)")));
    assertThat(cardNumberError.getText()).isEqualTo("Payment successful!");
    assertThat(cardNumberError.isDisplayed()).isTrue();
  }

  @Test
  @Order(4)
  @SneakyThrows
  void whenPaymentIsSubmitted_thenPOSTRequestIsSentToAPI() {
    goToPayment();

    WebElement cardNumberField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")));
    String cardNumber = String.format("%016d", (long) (Math.random() * 10000000000000000L));
    cardNumberField.sendKeys(cardNumber);

    WebElement submitButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.cssSelector("#root > div > main > div > button")));
    submitButton.click();
    Thread.sleep(1000);

    assertThat(API_CLIENT.ifPaymentExists(cardNumber)).isTrue();
  }

  @Test
  @Order(5)
  void whenPaymentIsUnsuccessful_thenCartIsNotEmptied() {
    goToProducts();
    Product product = Product.builder()
        .id(128L)
        .name("External SSD")
        .price(120.00)
        .categoryId(1L)
        .build();
    API_CLIENT.addProduct(product);
    driver.navigate().refresh();
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#root > div > main > div > div > div > button")
    ));
    addToCartButton.click();
    goToPayment();

    WebElement cardNumberField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#root > div > main > div > div > label > input[type=\"text\"]")));
    cardNumberField.sendKeys("");

    WebElement submitButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
    submitButton.click();

    goToCart();

    var cartItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.cssSelector("#root > div > main > div > ul > li")));
    assertThat(cartItems).isNotEmpty();
  }
}