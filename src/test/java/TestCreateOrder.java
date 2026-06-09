import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreateOrder {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] colors;

    public TestCreateOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = color;
    }


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{"BLACK"}
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{"BLACK", "GREY"}
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{}
                }
        };
    }

    private String arrayToJson(String[] array) {
        if (array == null || array.length == 0) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            json.append("\"").append(array[i]).append("\"");
            if (i < array.length - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    @Test
    @Step("Создание заказа")
    public void createOrder(){
        String colorsJson = arrayToJson(colors);
        String requestBody = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"address\":\"%s\",\"metroStation\":\"%s\",\"phone\":\"%s\",\"rentTime\":%d,\"deliveryDate\":\"%s\",\"comment\":\"%s\",\"colors\":%s}",
                firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colorsJson
        );

        given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders")
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201)
        ;
    }

}
