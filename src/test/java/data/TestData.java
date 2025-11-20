package data;

import com.github.javafaker.Faker;

public class TestData {
    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    static Faker userModel = new Faker();
    public static final String EMAIL = userModel.name().lastName() + userModel.regexify("[0-9]{4}") + "@yandex.ru";
    public static final String PASSWORD = userModel.regexify("[0-9]{4}");
    public static final String NAME = userModel.name().firstName();
    public static final String WRONG_EMAIL = "Wrong" + EMAIL;
    public static final String WRONG_PASSWORD = "Wrong" + PASSWORD;
}
