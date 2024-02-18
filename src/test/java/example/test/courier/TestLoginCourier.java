package example.test.courier;

import example.api.courier.ApiCourier;
import example.data.courier.CreateCourierData;
import example.data.courier.LoginCourierData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestLoginCourier {
    private ApiCourier apiCourier;

    private CreateCourierData createCourierData;

    private LoginCourierData loginCourierData;

    @Before
    public void setUp() {
        apiCourier = new ApiCourier();
        createCourierData = new CreateCourierData("HuraYavYandex", "jackychan", "Jacky");
        apiCourier.create(createCourierData);
    }
    @Test
    @DisplayName("Курьер может авторизоваться")
    public void loginCourier() {
        loginCourierData = new LoginCourierData("HuraYavYandex", "jackychan");
        ValidatableResponse response = apiCourier.login(loginCourierData);
        ExtractableResponse<Response> extractableResponse = response.extract();
        assertEquals("Курьер не авторизован", extractableResponse.statusCode(), 200);
    }

    @Test
    @DisplayName("Система вернет ошибку если указать неправильный логин или пароль")
    public void loginCourierWithIncorrectPass() {
        loginCourierData = new LoginCourierData("HuraYavYandex", "jackychan1");
        ValidatableResponse response = apiCourier.login(loginCourierData);
        response.statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void loginCourierWithoutPassword() {
        loginCourierData = new LoginCourierData("HuraYavYandex", "");
        ValidatableResponse response = apiCourier.login(loginCourierData);
        response.statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void loginCourierWithoutLogin() {
        loginCourierData = new LoginCourierData("", "jackychan");
        ValidatableResponse response = apiCourier.login(loginCourierData);
        response.statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем система вернет ошибку")
    public void loginCourierByNotExistsLogin() {
        loginCourierData = new LoginCourierData("not_exists_login", "not_exists_pass");
        ValidatableResponse response = apiCourier.login(loginCourierData);
        response.statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void successLoginCourierReturnedId() {
        loginCourierData = new LoginCourierData("HuraYavYandex", "jackychan");
        ValidatableResponse response = apiCourier.login(loginCourierData);
        assertThat("Запрос не вернул id", response.extract().body().jsonPath().get("id"), notNullValue());
    }

    @After
    public void cleanUp() {
        apiCourier.delete(loginCourierData);
    }
}
