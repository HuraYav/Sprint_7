package example.api.courier;

import example.BaseHttpClient;
import example.data.courier.CreateCourierData;
import example.data.courier.LoginCourierData;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ApiCourier {
    public static final String postLoginUrl = "/api/v1/courier/login";

    public static final String postCreateUrl = "/api/v1/courier";
    public static final String deleteUrl = "/api/v1/courier/%d";

    public ValidatableResponse login(LoginCourierData loginData) {
        return given().spec(BaseHttpClient.baseRequestSpec())
                .body(loginData)
                .post(postLoginUrl)
                .then()
                .log().all();
    }

    public ValidatableResponse create(CreateCourierData createData) {
        return given().spec(BaseHttpClient.baseRequestSpec())
                .body(createData)
                .post(postCreateUrl)
                .then()
                .log().all();
    }

    public void delete(LoginCourierData loginData) {
        ValidatableResponse loginResponse = login(loginData);
        ExtractableResponse<Response> extractableLoginResponse = loginResponse.extract();
        int statusCode = extractableLoginResponse.statusCode();
        if (statusCode == 200) {
            given().spec(BaseHttpClient.baseRequestSpec()).delete(
                    String.format(
                            deleteUrl,
                            extractableLoginResponse.body().jsonPath().getInt("id")
                    )
            );
        }
    }
}
