package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReport implements ITestListener {

    ExtentSparkReporter sparkReporter;
    ExtentReports extentReports;
    ExtentTest test;

    String repName;

    public void onStart(ITestContext context) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt = new Date();
        String currentdatetimestamp = df.format(dt);

        repName = "Test-Report-" + currentdatetimestamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("Automation Report"); // Title of the report
        sparkReporter.config().setReportName("Sanity Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "StoreUser");
        extentReports.setSystemInfo("Module", "Admin");
        extentReports.setSystemInfo("SubModule", "Customers");
        extentReports.setSystemInfo("UserName", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", "Production");

        String os = context.getCurrentXmlTest().getParameter("os");
        extentReports.setSystemInfo("Operating System", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        extentReports.setSystemInfo("Browser", browser);

        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extentReports.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {
        test = extentReports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // To display groups in report
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    public void onTestFailure(ITestResult result){
        test = extentReports.createTest(result.getName());
        test.log(Status.FAIL,"Testcase failed is : " +result.getName());
        test.log(Status.FAIL,"Testcase failed due to : "+result.getThrowable());
    }

    public void onTestSkipped(ITestResult result){
        extentReports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP,result.getName()+ "got skipped");
        test.log(Status.INFO,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context){
        extentReports.flush();
        String pathofExtentReport= System.getProperty("user.dir")+"\\reports\\" + repName;
        File extentreport = new File(pathofExtentReport);
        try{
            Desktop.getDesktop().browse(extentreport.toURI());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
