package usecase.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import lombok.SneakyThrows;
import usecase.model.Product;

public class ApiClient {

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;
  private static final String BASE_URL = "http://localhost:9000";

  public ApiClient() {
    this.httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();
    this.objectMapper = new ObjectMapper();
  }

  @SneakyThrows
  public HttpResponse<String> addProduct(Product product) {
    String jsonBody = objectMapper.writeValueAsString(product);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/item"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }

  @SneakyThrows
  public boolean ifPaymentExists(String cardNumber) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/payments"))
        .header("Content-Type", "application/json")
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

    return response.body().contains(cardNumber);
  }

  @SneakyThrows
  public List<Product> getProducts() {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/items"))
        .header("Content-Type", "application/json")
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new RuntimeException(
          "Failed to fetch products. HTTP status code: " + response.statusCode());
    }

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(
        response.body(),
        new TypeReference<>() {
        }
    );
  }

  @SneakyThrows
  public void clearCart() {
    HttpRequest clearCartRequest = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/cart/clear"))
        .DELETE()
        .build();

    HttpResponse<String> clearCartResponse = httpClient.send(clearCartRequest,
        BodyHandlers.ofString());
    if (clearCartResponse.statusCode() != 200) {
      throw new RuntimeException(
          "Failed to clear cart. HTTP status code: " + clearCartResponse.statusCode());
    }
  }

  @SneakyThrows
  public void deleteAllItems() {
    HttpRequest getItemsRequest = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/items"))
        .header("Content-Type", "application/json")
        .GET()
        .build();

    HttpResponse<String> getItemsResponse = httpClient.send(getItemsRequest,
        BodyHandlers.ofString());
    if (getItemsResponse.statusCode() != 200) {
      throw new RuntimeException(
          "Failed to fetch items. HTTP status code: %d".formatted(getItemsResponse.statusCode()));
    }

    List<Product> products = objectMapper.readValue(getItemsResponse.body(), new TypeReference<>() {
    });

    for (Product product : products) {
      HttpRequest deleteItemRequest = HttpRequest.newBuilder()
          .uri(URI.create(BASE_URL + "/item?id=" + product.id()))
          .DELETE()
          .build();

      HttpResponse<String> deleteItemResponse = httpClient.send(deleteItemRequest,
          BodyHandlers.ofString());
      if (deleteItemResponse.statusCode() != 200) {
        throw new RuntimeException(
            "Failed to delete item with ID %d. HTTP status code: %d".formatted(
                product.id(), deleteItemResponse.statusCode())
        );
      }
    }
  }
}