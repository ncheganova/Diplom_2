package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    //создать заказ
    public static Response createOrder(OrderModel orderModel, String userAccessToken) {
        return given()
                .log().all()
                .header("Authorization", userAccessToken)
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post("/api/orders")
                .then()
                .log().all()
                .extract().response();
    }
}
