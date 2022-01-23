package pk.countries;

import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Get_defaultCountry_ByHashMap {

		@Test
		public void RestAPI_Test() throws ParseException {
			RestAssured.baseURI = "https://demo.spreecommerce.org/api/v2/storefront";
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get("/countries/gb");

			// Now let us print the body of the message to see what response
			// we have recieved from the server
			String responseBody = response.getBody().asString();
			System.out.println("Response Body is =>  " + responseBody);
			// Status Code Validation
			int statusCode = response.getStatusCode();
			System.out.println("Status code is =>  " + statusCode);
			Assert.assertEquals(200, statusCode);

			//Using a HashMap
			Map<String, String> data = response.jsonPath().getJsonObject("data");
			System.out.println("Total JSON data"+data);
			System.out.println(data.get("id"));
			System.out.println(data.get("type"));
			String type_act=data.get("type");
			Assert.assertEquals(type_act, "country");
			
			//Using a HashMap
			Map<String, String> attribute = response.jsonPath().getJsonObject("data.attributes");
			System.out.println(attribute.get("iso"));
			String iso_name_act=attribute.get("iso_name");
			Assert.assertEquals(iso_name_act, "UNITED KINGDOM");
			System.out.println(attribute.get("iso_name"));
					
		}

}
