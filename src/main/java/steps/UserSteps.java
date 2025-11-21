package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserModel;

import static io.restassured.RestAssured.given;
import static model.Endpoints.*;

public class UserSteps {
    //создать пользователя
    public static Response createUser(UserModel userModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(CREATE_USER_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }
    //получить токен доступа
    public static String getUserAccessToken(Response response) {
        return response.path("accessToken");
    }
    //удалить пользователя
    public static void deleteUser(String userAccessToken) {
        given()
                .log().all()
                .header("Authorization", userAccessToken)
                .delete(DELETE_USER_ENDPOINT)
                .then()
                .log().all();
    }
    //логин пользователя
    public static Response loginUser(UserModel userModel, String userAccessToken) {
        return given()
                .log().all()
                .header("Authorization", userAccessToken)
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(LOGIN_USER_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }
}
