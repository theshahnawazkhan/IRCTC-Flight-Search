package IrctcSearch;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class IrctcFlightSearchAutomation {
	public static WebDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	@Test
	public void flightSearch() throws InterruptedException, IOException {
		Scanner sc = new Scanner(System.in);
		String browserName = sc.nextLine();
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Shahnawaz Khan\\eclipse-workspace\\IrctcFlight\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\Shahnawaz Khan\\eclipse-workspace\\IrctcFlight\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		// Navigate to the webpage
		driver.get("https://www.air.irctc.co.in/ ");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Selecting from city
		WebElement fromBox = driver.findElement(By.id("stationFrom"));
		fromBox.sendKeys("Hyd");
		driver.findElement(By.xpath("//div[text()='Hyderabad (HYD)']")).click();

		// Selecting to city
		WebElement toBox = driver.findElement(By.id("stationTo"));
		toBox.sendKeys("Pune (PNQ)");
		WebElement destination = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Pune (PNQ)')]")));
		destination.click();

		// Selecting origin date
		driver.wait(10);
		driver.findElement(By.xpath("//input[@id= 'originDate']")).click();
		driver.findElement(By.xpath("//span[@class='act active-red']")).click();

		// Selecting passengers and class
		driver.findElement(By.id("noOfpaxEtc")).click();
		WebElement dt = driver.findElement(By.id("travelClass"));
		Select slt = new Select(dt);
		slt.selectByIndex(1);
		// To click On Search
		driver.findElement(By.xpath("//button[text()='Search ']")).click();

		// To verify the result
		List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='right-searchbarbtm']"));
		int i = 0;
		for (WebElement searchResult : searchResults) {
			i = i + 1;
			if (searchResult.findElement(By.xpath(".//span[text()='Hyderabad (HYD)']")).isDisplayed()) {
				System.out.println("Hyderabad is diplayed as the source city for search result number:" + i);

				// report pass
			} else {
				// report fail
				System.out.println("Hyderabad is not diplayed as the source city for search result number");

			}
			if (searchResult.findElement(By.xpath(".//span[text()='Pune (PNQ)']")).isDisplayed()) {
				System.out.println("Pune is displayed as the destination city for search result number:" + i);

				// report pass

			} else {

				// report fail
				System.out.println("Pune is not displayed as the destination city for search result number:");

			}

		}

		String msg1 = driver.findElement(By.xpath(".//div/a/span")).getText();
		System.out.println("Validating the Date");
		System.out.println(msg1);

		// To display the name and Number of available Flights on the console

		List<WebElement> list = driver.findElements(By.xpath(
				"//div[@class='right-searchbarbtm']//div[1]//b |//div[@class='right-searchbarbtm']/div[1]//b/following-sibling::span"));
		System.out.println("Total flights available for today is " + list.size());
		System.out.println("Name and Number of this flights are");
		for (WebElement webElement : list) {
			System.out.println(webElement.getText());

		}

		// Capturing screenshot
		TakesScreenshot ts = ((TakesScreenshot) driver);
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(
				"C:\\Users\\Shahnawaz Khan\\eclipse-workspace\\IrctcFlight\\Screenshot\\test.png");
		FileHandler.copy(sourceFile, destinationFile);

		// Close the driver
		driver.close();
	}

}
