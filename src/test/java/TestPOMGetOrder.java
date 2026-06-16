import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestPOMGetOrder {

    @Step("Получение заказа")
    public Response getOrder(String url){
        return
        given()
                .spec(RequestTest.requestSpec)
                .get( url);
    }
}
