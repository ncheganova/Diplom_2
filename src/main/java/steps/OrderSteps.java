package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;
import static model.Endpoints.CREATE_ORDER_ENDPOINT;

public class OrderSteps {
    //создать заказ
    public static Response createOrder(OrderModel orderModel, String userAccessToken) {
        return given()
                .log().all()
                .header("Authorization", userAccessToken)
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }
}
