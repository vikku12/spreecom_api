package pk.countries;


import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Get_defaultCountry_using_ExtentReport {

	// builds a new report using the HTML template
		ExtentSparkReporter htmlReporter;
		ExtentReports extent;
		// helps to generate the logs in test report.
		ExtentTest test;

		@BeforeTest()
		public void startReport() {
			// initialize the HtmlReporter
			htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/ExtentReport/ExtentReportResult.html");
			// initialize ExtentReports and attach the HtmlReporter
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			// To add system or environment info by using the setSystemInfo method.
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("QA Name", "Abhi");

			// configuration items to change the look and feel
			// add content, manage tests etc
			// htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setDocumentTitle("Spreecom - Extent Report");
			htmlReporter.config().setReportName("Batch for Default Country Test Report");
			// htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.DARK);
			htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

		}

		@AfterTest
		public void CloseExtentReport()

		{
			// to write or update test information to reporter
			extent.flush();
		}

		@Test
		public void getDefaultCountry() {
			test = extent.createTest("Test Case 1", "Default Country");
			RestAssured.baseURI = "https://demo.spreecommerce.org/api/v2/storefront";
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get("/countries/default");

			// Now let us print the body of the message to see what response
			// we have received from the server
			String responseBody = response.getBody().asString();
			System.out.println("Response Body is =>  " + responseBody);
			// Status Code Validation
			int statusCode = response.getStatusCode();
			System.out.println("Status code is =>  " + statusCode);
			Assert.assertEquals(200, statusCode);

			// First get the JsonPath object instance from the Response interface
			Assert.assertEquals(
					responseBody.contains("UNITED STATES") /* Expected value */,
					true /* Actual Value */, "Response body contains UNITED STATES");

		}

}
