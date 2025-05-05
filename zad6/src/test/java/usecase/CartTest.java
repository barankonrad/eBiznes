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
public class CartTest extends BarNavigator {

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
    goToCart();
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
  void whenAppStarts_thenCartIsInitiallyEmpty() {
    WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("p")
    ));
    assertThat(emptyCartMessage.isDisplayed()).isTrue();
    assertThat(emptyCartMessage.getText()).isEqualTo("Your cart is currently empty.");
  }

  @Test
  @Order(1)
  void whenProductIsAdded_thenItAppearsInCartView() {
    Product product = Product.builder()
        .id(128L)
        .name("External SSD")
        .price(120.00)
        .categoryId(1L)
        .build();
    API_CLIENT.addProduct(product);
    driver.navigate().refresh();

    goToProducts();
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#root > div > main > div > div > div > button")
    ));
    String productName = driver.findElement(
            By.cssSelector("#root > div > main > div > div > div > h3"))
        .getText();
    addToCartButton.click();
    goToCart();
    WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > ul > li")
    ));
    WebElement quantityElement = cartItem.findElement(By.cssSelector("p:nth-child(4)"));
    WebElement cartItemName = cartItem.findElement(
        By.cssSelector("#root > div > main > div > ul > li > h3"));

    assertThat(cartItemName.getText()).isEqualTo(productName);
    assertThat(quantityElement.getText()).isEqualTo("Quantity: 1");
  }

  @Test
  @Order(2)
  void whenIncreaseQuantityIsClicked_thenProductQuantityIncreases() {
    WebElement quantityElement = driver.findElement(
        By.cssSelector("#root > div > main > div > ul > li > p:nth-child(4)"));
    int initialQuantity = Integer.parseInt(quantityElement.getText().replace("Quantity: ", ""));
    goToProducts();
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#root > div > main > div > div > div > button")
    ));
    addToCartButton.click();
    goToCart();
    WebElement updatedQuantityElement = driver.findElement(
        By.cssSelector("#root > div > main > div > ul > li > p:nth-child(4)"));
    int updatedQuantity = Integer.parseInt(
        updatedQuantityElement.getText().replace("Quantity: ", ""));
    assertThat(updatedQuantity).isEqualTo(initialQuantity + 1);
  }

  @Test
  @Order(3)
  void whenCartHasMultipleSameItem_thenTotalPriceIsCorrectlyDisplayed() {
    WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > h3"))
    );
    WebElement unitPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > ul > li > p:nth-child(3)")));
    WebElement quantityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > ul > li > p:nth-child(4)")
    ));
    double unitPrice = Double.parseDouble(unitPriceElement.getText().replace("Unit price: $", ""));
    int quantity = Integer.parseInt(quantityElement.getText().replace("Quantity: ", ""));
    String displayedTotalText = totalPriceElement.getText().replace("Total cart cost: $", "");
    double displayedTotal = Double.parseDouble(displayedTotalText);
    assertThat(displayedTotal).isEqualTo(unitPrice * quantity);
  }


  @Test
  @Order(4)
  void whenRemoveIsClicked_thenProductIsRemovedFromCart() {
    WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#root > div > main > div > ul > li > button")
    ));
    removeButton.click();
    WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > p")
    ));
    assertThat(emptyCartMessage.isDisplayed()).isTrue();
    assertThat(emptyCartMessage.getText()).isEqualTo("Your cart is currently empty.");
  }

  @Test
  @Order(5)
  void whenCartHasMultipleDifferentItems_thenTotalPriceIsCorrectlyDisplayed() {
    goToProducts();
    Product product = Product.builder()
        .id(821L)
        .name("CD disc")
        .price(80.00)
        .categoryId(1L)
        .build();
    API_CLIENT.addProduct(product);
    driver.navigate().refresh();
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#root > div > main > div > div > div > button")
    ));
    addToCartButton.click();

    goToCart();
    WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > h3"))
    );

    var cartItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.cssSelector("#root > div > main > div > ul > li")));

    double totalPrice = 0.0;
    for (WebElement item : cartItems) {
      WebElement unitPriceElement = item.findElement(By.cssSelector("p:nth-child(3)"));
      WebElement quantityElement = item.findElement(By.cssSelector("p:nth-child(4)"));

      double unitPrice = Double.parseDouble(
          unitPriceElement.getText().replace("Unit price: $", ""));
      int quantity = Integer.parseInt(quantityElement.getText().replace("Quantity: ", ""));
      totalPrice += unitPrice * quantity;
    }

    String displayedTotalText = totalPriceElement.getText().replace("Total cart cost: $", "");
    double displayedTotal = Double.parseDouble(displayedTotalText);
    assertThat(displayedTotal).isEqualTo(totalPrice);
  }

  @Test
  @Order(6)
  void whenAddingLargeQuantity_thenCartHandlesOverflowGracefully() {
    API_CLIENT.clearCart();
    goToProducts();
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#root > div > main > div > div > div > button")
    ));

    int maxTime = 2500;
    for (int i = 0; i < maxTime; i++) {
      addToCartButton.click();
    }

    goToCart();

    WebElement quantityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > ul > li > p:nth-child(4)")
    ));
    int quantity = Integer.parseInt(quantityElement.getText().replace("Quantity: ", ""));
    WebElement unitPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > ul > li > p:nth-child(3)")
    ));
    double unitPrice = Double.parseDouble(unitPriceElement.getText().replace("Unit price: $", ""));
    WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > h3")
    ));
    double totalPrice = Double.parseDouble(
        totalPriceElement.getText().replace("Total cart cost: $", ""));

    assertThat(quantity).isEqualTo(maxTime);
    assertThat(totalPrice).isEqualTo(unitPrice * quantity);
  }
}