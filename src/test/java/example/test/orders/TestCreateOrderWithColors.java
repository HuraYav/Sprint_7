package example.test.orders;

import example.api.orders.ApiOrders;
import example.data.order.CreateOrderData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreateOrderWithColors {
    private ApiOrders apiOrders;

    private String[] colors;

    private CreateOrderData createOrderData;

    public TestCreateOrderWithColors(String[] colors) {
        colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] colorsVariations(){
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK","GREY"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setUp() {
        apiOrders = new ApiOrders();
        createOrderData = new CreateOrderData(
                "naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors
        );
    }

    @Test
    @DisplayName("Проверка создания заказа с различными цветами")
    public void testCreateOrderWithColors() {
        apiOrders.createOrder(createOrderData).statusCode(201).assertThat().body("track", notNullValue());
    }
}
