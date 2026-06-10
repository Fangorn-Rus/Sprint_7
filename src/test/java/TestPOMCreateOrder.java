import io.qameta.allure.junit4.DisplayName;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestPOMCreateOrder {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] colors;


    public TestPOMCreateOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    @DisplayName("Создание заказа")
    public void createOrder(){

        OrderDTO order = new OrderDTO(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, colors);

        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(order)
                .when()
                .post(Endpoints.CREATE_ORDER)
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED)
        ;
    }
}
