package langchain4j;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;

public class OpenAIChatsWithReport {

    public static void main(String[] args) throws IOException {
        // Initialize OpenAI Client
        String apiKey = System.getenv("OPENAI_API_KEY"); // Replace with your OpenAI API key
        ChatLanguageModel model = OpenAiChatModel.withApiKey(apiKey);


        // Load and prepare JSON files to analyze
        List<String> jsonFilesContent = loadJsonFilesFromDirectory("allure-results");

        // Convert JSON files to a single prompt
        String fileContentPrompt = buildJsonFileContentPrompt(jsonFilesContent);
        System.out.println("Prompt: " + fileContentPrompt);
//        String answer = model.generate("Analyze the json formatted test results from my test automated tests. " +
//                "The results are in the following JSON files: " + fileContentPrompt+ "What are the key insights from the test results?");

        //read yaml file to return expected format

        //read the file in this path src/test/resources/file-formats/bug-report-format.md
        //add code to read file from directory
        String bugReportFormat = readFileContent("src/test/resources/file-formats/bug-report-format.md");

        String answer = model.generate("Analyze the json formatted test results from my test automated tests. " +
                "The results are in the following JSON files: " + fileContentPrompt + ". Finally create a bug report in the following format: \n" +
                bugReportFormat);

        try {
            writeToFile("src/test/resources/bug-reports", answer);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        System.out.println(answer);

    }

    private static void writeToFile(String directoryPath, String content) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filePath = directoryPath + "/bug-report-" + timestamp + ".md";
        Files.write(Paths.get(filePath), content.getBytes());
    }

    private static String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Load JSON files from a directory.
     *
     * @param directoryPath the directory containing JSON files to be analyzed
     * @return a list of JSON strings
     */
    private static List<String> loadJsonFilesFromDirectory(String directoryPath) {
        List<String> jsonFilesContent = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith("results.json"));

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        try {
                            String content = new String(Files.readAllBytes(file.toPath()));
                            // Optional: Validate if it's a valid JSON before adding
                            if (isValidJson(content)) {
                                jsonFilesContent.add("File: " + file.getName() + "\n" + content);
                            } else {
                                System.err.println("Invalid JSON in file " + file.getName());
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
        return jsonFilesContent;
    }

    /**
     * Validate if a given string is a valid JSON.
     *
     * @param jsonString the JSON string to validate
     * @return true if valid, false otherwise
     */
    private static boolean isValidJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Build a prompt containing the contents of multiple JSON files.
     *
     * @param jsonFilesContent a list of JSON strings
     * @return a concatenated string prompt
     */
    private static String buildJsonFileContentPrompt(List<String> jsonFilesContent) {
        StringBuilder promptBuilder = new StringBuilder();

        for (String content : jsonFilesContent) {
            promptBuilder.append(content).append("\n\n");
        }

        return promptBuilder.toString();
    }
}
