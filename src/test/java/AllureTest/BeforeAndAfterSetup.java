package AllureTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Setup class for before and after suite actions in Allure TestNG tests.
 * This class is responsible for initializing and tearing down resources before and after all tests are run.
 */
public class BeforeAndAfterSetup {



    /**
     * Method executed before the test suite starts.
     * It can be used for setup actions that are necessary before any test is run.
     */
    @BeforeSuite
    public void beforeSuite() {
        // Your cleanup code here
        System.out.println("starting test before suite...");
    }

    /**
     * Method executed after the test suite has finished.
     * It performs cleanup actions, generates bug reports based on test results, and sends them via email.
     * Utilizes the OpenAI and LangChain4j libraries to analyze test results and generate reports.
     *
     * @throws IOException if there is an issue with file operations
     */
    @AfterSuite
    public void tearDown() throws IOException {
        // Initialize OpenAI Client
        String apiKey = System.getenv("OPENAI_API_KEY"); // Replace with your OpenAI API key
        ChatLanguageModel model = OpenAiChatModel.withApiKey(apiKey);
        // Load and prepare JSON files to analyze
        List<String> testResultsArray = loadJsonFilesFromDirectory("allure-results");
        // Convert JSON files to a single prompt
        String testResults = buildJsonFileContentPrompt(testResultsArray);
        String bugReportFormat = readFileContent("src/test/resources/file-formats/bug-report-format.md");
        String instructions = readFileContent("src/test/resources/Prompts/BugReportInstructions.txt");

        String bug_report = model.generate(instructions + " " + testResults + " " + bugReportFormat);

        String emailInstructions = readFileContent("src/test/resources/Prompts/emailPrompt.txt");

        String email = model.generate(emailInstructions+"\n" +
                bug_report);
        try {
            writeToFile("src/test/resources/bug-reports", "bug-report", bug_report);
            writeToFile("src/test/resources/emails", "email-bug-report", email);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Writes content to a file within a specified directory, appending a timestamp to the filename.
     *
     * @param directoryPath The directory path where the file will be created.
     * @param fileName      The base name of the file (timestamp will be appended).
     * @param content       The content to write to the file.
     * @throws IOException If an I/O error occurs writing to or creating the file.
     */
    private static void writeToFile(String directoryPath, String fileName, String content) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filePath = directoryPath + "/" + fileName + "-" + timestamp + ".md";
        Files.write(Paths.get(filePath), content.getBytes());
    }

    /**
     * Reads the content of a file and returns it as a String.
     *
     * @param filePath The path of the file to read.
     * @return The content of the file as a String.
     * @throws IOException If an I/O error occurs reading from the file.
     */
    private static String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Loads JSON files from a specified directory and returns their content as a list of strings.
     *
     * @param directoryPath The directory containing JSON files to be analyzed.
     * @return A list of strings, each representing the content of a JSON file.
     */
    private static List<String> loadJsonFilesFromDirectory(String directoryPath) {
        List<String> jsonFilesContent = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith("result.json"));

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
     * Validates if a given string is a valid JSON format.
     *
     * @param jsonString The JSON string to validate.
     * @return true if the string is a valid JSON, false otherwise.
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
     * Builds a single string prompt containing the contents of multiple JSON files.
     *
     * @param jsonFilesContent A list of JSON strings.
     * @return A concatenated string prompt.
     */
    private static String buildJsonFileContentPrompt(List<String> jsonFilesContent) {
        StringBuilder promptBuilder = new StringBuilder();

        for (String content : jsonFilesContent) {
            promptBuilder.append(content).append("\n\n");
        }

        return promptBuilder.toString();
    }

}