package demo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    public void testCase01() {
        System.out.println("Start Test case: testCase01");
        driver.get("https://leetcode.com/");

        String url = driver.getCurrentUrl();

        if (url.contains("leetcode")) {
            System.out.println("The URL of the Leetcode homepage contains 'leetcode'.");
        } else {
            System.out.println("Not contains 'leetcode'.");
        }
        System.out.println("end Test case: testCase01");
    }

    public void testCase02() {
        System.out.println("Start Test case: testCase02");
        driver.get("https://leetcode.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Click on the "Questions" link on the Leetcode homepage.
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='View Questions ']"))).click();

        // Verify that you are on the problem set page, by checking the URL contains
        // "problemset".
        String url = driver.getCurrentUrl();

        if (url.contains("problemset")) {
            System.out.println("Problem set page");
        } else {
            System.out.println("Not in the problem set page");
        }

        // Retrieve the details of the first 5 questions and print them.
        // find all questions on the page.
        List<WebElement> quest = driver.findElements(
                By.xpath("(//div[@role='rowgroup'])[3]//div[@role='row'][position() >= 2 and position() <= 6]"));

        System.out.println("Details of the first 5 questions : ");

        // array of the titles
        String[] expTitles = { "Two Sum",
                "Add Two Numbers",
                "Longest Substring Without Repeating Characters",
                "Median of Two Sorted Arrays",
                "Longest Palindromic Substring"
        };

        // loop through first 5 questions and print their details
        for (int i = 0; i < Math.min(5, quest.size()); i++) {
            WebElement fiveques = quest.get(i);
            String title = fiveques.getText();

            System.out.println("Question " + (i + 1) + ": " + title);
            System.out.println("Expected Title : " + expTitles[i]);
        }

        System.out.println("Expected Result : " + "The correct details of the problems are obtained and verified.");
        System.out.println("end Test case: testCase02");
    }

    public void testCase03() {
        System.out.println("Start Test case: testCase03");
        driver.get("https://leetcode.com/problemset/");

        // two sum problem
        driver.findElement(By.xpath("//a[text()='Two Sum']")).click();

        String url = driver.getCurrentUrl();

        if (url.contains("two-sum")) {
            System.out.println("The URL of the problem contains 'two-sum'");
        } else {
            System.out.println("The URL of the problem does not contain 'two-sum'");
        }
        System.out.println("end Test case: testCase03");
    }

    public void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");
        driver.get("https://leetcode.com/problems/two-sum/description/");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            // submission tab
            WebElement subTab = driver.findElement(By
                    .xpath("//div[@data-layout-path='/ts0/tb3']"));
            subTab.click();

            // verify
            WebElement dispMsg = wait
                    .until(ExpectedConditions
                            .visibilityOfElementLocated(By.xpath("(//div[@class='flexlayout__tab'])[4]")));
            String msg = dispMsg.getText();

            if (msg.equals("Register or Sign In")) {
                System.out.println(
                        "Register or Sign In" + " " + "is displayed when you click on the submissions tab.");
            } else {
                System.out.println("Register or Sign In is not displayed when you click on submission tab.");
            }
        } catch (Exception e) {
            System.out.println("Exception occured : " + e.getMessage());
        }
        System.out.println("end Test case: testCase04");
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }
}