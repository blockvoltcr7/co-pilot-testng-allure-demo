package langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class OpenAIChatDemo {

    public static void main(String[] args) {
        //String openAiKey = System.getenv("OPENAI_API_KEY");
        String openAiKey = System.getenv("OPENAI_API_KEY");
        ChatLanguageModel model = OpenAiChatModel.withApiKey(openAiKey);
        String answer = model.generate("What is the capital of France?");
        System.out.println(answer);
    }
}
