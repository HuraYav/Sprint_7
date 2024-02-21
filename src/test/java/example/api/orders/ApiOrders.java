package example.api.orders;

import example.BaseHttpClient;
import example.data.order.CreateOrderData;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ApiOrders {
    public static final String postCreateUrl = "/api/v1/orders";
    public static final String getListUrl = "/api/v1/orders";
    public ValidatableResponse createOrder(CreateOrderData createOrderData) {
        return given().spec(BaseHttpClient.baseRequestSpec())
                .body(createOrderData)
                .post(postCreateUrl)
                .then()
                .log().all();
    }

    public ValidatableResponse getList() {
        return given().spec(BaseHttpClient.baseRequestSpec())
                .get(getListUrl)
                .then()
                .log().all();
    }
}
