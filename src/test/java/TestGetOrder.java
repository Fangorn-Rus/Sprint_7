import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetOrder {

    @Before
    public void init() {
        RequestTest.setUp();
    }

    @Test
    @DisplayName("тело ответа возвращается список заказов")
    public void getOrder(){
        given()
                .spec(RequestTest.requestSpec)
                .get( "/api/v1/orders?limit=10&page=0")
                .then().assertThat().body("orders[0].id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

}
