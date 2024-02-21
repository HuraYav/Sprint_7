package example.test.orders;

import example.api.orders.ApiOrders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class TestListOrders {
    private ApiOrders apiOrders;

    @Before
    public void setUp() {
        apiOrders = new ApiOrders();
    }

    @Test
    @DisplayName("Возвращается список заказов")
    public void testGetListOrders() {
        ValidatableResponse response = apiOrders.getList();
        response.statusCode(200).body("orders", hasSize(greaterThan(0)));
    }
}
