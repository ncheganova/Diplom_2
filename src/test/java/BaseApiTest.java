import io.restassured.RestAssured;
import org.junit.BeforeClass;

import static data.TestData.BASE_URL;

public class BaseApiTest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
}