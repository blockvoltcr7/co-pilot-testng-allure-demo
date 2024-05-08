package AllureTest;

import Utils.RestAssuredUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;

public class TestGETRestAssured {

    public static void main(String[] args) {
        // Send a GET request to the specified URL
        Response response = RestAssuredUtils.sendGetRequest("https://api.restful-api.dev/objects?id=3");

        // Parse the response
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(response.asString());

            JsonNode firstNode = rootNode.get(0); // Get the first object in the array
            System.out.println(firstNode);

            String id = firstNode.get("id").asText(); // Get the "id" property of the object

            // Print the ID
            System.out.println("ID: " + id);
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }

    }
}
