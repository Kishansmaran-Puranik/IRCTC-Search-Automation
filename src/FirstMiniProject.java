import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;




public class FirstMiniProject {
    public static WebDriver driver;

    public void verify() {
        String expResult = "IRCTC Next Generation eTicketing System";
        String actResult = driver.getTitle();
        if (actResult.contains(expResult))
            System.out.println("Website launch is IRCTC Train");
        else
            System.out.println("Website launch is incorrect");
    }

    public void selectFromAddress() throws InterruptedException {
        WebElement from = driver.findElement(By.xpath("//*[@id=\"origin\"]/span/input"));
        from.sendKeys("HYDERABAD DECAN - HYB");
        from.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        from.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

    }

    public void selectDestinationAddress() throws InterruptedException {
        WebElement to = driver.findElement(By.xpath("//*[@id=\"destination\"]/span/input"));
        to.sendKeys("pune");
        Thread.sleep(1000);
        to.sendKeys(Keys.ARROW_DOWN);
        to.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
    }

    public void chooseDate() throws InterruptedException {
        WebElement date = driver.findElement(By.xpath("//*[@id=\"jDate\"]/span/input"));
        date.click();
        Thread.sleep(1000);
        date.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        date.sendKeys(Keys.BACK_SPACE);
        date.sendKeys("09/07/2023");

    }

    public void DisableCheckbox() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/div/form/div[4]/div/span[1]/label")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='ui-button-text ui-clickable']")).click();
        Thread.sleep(1000);
    }

    public void selectClass() throws InterruptedException
    {
        WebElement sleeper = driver.findElement(By.xpath("//*[@id=\"journeyClass\"]"));
        sleeper.click();
        driver.findElement(By.xpath("//*[@id=\"journeyClass\"]/div/div[4]/div/ul/p-dropdownitem[12]")).click();

    }

    public void search() throws InterruptedException
    {
        int count=0;
        driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/div/form/div[5]/div/button")).click();
        String beforeXpath="/html/body/app-root/app-home/div[3]/div/app-train-list/div[4]/div/div[5]/div[";
        String afterXpath="]/div[1]/app-train-avl-enq/div[1]/div[1]/div[1]";
        for(int i=1;i<=5;i++) {
            String actualXpath=beforeXpath+i+afterXpath;
            List<WebElement> results =driver.findElements(By.xpath(actualXpath));
            count+=results.size();

         //Display the number of trains available and names on console
            for (WebElement webElement : results) {
                System.out.println("Name and Number of the available train is:-"+webElement.getText());
            }
        }
        System.out.println("Total number of Results-"+count);

    }

    public void screenshot()
    {
        TakesScreenshot srcShot = ((TakesScreenshot)driver);
        File Src = srcShot.getScreenshotAs(OutputType.FILE);
        String filePath="./Screenshots/screenshot.png";
        File Dest = new File(filePath);
        try {
            FileHandler.copy(Src, Dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.close();


        System.out.println("Screenshot successfully");

    }







    public static void main(String[] args) throws Exception {
        driver = null;
        while (true) {
            System.out.println("Enter Browser Name or 0 to Exit");
            @SuppressWarnings("resource")
            Scanner sc = new Scanner(System.in);
            String browser = sc.nextLine();
            if (browser.equalsIgnoreCase("0")) {
                System.out.println("Program terminatted");
                System.exit(0);
            } else if (!(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("firefox"))) {
                System.out.println("Invalid browser name, ");
                continue;

            } else if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "./Resources/chromedriver.exe");
                driver = new ChromeDriver();
                System.out.println("Starting chrome browser..");


            }


            driver.get("https://www.irctc.co.in/");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            FirstMiniProject tr = new FirstMiniProject();
            tr.verify();
            tr.selectFromAddress();
            tr.selectDestinationAddress();
            tr.chooseDate();
            tr.DisableCheckbox();
            tr.selectClass();
            tr.search();
            tr.screenshot();

        }
    }
}