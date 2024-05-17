package langchain4j;

import Utils.RestAssuredUtils;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import io.restassured.response.Response;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_VISION_PREVIEW;

public class OpenAIChatDemo {

    public static void main(String[] args) {
        //String openAiKey = System.getenv("OPENAI_API_KEY");
        String openAiKey = System.getenv("OPENAI_API_KEY");
        String ALPHA_VANTAGE_API_KEY = System.getenv("ALPHA_VANTAGE_API_KEY");
        Response response;
        String answer;
        ChatLanguageModel model = OpenAiChatModel.withApiKey(openAiKey);
//        response = RestAssuredUtils.sendGetRequest("https://www.alphavantage.co/query?function=TOP_GAINERS_LOSERS&apikey="+ALPHA_VANTAGE_API_KEY);
//        answer = model.generate("what is the value in the metadata field? if it contains the  \n"+response.getBody().asString());
//        System.out.println(answer);
//        response = RestAssuredUtils.sendGetRequest("https://www.alphavantage.co/query?function=COPPER&interval=monthly&apikey="+ALPHA_VANTAGE_API_KEY);
//        answer = model.generate("analyze this response and validate that the interval of the data is in 'Daily' interval \n"+response.getBody().asString());
//        System.out.println(answer);
        response = RestAssuredUtils.sendGetRequest("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=NVDA&apikey="+ALPHA_VANTAGE_API_KEY);
        answer = model.generate("verify that the cusip value is 897342V3 in the response, if it is not present return false "+response.getBody().asString());
        System.out.println(answer);
    }
}
