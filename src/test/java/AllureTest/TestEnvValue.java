package AllureTest;

public class TestEnvValue {

    public static void main(String[] args) {
        String envValue = System.getenv("OPENAI_API_KEY");
        System.out.println("The value of the environment variable ENV_VALUE is: " + envValue);
    }
}
