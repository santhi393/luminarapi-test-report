package com.sam;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.sun.xml.xsom.impl.Ref.ContentType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.response.Response.*;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;

public class TestScripts {
	static int statusCode;
	static ExtentTest logger;
	static ExtentReports extent = new ExtentReports();
	
	JSONObject requestParams;
	
	Response response;
	
	@BeforeClass
	public void reportIntialization() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("Reports//my-extent-report.html");
		
		extent.setSystemInfo("OS Name", "Windows");
		extent.setSystemInfo("Environment", "QA");
		reporter.config().setDocumentTitle("Automation Test Report for QA environment");
		extent.attachReporter(reporter);
		logger = extent.createTest("API Test", "All API Test Case Results");
		
		requestParams = new JSONObject();
		RestAssured.baseURI = "http://localhost:8888";

	}
	
	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			logger.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
			MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		} else {
			logger.log(Status.SKIP,
			MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			logger.skip(result.getThrowable());
		}
	}
	
	@AfterTest
	void tearDown() {
		extent.flush();
	}
	
	@Test(description = "Creates a new blog category", priority=0)
	public void testScript1() {
		
		RestAssured.basePath = "/api/blog/categories/";
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", 25);
		requestParams.put("name", "aweewll");
		
		response = given().contentType("application/json").body(requestParams).when().post();
		
		statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 201);

	}
	
	@Test(description = "Returns list of blog categories", priority=1)
	public void testScript2() {
		
		response = RestAssured.get("http://localhost:8888/api/blog/categories/");

		statusCode = response.statusCode();
		if(statusCode == 200) {
			logger.pass("Response verified successfully");
		}
		else {
			logger.fail("Response not verified successfully");
		}
		Assert.assertEquals(statusCode, 200);
		response.then().body("name[0]", equalTo("aweewll"));
		logger.pass("Name validated successfully");			
	}
	
	
	@Test(description = "Blog posts GET method test script for verifying categories", priority=2)
	public void testScript3() {
		
		response = RestAssured.get("http://localhost:8888/api/blog/posts/?page=2&per_page=10");
		statusCode = response.statusCode();
		if(statusCode == 200) {
			logger.pass("Response verified successfully");
		}
		else {
			logger.fail("Response not verified successfully");
		}
		Assert.assertEquals(statusCode, 200);
		
		response.then().body("items.title[0]",equalTo(PropertyLoader.getTitleOne()), "items.title[1]", equalTo(PropertyLoader.getTitleTwo()),
				"items.title[2]",equalTo(PropertyLoader.getTitleThree()), "items.title[3]", equalTo(PropertyLoader.getTitleFour()), "items.title[4]",equalTo(PropertyLoader.getTitleFive()));
		logger.pass("Data verified");
	}
	
	
	@Test(description = "Returns list of blog posts from a specified time period", priority=3)
	public void testScript4() {
		
		response = RestAssured.get("http://localhost:8888/api/blog/posts/archive/2020/?page=4&bool=true&per_page=10");
		
		statusCode = response.statusCode();
		if(statusCode == 200) {
			logger.pass("Response verified successfully");
		}
		else {
			logger.fail("Response not verified successfully");
		}
		Assert.assertEquals(statusCode, 200);
		response.then().body("page",equalTo(4));	
	}
	
	
	@Test(description = "Creates a new blog post", priority=4)
	public void testScript5() {
		
		RestAssured.basePath = "/api/blog/posts/";
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("body", "This is post method testing");
		requestParams.put("category", "Technology");
		requestParams.put("category_id", 9);
		requestParams.put("id", 16);
		requestParams.put("pub_date", "2021-08-12");
		requestParams.put("title", "my title");
		
		response = given().contentType("application/json").body(requestParams).when().post();
		statusCode = response.statusCode();
		if(statusCode == 200) {
			logger.pass("Response verified successfully");
		}
		else {
			logger.fail("Response not verified successfully");
		}
		Assert.assertEquals(statusCode, 200);		
	}

}
