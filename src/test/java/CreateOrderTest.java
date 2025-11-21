import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.*;
import static steps.OrderSteps.createOrder;
import static steps.UserSteps.*;

public class CreateOrderTest extends BaseApiTest{
    private UserModel user;
    private String userAccessToken;
    private OrderModel order;

    @Before
    public void setUser(){
        user = new UserModel(EMAIL, PASSWORD, NAME);
        Response resCreateUser = createUser(user);
        userAccessToken = getUserAccessToken(resCreateUser);
    }

    @DisplayName("Проверка успешного создания заказа с атворизацией и ингредиентами")
    @Test
    public void testCreateOrderWithTokenSuccess() {
        ArrayList<String> ingredients = getOrdersIngredients(INGREDIENT_BUN, INGREDIENT_MEAT);
        order = new OrderModel(ingredients);

        createOrder(order, userAccessToken).then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.owner.name", equalTo(NAME))
                .body("order.owner.email", equalTo(EMAIL))
                .body("order.status", equalTo("done"))
                .body("order.price", equalTo(2325));
    }


    @DisplayName("Проверка успешного создания заказа без атворизацией с ингредиентами")
    @Test
    public void testCreateOrderWithoutTokenSuccess() {
        ArrayList<String> ingredients = getOrdersIngredients(INGREDIENT_BUN, INGREDIENT_MEAT);
        order = new OrderModel(ingredients);

        createOrder(order, "").then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @DisplayName("Проверка неуспешного создания заказа с атворизацией без ингредиентов")
    @Test
    public void testCreateOrderWithoutIngredientsFailure() {
        ArrayList<String> ingredients = getOrdersIngredients();
        order = new OrderModel(ingredients);

        createOrder(order, userAccessToken).then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }


    @DisplayName("Проверка неуспешного создания заказа с атворизацией и неверным хэшем ингредиентов")
    @Test
    public void testCreateOrderWithWrongIngredientsFailure() {
        ArrayList<String> ingredients = getOrdersIngredients(FIRST_WRONG_INGR, SECOND_WRONG_INGR);
        order = new OrderModel(ingredients);

        createOrder(order, userAccessToken).then()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @After
    public void cleanUp() {
        //код для удаления созданного пользователя
        deleteUser(userAccessToken);
    }
}