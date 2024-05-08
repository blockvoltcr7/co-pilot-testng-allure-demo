package AllureTest;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AllureTestHappyPath {


    @Test
    @Description("Lambda step test2")
    public void testMethodHappyPath() {
        System.out.println("Hello, world! im going to Pass");
        Assert.assertEquals(1, 1, "This test is expected to Pass");
    }
}
