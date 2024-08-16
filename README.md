# exchange_rate_project

這是一個用於從玉山銀行網站抓取匯率數據的爬蟲程式。它使用Spring Boot作為後端框架，並透過Selenium WebDriver來自動化瀏覽器操作。前端則使用Chart.js來顯示匯率變化圖表。

## 功能
- 抓取並顯示美元 (USD)、人民幣 (CNY)、日圓 (JPY)、歐元 (EUR)、澳幣 (AUD) 的匯率數據。
- 使用Chart.js繪製折線圖，顯示最近五天的匯率變化。

## 安裝與運行

### 1. clone project
```bash
git clone https://github.com/yunghsuan36/exchange_rate.git
```

### 2. properties setting
```properties
chrome.driver.path=/your/path/chromedriver

```
### 3. 建置與運行專案
``` bash
   mvn clean install
   mvn spring-boot:run
```
### 4. 存取應用程式
   在瀏覽器中打開以下網址：
``` bash
http://localhost:8080/forexRate
```
