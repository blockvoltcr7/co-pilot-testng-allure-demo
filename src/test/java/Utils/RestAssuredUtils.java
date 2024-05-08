package Utils;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredUtils {

    /**
     * Send a GET request to the specified URL and return the response.
     *
     * @param url the URL to send the GET request to
     * @return the response of the GET request
     */
    public static Response sendGetRequest(String url) {
        return RestAssured.get(url);
    }
}
