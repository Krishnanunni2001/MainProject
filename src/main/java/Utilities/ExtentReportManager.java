package Utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Main_TestNG.emiCalc_TestNG;

import com.aventstack.extentreports.Status;

 
 
public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;  // UI of the report
	public ExtentReports extent;  //populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods
 
	public void onStart(ITestContext context) {
		sparkReporter=new ExtentSparkReporter("C:\\Users\\2303964\\eclipse-workspace\\MainProject\\Reports\\myreport.html");//specify location of the report
		sparkReporter.config().setDocumentTitle("Automation Report"); // TiTle of report
		sparkReporter.config().setReportName("Emi Calculator"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Name","Emi Calculator");
		extent.setSystemInfo("Computer Name","localhost");
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("Tester Name","Krishnanunni V");
		extent.setSystemInfo("os","Windows11");
		extent.setSystemInfo("Browser name","Chrome,Edge");
	}
 
 
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName()); // create a new enty in the report
		test.log(Status.PASS, "Test case PASSED is:" + result.getName()); // update status p/f/s
		try {
			String imgPath = new Screenshots().ScreenShotTaker(emiCalc_TestNG.driver,result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
 
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test case FAILED is:" + result.getName());
		test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable()); 
		
	}
 
	public void onTestSkipped(ITestResult result) {
 
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
	}
    
	public void onFinish(ITestContext context) {
		extent.flush();
		String reportFilePath = "C:\\Users\\2303964\\eclipse-workspace\\MainProject\\Reports\\myreport.html";
        File htmlFile = new File(reportFilePath);
        try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}



