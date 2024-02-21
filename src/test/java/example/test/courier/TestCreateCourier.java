package example.test.courier;

import example.api.courier.ApiCourier;
import example.data.courier.CourierData;
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
        CourierData testCourierData = CourierData.validData();
        createCourierData = testCourierData.getCreateCourierData();
        loginCourierData = testCourierData.getLoginCourierData();

        ValidatableResponse response = apiCourier.create(createCourierData);
        response.statusCode(201).body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Регистрация без логина")
    public void createCourierWithoutLogin() {
        CourierData testCourierData = CourierData.noLoginData();
        createCourierData = testCourierData.getCreateCourierData();
        loginCourierData = testCourierData.getLoginCourierData();

        ValidatableResponse response = apiCourier.create(createCourierData);
        response.statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Регистрация без пароля")
    public void createCourierWithoutPassword() {
        CourierData testCourierData = CourierData.noPasswordData();
        createCourierData = testCourierData.getCreateCourierData();
        loginCourierData = testCourierData.getLoginCourierData();

        ValidatableResponse response = apiCourier.create(createCourierData);
        response.statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание дублирующегося пользователя")
    public void createDuplicateCourier() {
        CourierData testCourierData = CourierData.validData();
        createCourierData = testCourierData.getCreateCourierData();
        loginCourierData = testCourierData.getLoginCourierData();
        apiCourier.create(createCourierData);
        CreateCourierData createCourierDataDuplicate = testCourierData.getCreateCourierData();

        ValidatableResponse duplicateResponse = apiCourier.create(createCourierDataDuplicate);
        duplicateResponse.statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void cleanUp() {
        apiCourier.delete(loginCourierData);
    }
}
