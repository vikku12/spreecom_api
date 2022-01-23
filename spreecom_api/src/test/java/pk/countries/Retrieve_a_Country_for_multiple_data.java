package pk.countries;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Retrieve_a_Country_for_multiple_data extends Spreecom_TestData{
	@Test(dataProvider = "country_iso")
	//public void retriveacountry(String iso, String expectedValue)
	public void retriveacountry(String iso, String iso_name, String iso3)
	{
		RestAssured.baseURI = "https://demo.spreecommerce.org/api/v2/storefront/";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("countries/"+iso);

		// Now let us print the body of the message to see what response
		// we have received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		// First get the JsonPath object instance from the Response interface
		//Assert.assertEquals(responseBody.contains(expectedValue) /*Expected value*/, true /*Actual Value*/);
		//Assert.assertEquals(responseBody.contains(iso3) /*Expected value*/, true /*Actual Value*/);
		//Verify isoName
		JsonPath js = new JsonPath(response.asString());
		String iso_name_act = js.get("data.attributes.iso_name");
		System.out.println(iso_name_act);
		Assert.assertEquals(iso_name_act, iso_name);

		//Verify iso3
		String iso3_act = js.get("data.attributes.iso3");
		System.out.println(iso3_act);
		Assert.assertEquals(iso3_act, iso3);

	}
}
