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
                //.auth().oauth2(userAccessToken)
                .header("Authorization", userAccessToken)
                .delete("/api/auth/user")
                .then()
                .log().all();
    }
}
