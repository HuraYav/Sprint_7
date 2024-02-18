package example.api.orders;

import example.BaseHttpClient;
import example.data.order.CreateOrderData;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ApiOrders {
    public ValidatableResponse createOrder(CreateOrderData createOrderData) {
        return given().spec(BaseHttpClient.baseRequestSpec())
                .body(createOrderData)
                .post("/api/v1/orders")
                .then()
                .log().all();
    }

    public ValidatableResponse getList() {
        return given().spec(BaseHttpClient.baseRequestSpec())
                .get("/api/v1/orders")
                .then()
                .log().all();
    }
}
