package usecase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

abstract class BarNavigator {

  protected WebDriver driver;

  protected void goToPayment() {
    driver.findElement(By.xpath("//button[text()='Payment']")).click();
  }

  protected void goToCart() {
    driver.findElement(By.xpath("//button[text()='Cart']")).click();
  }

  protected void goToProducts() {
    driver.findElement(By.xpath("//button[text()='Products']")).click();
  }

}
