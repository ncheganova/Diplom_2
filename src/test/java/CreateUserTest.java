import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.*;
import static steps.UserSteps.*;

public class CreateUserTest extends BaseApiTest {
    private UserModel user;
    private String userAccessToken;

    @Before
    public void setUser(){
        user = new UserModel(EMAIL, PASSWORD, NAME);
    }

    @DisplayName("Успешное создание уникального пользователя")
    @Description("Тест проверяет код и тело ответа для создания уникального пользователя")
    @Test
    public void testCreateUserSuccess() {
        Response resCreateUser = createUser(user);
        userAccessToken = getUserAccessToken(resCreateUser);
        resCreateUser.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(EMAIL))
                .body("user.name", equalTo(NAME))
                .body("accessToken", startsWith("Bearer "))
                .body("refreshToken", notNullValue());
    }

    @DisplayName("Неуспешное создание пользователя, который уже зарегистрирован")
    @Description("Тест проверяет код и тело ответа для создания неуникального пользователя")
    @Test
    public void testCreateDoubleUserFailure() {
        Response resCreateOneUser = createUser(user);
        userAccessToken = getUserAccessToken(resCreateOneUser);
        createUser(user).then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @DisplayName("Неуспешное создание пользователя, если не заполнить одно из обязательных полей")
    @Description("Тест проверяет код и тело ответа при создании пользователя, если не заполнить email")
    @Test
    public void testCreateUserWithoutEmailFailure() {
        user.setEmail(null);
        createUser(user).then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @After
    public void cleanUp() {
        //код для удаления созданного пользователя
        if (userAccessToken != null) {
            deleteUser(userAccessToken);}
    }
}