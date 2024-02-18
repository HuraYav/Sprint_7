package example.test.courier;

import example.api.courier.ApiCourier;
import example.data.courier.CreateCourierData;
import example.data.courier.LoginCourierData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class TestCreateCourier {
    private ApiCourier apiCourier;

    private CreateCourierData createCourierData;

    private LoginCourierData loginCourierData;

    @Before
    public void setUp() {
        apiCourier = new ApiCourier();
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void createCourier() {
        createCourierData = new CreateCourierData("HuraYavYandexSimple", "jackychan", "Jacky");
        loginCourierData = new LoginCourierData("HuraYavYandexSimple", "jackychan");
        ValidatableResponse response = apiCourier.create(createCourierData);
        assertEquals("Курьер не cоздался", response.extract().statusCode(), 201);
    }

    @Test
    @DisplayName("Успешный запрос возвращает oк")
    public void successCreateCourierReturnOk() {
        createCourierData = new CreateCourierData("HuraYavYandexSimple", "jackychan", "Jacky");
        loginCourierData = new LoginCourierData("HuraYavYandexSimple", "jackychan");
        ValidatableResponse response = apiCourier.create(createCourierData);

        assertEquals("Запрос не вернул ok", response.extract().body().jsonPath().get("ok"), true);
    }

    @Test
    @DisplayName("Регистрация без логина")
    public void createCourierWithoutLogin() {
        createCourierData = new CreateCourierData("", "jackychan", "Jacky");
        loginCourierData = new LoginCourierData("", "jackychan");
        ValidatableResponse response = apiCourier.create(createCourierData);

        response.statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Регистрация без пароля")
    public void createCourierWithoutPassword() {
        createCourierData = new CreateCourierData("HuraYavYandex", "", "Jacky");
        loginCourierData = new LoginCourierData("HuraYavYandex", "");
        ValidatableResponse response = apiCourier.create(createCourierData);

        response.statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание дублирующегося пользователя")
    public void createDuplicateCourier() {
        createCourierData = new CreateCourierData("HuraYavYandex", "jackychan", "Jacky");
        loginCourierData = new LoginCourierData("HuraYavYandex", "jackychan");
        apiCourier.create(createCourierData);

        CreateCourierData createCourierData2 = new CreateCourierData("HuraYavYandex", "jackychan2", "Jacky2");
        apiCourier.create(createCourierData2).statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void cleanUp() {
        apiCourier.delete(loginCourierData);
    }
}
