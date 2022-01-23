package pk.address;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Update_an_Address_Using_JsonFile {
	String outh_token;
	String address_id;

	@BeforeTest
	public void create_Refresh_Token() {
		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("grant_type", "password");
		requestParams.put("username", "vishal@spree.org");
		requestParams.put("password", "qwerty1234");
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		Response response = request.post("/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();

		// String responseBody = response.getBody().toString();
		// System.out.println("Response Body is => " + responseBody);
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		outh_token = jsonPathEvaluator.get("access_token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
		// First get the JsonPath object instance from the Response interface
		// Assert.assertEquals(responseBody.contains("Product was created with UI.")
		// /*Expected value*/, true /*Actual Value*/, "Response body contains ");
	}

	@Test(priority = 1)
	public void CreateAddress() throws IOException, ParseException {
		// Create json object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\JSON_File\\CreateAddress.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		// String bearerToken = "pbHRSz1GjfrGc3t2A--qiGd57EyJYnw74nlr8JK9R3o";
		Response response = RestAssured.given().auth().oauth2(outh_token).contentType(ContentType.JSON)
				.body(prodjsonobj).post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses").then()
				.extract().response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		address_id = jsonPathEvaluator.get("data.id").toString();
		JsonPath js = new JsonPath(response.asString());
		String firstName = js.get("data.attributes.firstname");
		System.out.println(firstName);
		Assert.assertEquals(firstName, "Aarav");
		String lastName = js.get("data.attributes.firstname");
		System.out.println(lastName);
		Assert.assertEquals(lastName, "Raj");
	}

	@Test(priority = 2)
	public void updateAddress() throws IOException, ParseException {
		// Create json object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\JSON_File\\UpdateAddress.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		// String bearerToken = "pbHRSz1GjfrGc3t2A--qiGd57EyJYnw74nlr8JK9R3o";
		Response response = RestAssured.given().auth().oauth2(outh_token).contentType(ContentType.JSON)
				.body(prodjsonobj)
				.patch("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + address_id).then()
				.extract().response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		JsonPath js = new JsonPath(response.asString());
		String firstName = js.get("data.attributes.firstname");
		System.out.println(firstName);
		Assert.assertEquals(firstName, "Raju");
		String lastName = js.get("data.attributes.firstname");
		System.out.println(lastName);
		Assert.assertEquals(lastName, "Raj");
	}
}
