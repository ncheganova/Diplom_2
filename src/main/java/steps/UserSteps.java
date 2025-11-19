package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserModel;

import static io.restassured.RestAssured.given;

public class UserSteps {
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
}
