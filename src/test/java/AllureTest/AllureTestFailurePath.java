package AllureTest;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AllureTestFailurePath {


    @Test
    @Description("Lambda step test1")
    public void testMethod() {
        System.out.println("Hello, world! im going to fail on purpose");
        Assert.assertEquals(34234, 423423, "Passport ID does not match the database entry for passport ID");
    }


}
