package pk.countries;

import org.testng.annotations.DataProvider;

public class Spreecom_TestData {

	@DataProvider(name="country_iso")
	public Object[][] iso_name(){

		return new Object[][] {

			/* {"usa","UNITED STATES"},
			{"ind","INDIA"},
			{"pak","PAKISTAN"}*/
			{"usa","UNITED STATES", "USA"},
			{"ind","INDIA", "IND"},
			{"pak","PAKISTAN", "PAK"},
		};
	}

}
