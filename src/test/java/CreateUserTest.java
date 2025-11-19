import model.UserModel;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.*;

public class CreateUserTest extends BaseApiTest {
    private UserModel user;

    @Before
    public void setUser(){
        user = new UserModel(EMAIL, PASSWORD, NAME);
    }
//    создать уникального пользователя;
    @Test
    public void testCreateUserSuccess() {
        createUser(user)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(EMAIL))
                .and()
                .body("user.name", equalTo(NAME));
    }
//    создать пользователя, который уже зарегистрирован;
//    создать пользователя и не заполнить одно из обязательных полей.
}
