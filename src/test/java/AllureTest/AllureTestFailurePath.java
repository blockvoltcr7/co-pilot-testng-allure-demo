package AllureTest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AllureTestFailurePath {


    @Test
    public void testMethod() {
        System.out.println("Hello, world! im going to fail on purpose");
        Assert.assertEquals(1, 2, "This test is expected to fail");
    }
}
