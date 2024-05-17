package AllureTest;

import Utils.RestAssuredUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class DeviceCapacityAPITest extends BeforeAndAfterSetup {

    @Test
    @Epic("EPIC-54534: Whole Sale Device Management for all promo devices")
    @Issue("JIRA-56353")
    @Feature("Verify the capacity of the apple promotional devices")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Promotional Apple Iphone 12 Pro Max must have a capacity of 256 GB")
    public void deviceCapacityAPITestForApple() {
        // Send a GET request to the specified URL
        Response response = RestAssuredUtils.sendGetRequest("https://api.restful-api.dev/objects?id=3");

        Allure.addAttachment("Response Body", response.asString());

        // Parse the response
        JSONArray jsonArray = new JSONArray(response.asString());
        // Get the first object in the array
        JSONObject firstObject = jsonArray.getJSONObject(0);
        // Get the "data" object
        JSONObject dataObject = firstObject.getJSONObject("data");
        // Get the "color" and "capacity GB" properties of the "data" object
        String color = dataObject.getString("color");
        int capacityGB = dataObject.getInt("capacity GB");
        // Print the color and capacity GB
        System.out.println("Color: " + color);
        System.out.println("Capacity GB: " + capacityGB);


        // Assert that the capacity GB equals 256
        Assert.assertEquals(capacityGB, 256, "Capacity GB does not match expected value");

    }
}