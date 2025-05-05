package usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.Duration;
import java.util.List;
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
public class ProductTest extends BarNavigator {

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
  void whenOnProductsPage_thenHeaderCorrectlyDisplays() {
    WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

    assertThat(header.getText()).isEqualTo("Product List");
    assertThat(header.isDisplayed()).isTrue();
  }

  @Test
  @Order(1)
  void whenProductListIsLoaded_thenItIsVisibleAndContainsNoProducts() {
    WebElement productList = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("div[style*='display: flex'][style*='gap: 1rem;']")
    ));

    assertThat(productList.isDisplayed()).isTrue();

    List<WebElement> products = productList.findElements(
        By.cssSelector("div[style*='border: 1px solid']"));
    assertThat(products).isEmpty();
  }

  @Test
  @Order(2)
  void whenPageLoads_thenProductsAreFetchedFromAPIAndDisplayed() {
    Product product = Product.builder()
        .id(128L)
        .name("External SSD")
        .price(120.00)
        .categoryId(1L)
        .build();
    API_CLIENT.addProduct(product);
    driver.navigate().refresh();

    WebElement productList = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("div[style*='display: flex'][style*='gap: 1rem;']")
    ));

    List<WebElement> products = productList.findElements(
        By.cssSelector("div[style*='border: 1px solid']"));
    WebElement productNameElement = products.getFirst().findElement(By.cssSelector("h3"));
    WebElement productPriceElement = products.getFirst()
        .findElement(By.cssSelector("p:nth-of-type(1)"));
    WebElement productCategoryElement = products.getFirst()
        .findElement(By.cssSelector("p:nth-of-type(2)"));

    assertThat(products).hasSize(1);
    assertThat(productNameElement.getText()).isEqualTo("External SSD");
    assertThat(productPriceElement.getText()).isEqualTo("Price: $120");
    assertThat(productCategoryElement.getText()).contains("Category ID: 1");
  }

  @Test
  @Order(3)
  void whenProductIsRendered_thenItHasNamePriceAndCategory() {
    WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("div[style*='border: 1px solid']")
    ));

    WebElement productName = product.findElement(By.cssSelector("h3"));
    assertThat(productName.getText()).isNotEmpty();

    WebElement productPrice = product.findElement(By.cssSelector("p:nth-of-type(1)"));
    assertThat(productPrice.getText()).matches("^Price: \\$\\d+(\\.\\d{1,2})?$");

    WebElement productCategory = product.findElement(By.cssSelector("p:nth-of-type(2)"));
    assertThat(productCategory.getText()).contains("Category ID: ");
  }

  @Test
  @Order(4)
  void whenProductIsRendered_thenAddToCartButtonIsPresent() {
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//*[@id=\"root\"]/div/main/div/div/div/button")
    ));

    assertThat(addToCartButton.isDisplayed()).isTrue();
    assertThat(addToCartButton.isEnabled()).isTrue();
  }

  @Test
  @Order(5)
  void whenMultipleProductsInDatabase_thenAllProductsAreDisplayed() {
    Product firstItem = Product.builder()
        .id(1L)
        .name("First item")
        .price(1.00)
        .categoryId(1L)
        .build();
    Product secondItem = Product.builder()
        .id(2L)
        .name("Second item")
        .price(2.00)
        .categoryId(2L)
        .build();
    API_CLIENT.addProduct(firstItem);
    API_CLIENT.addProduct(secondItem);
    driver.navigate().refresh();

    WebElement productList = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("#root > div > main > div > div")));
    List<WebElement> products = productList.findElements(
        By.cssSelector("div[style*='border: 1px solid']"));

    assertThat(products).hasSizeGreaterThanOrEqualTo(2);
    List<String> productNames = products.stream()
        .map(product -> product.findElement(By.cssSelector("h3")).getText())
        .toList();
    List<String> prices = products.stream()
        .map(product -> product.findElement(By.cssSelector("p:nth-of-type(1)")).getText())
        .toList();
    List<String> categories = products.stream()
        .map(product -> product.findElement(By.cssSelector("p:nth-of-type(2)")).getText())
        .toList();

    assertThat(productNames).contains("First item");
    assertThat(prices).contains("Price: $1");
    assertThat(categories).contains("Category ID: 1");

    assertThat(productNames).contains("Second item");
    assertThat(prices).contains("Price: $2");
    assertThat(categories).contains("Category ID: 2");
  }
}