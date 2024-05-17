package AllureTest;

import Utils.RestAssuredUtils;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StockGlobalQuotesTest {

    @Test
    @Epic("EPIC-34234: Global Quotes API feature (add Cusip to response)")
    @Issue("JIRA-34534")
    @Feature("Verify Cusip is present in the response")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Global Quotes API must return the Cusip value in the response")
    public void globalCusipResponseValidation() {

        //String openAiKey = System.getenv("OPENAI_API_KEY");
        String openAiKey = System.getenv("OPENAI_API_KEY");
        String ALPHA_VANTAGE_API_KEY = System.getenv("ALPHA_VANTAGE_API_KEY");
        ChatLanguageModel model = OpenAiChatModel.withApiKey(openAiKey);
        // Send a GET request to the specified URL
        Response response = RestAssuredUtils.sendGetRequest("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=NVDA&apikey="+ALPHA_VANTAGE_API_KEY);
        Allure.addAttachment("Response Body", response.asString());
        String answer = model.generate("verify that the cusip value is 897342V3 in the response, if it is not present return false "+response.getBody().asString());
        Assert.assertEquals(answer, "true", "Cusip value is not present in the response");
    }
}
