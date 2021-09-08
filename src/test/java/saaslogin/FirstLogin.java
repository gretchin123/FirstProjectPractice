package saaslogin;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import xpaths.LoginXpaths;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FirstLogin {
    WebDriver driver;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    XSSFCell cell;
    DataFormatter dataFormatter = new DataFormatter();
    String AutomationResultPassed = "Passed";
    String AutomationResultFailed = "Failed";


    private static final LoginXpaths loginlocators = new LoginXpaths();

    @BeforeTest
    public void initialization(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Gretchin\\Desktop\\Automation Stuffs\\SAASCONSOLE\\chromedriver_win32_93\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://tapp.saasconsole.com/signin");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(9000, TimeUnit.SECONDS );
    }

    @Test(priority = 1)
    public void First_Login() throws IOException, InterruptedException {
        File src;
        src = new File("C:\\Users\\Gretchin\\Desktop\\Automation Stuffs\\SAASCONSOLE\\DataDriven\\SAASCONSOLE.xlsx");
        FileInputStream fis=new FileInputStream(src);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet("LogIn");

        for (int i=1; i<=sheet.getLastRowNum(); i++){

            //Import data for scenario
            cell = sheet.getRow(i).getCell(0);
            String ScenarioType = dataFormatter.formatCellValue(cell);

            //Import data for email.
            cell = sheet.getRow(i).getCell(1);
            String email = dataFormatter.formatCellValue(cell);

            //Import data for password.
            cell = sheet.getRow(i).getCell(2);
            String pword = dataFormatter.formatCellValue(cell);

            //Set Date
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            System.out.println(currentDate);

            //Start flow of the code.
            //Input email
            WebElement inputEmail = driver.findElement(By.xpath(loginlocators.EmailTextField()));
            inputEmail.clear();
            inputEmail.sendKeys(email);
            driver.manage().timeouts().implicitlyWait(9000,TimeUnit.SECONDS);

            //Input password
            WebElement inputPassword = driver.findElement(By.xpath(loginlocators.PasswordTextField()));
            inputPassword.clear();
            inputPassword.sendKeys(pword);
            driver.manage().timeouts().implicitlyWait(9000,TimeUnit.SECONDS);

             WebElement loginbtn = driver.findElement(By.xpath(loginlocators.LogInButton()));

            if (ScenarioType.contains("Empty")){
                //Condition if scenario is empty

                //Click LogIn button
                loginbtn.click();
                Thread.sleep(4000);

                //Saving to Excel
                FileOutputStream fos = new FileOutputStream(src);
                sheet.getRow(i).createCell(3).setCellValue(AutomationResultPassed);
                sheet.getRow(i).createCell(5).setCellValue(loginlocators.EmailRequiredField());
                sheet.getRow(i).createCell(6).setCellValue(currentDate);
                workbook.write(fos);
                fos.close();
                break;
            }

        }


    }

}
