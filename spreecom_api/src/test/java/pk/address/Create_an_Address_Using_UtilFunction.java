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
import io.restassured.response.Response;
import pk.utility.Utility_Function;

public class Create_an_Address_Using_UtilFunction extends Utility_Function {

	@Test
	public void createanaddress() throws IOException, ParseException {

		// Create json object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\JSON_File\\CreateAddress.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		// String bearerToken = "gQvs0T9IVLbnliVSoEzYzfqZWpj17ibpFCbapm35O08";
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

		// First get the JsonPath object instance from the Response interface
		Assert.assertEquals(responseBody.contains("Aarav") /* Expected value */, true /* Actual Value */,
				"Response body contains Aarav");
		// convert the body into lower case and then do a comparison to ignore casing.
		Assert.assertEquals(responseBody.contains("Raj") /* Expected value */, true /* Actual Value */,
				"Response body contains Raj");
	}
}
