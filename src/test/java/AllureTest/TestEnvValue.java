package AllureTest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestEnvValue {

    @Test
    public void testEnvValue() {
        String envValue = System.getenv("OPENAI_API_KEY");
        System.out.println("The value of the environment variable OPENAI_API_KEY is: " + envValue);
        Assert.assertNotNull(envValue, "The environment variable OPENAI_API_KEY is not set.");
    }
}