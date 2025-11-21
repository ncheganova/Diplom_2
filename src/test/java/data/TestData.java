package data;

import com.github.javafaker.Faker;

import java.util.ArrayList;

public class TestData {
    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    static Faker userModel = new Faker();
    public static final String EMAIL = userModel.name().lastName() + userModel.regexify("[0-9]{4}") + "@yandex.ru";
    public static final String PASSWORD = userModel.regexify("[0-9]{4}");
    public static final String NAME = userModel.name().firstName();
    public static final String WRONG_EMAIL = "Wrong" + EMAIL;
    public static final String WRONG_PASSWORD = "Wrong" + PASSWORD;
    public static final String INGREDIENT_BUN = "61c0c5a71d1f82001bdaaa6d";
    public static final String INGREDIENT_MEAT = "61c0c5a71d1f82001bdaaa6f";
    public static final String FIRST_WRONG_INGR = "61c0c5a71d1faaaaaaaaaaaa";
    public static final String SECOND_WRONG_INGR = "61c0c5a71d1fbbbbbbbbbbbb";

    public static ArrayList<String> getOrdersIngredients(String firstIngredient, String secondIngredient) {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add(firstIngredient);
        ingredients.add(secondIngredient);
      return ingredients;
    }

    public static ArrayList<String> getOrdersIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        return ingredients;
    }
}
