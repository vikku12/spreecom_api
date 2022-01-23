package pk.address;

import java.io.FileReader;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pk.utility.Utility_Function;

public class DeleteAddress extends Utility_Function {

	String address_id;

	@Test(priority = 1)
	public void CreateAddress() throws IOException, ParseException {
		// Create JSON object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\JSON_File\\CreateAddress.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		// String bearerToken = "pbHRSz1GjfrGc3t2A--qiGd57EyJYnw74nlr8JK9R3o";
		Response response = RestAssured.given().auth().oauth2(Utility_Function.oAuth_Token())
				.contentType(ContentType.JSON).body(prodjsonobj)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses").then().extract().response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		address_id = jsonPathEvaluator.get("data.id").toString();
		JsonPath js = new JsonPath(response.asString());
		String firstname_act = js.get("data.attributes.firstname");
		System.out.println(firstname_act);
		Assert.assertEquals(firstname_act, "Aarav");
		// JsonPath js = new JsonPath(response.asString());
		String lastname_act = js.get("data.attributes.lastname");
		System.out.println(lastname_act);
		Assert.assertEquals(lastname_act, "Raj");
	}

	@Test(priority = 2)
	public void updateAddress() throws IOException, ParseException {
		// Create JSON object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\JSON_File\\UpdateAddress.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		// String bearerToken = "pbHRSz1GjfrGc3t2A--qiGd57EyJYnw74nlr8JK9R3o";
		Response response = RestAssured.given().auth().oauth2(Utility_Function.oAuth_Token())
				.contentType(ContentType.JSON).body(prodjsonobj)
				.patch("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + address_id).then()
				.extract().response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		JsonPath js = new JsonPath(response.asString());
		String firstname_act = js.get("data.attributes.firstname");
		System.out.println(firstname_act);
		Assert.assertEquals(firstname_act, "Raju");
		// JsonPath js = new JsonPath(response.asString());
		String lastname_act = js.get("data.attributes.lastname");
		System.out.println(lastname_act);
		Assert.assertEquals(lastname_act, "Raj");
	}

	@Test(priority = 3)
	public void deleteAnAddress() throws IOException, ParseException {
		// Create JSON object of JSONParser class to parse the JSON data

		// String bearerToken = "pbHRSz1GjfrGc3t2A--qiGd57EyJYnw74nlr8JK9R3o";
		Response response = RestAssured.given().auth().oauth2(Utility_Function.oAuth_Token())
				.delete("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + address_id).then()
				.extract().response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(204, statusCode);
	}
}
