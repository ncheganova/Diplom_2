package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserModel;

import static io.restassured.RestAssured.given;

public class UserSteps {
    //создать пользователя
    public static Response createUser(UserModel userModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post("/api/auth/register")
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
                .delete("/api/auth/user")
                .then()
                .log().all();
    }
    //логин пользователя успешный
    public static Response loginUser(UserModel userModel, String userAccessToken) {
        return given()
                .log().all()
                .header("Authorization", userAccessToken)
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post("/api/auth/login")
                .then()
                .log().all()
                .extract().response();
    }
    //логин пользователя неуспешный
    public static Response loginUser(UserModel userModel, String userAccessToken, UserModel modifiedUserModel) {
        return given()
                .log().all()
                .header("Authorization", userAccessToken)
                .contentType(ContentType.JSON)
                .body(modifiedUserModel)
                .when()
                .post("/api/auth/login")
                .then()
                .log().all()
                .extract().response();
    }
}
