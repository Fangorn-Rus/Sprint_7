import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestPOMGetOrder {

    @Step("Получение заказа")
    public Response getOrder(){
        return
        given()
                .spec(RequestTest.requestSpec)
                .get( "/api/v1/orders?limit=10&page=0");
    }
}
