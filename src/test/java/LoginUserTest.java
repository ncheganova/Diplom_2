import io.restassured.response.Response;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static steps.UserSteps.*;

public class LoginUserTest extends BaseApiTest{
    private UserModel user;
    private String userAccessToken;

    @Before
    public void setUser(){
        user = new UserModel(EMAIL, PASSWORD, NAME);
        Response resCreateUser = createUser(user);
        userAccessToken = getUserAccessToken(resCreateUser);
    }

    //    вход под существующим пользователем
    @Test
    public void testLoginUserSuccess() {
        loginUser(user, userAccessToken).then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("accessToken", startsWith("Bearer "))
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(EMAIL))
                .body("user.name", equalTo(NAME));
    }
    //    вход с неверным логином
    @Test
    public void testLoginUserWithWrongEmailFailure() {
        UserModel userModified = new UserModel(WRONG_EMAIL, PASSWORD, NAME);
        loginUser(user, userAccessToken, userModified).then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", startsWith("email or password are incorrect"));
    }

    //    вход с неверным паролем
    @Test
    public void testLoginUserWithWrongPasswordFailure() {
        UserModel userModified = new UserModel(EMAIL, WRONG_PASSWORD, NAME);
        loginUser(user, userAccessToken, userModified).then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", startsWith("email or password are incorrect"));
    }

    @After
    public void cleanUp() {
        //код для удаления созданного пользователя
            deleteUser(userAccessToken);
    }
}