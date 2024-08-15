package com.example.exchangerate.service;

import com.example.exchangerate.enums.Currencys;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Value("${chrome.driver.path}")
    private String chromeDriverPath;

    public List<Map<String, String>> getExchangeRate(Currencys currency) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        LocalDate fiveDaysAgo = LocalDate.now().minusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fiveDaysAgoFormatted = fiveDaysAgo.format(formatter);

        List<Map<String, String>> rates = new ArrayList<>();
        try {
            driver.get("https://www.esunbank.com/zh-tw/personal/deposit/rate/forex/exchange-rate-chart");

            WebElement currencySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currency")));

            //選擇幣別
            Select select = new Select(currencySelectElement);
            select.selectByVisibleText(currency.getDisplayName());
            wait.until(ExpectedConditions.textToBePresentInElement(currencySelectElement, currency.getDisplayName()));

            //修改起始日期
            WebElement fromDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fromDate")));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].value = '" + fiveDaysAgoFormatted + "';", fromDateInput);

            //開始查詢
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn")));
            submitButton.click();

            Thread.sleep(1000);

            //點數據表
            WebElement dataSheetTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-2")));
            dataSheetTab.click();

            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table")));

            List<WebElement> rows = table.findElements(By.cssSelector("tr"));

            int count = 0;
            for (WebElement row : rows) {
                try {
                    WebElement dateElement = row.findElement(By.cssSelector("td.table-text-center"));
                    String date = dateElement.getText();

                    List<WebElement> cells = row.findElements(By.cssSelector("td.table-text-right"));
                    if (!cells.isEmpty()) {
                        String rate = cells.get(1).getText().split("\\s+")[0];

                        if (!rate.isEmpty()) {
                            rates.add(Map.of("date", date, "rate", rate));
                            count++;
                        }
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }

                if (count >= 5) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
        return rates;
    }
}
