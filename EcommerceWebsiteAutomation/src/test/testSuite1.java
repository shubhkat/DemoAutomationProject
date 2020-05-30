package test;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.poifs.crypt.dsig.KeyInfoKeySelector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import utility.excel;

public class testSuite1 {
	
	//Declaration of webdriver
	WebDriver driver;
	int intPrice1;	// Declaration of price displayed in search results page
	int intPrice2;	// Declaration of price after clicking the product link
	int intPrice3;	// Declaration of price after add to basket page
	int intPrice4;	// Declaration of price after add to basket page
	int totalPrice;	// Declaration of  total price after applying delivery charges
	
	//excel Path
	String filePath = "../EcommerceWebsiteAutomation/test_data/testData.xlsx";
	//URL
	String url = "https://www.flipkart.com/";
	
	@BeforeTest	//TestNG Annotation used for running before all tests
	public void openBrowser() {
		//setup of firefox webdriver
		WebDriverManager.firefoxdriver().setup();
		//Creating new instance of web driver
		driver =new FirefoxDriver();
		//WebDriverManager.chromedriver().setup();
		//driver =new ChromeDriver();
		//webdriver waits for 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//maximize the window
		driver.manage().window().maximize();
		//open the url
		driver.get(url);
	}
	
	
	
	
	  @AfterTest //TestNG Annotation used for running after all tests
	  public void closeBrowser() throws InterruptedException {
	  
		  //sleep java thread for 5 seconds
		  Thread.sleep(5000); 
		  //close all the driver instances
		  driver.quit();
	  
	  }
	 
	 
	 
	
	@Test(priority = 1) // First test execution
	public void loginPage() {
		
		//sheet Name
		String sheetName = "Login Details";
		
		//identify cross button element
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		//Identify login button element
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[3]/div/div/div/a")).click();
		//Identify username element
		WebElement username = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[1]/input"));
		//Read cell data for username
		String user_N = excel.ReadExcel(filePath, sheetName, 1, 0);
		//send cell data for username
		username.sendKeys(user_N);
		
		//finding passwaord element
		WebElement password = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[2]/input"));
		//Read cell data for password
		String pass_word = excel.ReadExcel(filePath, sheetName, 1, 1);
		//send cell data for password
		password.sendKeys(pass_word);
		
		//WebElement loginbtn = driver.findElement(By.xpath("(//span[text()='Login'])[2]"));
		WebElement loginbtn = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[3]/button/span"));
		loginbtn.click();
	}
	
	@Test(priority = 2) // Second test execution
	public void productSelection() throws InterruptedException {
		
		//sheet Name
		String sheetName = "Product Selection";
		Thread.sleep(2000);
		WebElement searchButton = driver.findElement(By.xpath("//input[@placeholder=\"Search for products, brands and more\"]"));
		String searchText = excel.ReadExcel(filePath, sheetName, 1, 0);
		searchButton.sendKeys(searchText, Keys.ENTER);
		
		String productTitle = driver.findElement(By.xpath("(//div/div/div[3]/div[2]/div/div[2]/div/div/div[1]/div/a[2])[1]")).getText();
		Reporter.log("Product Title: "+productTitle, true);
		
		WebElement product = driver.findElement(By.xpath("(//div/div/div[3]/div[2]/div/div[2]/div/div/div[1])[2]"));
		String price1 = driver.findElement(By.xpath("(//div/div[1]/a/div/div[1])[5]")).getText();
		String realPrice1 = price1.substring(1, price1.length());
		Reporter.log("Product price: "+realPrice1, true);
		intPrice1 = Integer.parseInt(realPrice1);
		//System.out.println(intPrice1);
		product.click();
		Thread.sleep(1000);
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		while(itr.hasNext()) {
			String ref_id = itr.next();
			driver.switchTo().window(ref_id);
		}
		
		String price2 = driver.findElement(By.xpath("(//div[2]/div[1]/div[2]/div[1]/div[1]/div[1])[1]")).getText();
		
		//String price = product_price.getText();
		String realPrice2 = price2.substring(1, price2.length());
		//Reporter.log(price2, true);
		//Reporter.log(realPrice2, true);
		
		intPrice2 = Integer.parseInt(realPrice2);
		//System.out.println(intPrice2);
	}
	
	@Test(priority = 3) // Third test execution
	public void addToBasket() throws InterruptedException {
		Thread.sleep(1000);
		//scroll down
		JavascriptExecutor js = (JavascriptExecutor)driver;
		for(int i=0; i<3; i++) {
			js.executeScript("window.scrollBy(0,1000)");
			Thread.sleep(2000);
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'ADD TO BASKET')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'GO TO BASKET')]")).click();
	}
	
	@Test(priority = 4) // Fourth test execution
	public void comparePrice() throws InterruptedException {
		Thread.sleep(2000);
		String price3 = driver.findElement(By.xpath("(//div[1]/div[2]/div[1]/div/div/span[1])[1]")).getText();
		String realPrice3 = price3.substring(1, price3.length());
		
		intPrice3 = Integer.parseInt(realPrice3);
		//System.out.println(intPrice3);
		
		String price4 = driver.findElement(By.xpath("//div[1]/div[2]/div[1]/div/div/div/div/div[1]/span")).getText();
		String realPrice4 = price4.substring(1, price4.length());
		
		intPrice4 = Integer.parseInt(realPrice4);
		//System.out.println(intPrice4);
		
		String deliveryFee = driver.findElement(By.xpath("(//div[1]/div[2]/div[1]/div/div/div/div/div[2]/span)[1]")).getText();
		String realDeliveryFee = deliveryFee.substring(1, deliveryFee.length());
		Reporter.log("Delivery Charges: "+realDeliveryFee, true);
				
		int intDeliveryFee = Integer.parseInt(realDeliveryFee);
		//System.out.println(intDeliveryFee);
		
		String totalAmt = driver.findElement(By.xpath("//div[1]/div[2]/div[1]/div/div/div/div/div[3]/div/span")).getText();
		String realTotalAmt = totalAmt.substring(1, totalAmt.length());
		Reporter.log("Delivery Charges + Product price = "+realTotalAmt, true);
				
		int intTotalAmt = Integer.parseInt(realTotalAmt);
		//System.out.println(intTotalAmt);
		
		if(intPrice1 == intPrice2 && intPrice1 ==intPrice3 && intPrice1 == intPrice4) {
			Reporter.log("All price are same", true);
			totalPrice = intPrice4 + intDeliveryFee;
		}
		else {
			Reporter.log("All price are different", true);
		}
		
		if(totalPrice == intTotalAmt) {
			Reporter.log("Total price for the product is equal to total amount to be paid", true);
		}
		else {
			Reporter.log("Total price for the product is not equal to total amount to be paid", true);
		}
		
	}
	
	/*
	 * @Test (priority = 5) // Fifth test execution 
	 * public void placeOrder() {
	 * 
	 * //Place order button
	 * //driver.findElement(By.xpath("//span[contains(text(),'Place Order')]")).
	 * click();
	 * 
	 * }
	 */
	
}
