import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Step("Удаление курьера")
    public static void deleteCourier(File json){
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                ;
        int courierId = response.jsonPath().getInt("id");
        String deleteUrl = "/api/v1/courier/" + courierId;
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(deleteUrl);
    }

    @Test
    @Step("курьера можно создать")
    public void createCourier() {
        File json = new File("src/test/resources/newCourierData.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201)
        ;

        deleteCourier(json);
    }

    @Test
    @Step("нельзя создать двух одинаковых курьеров")
    public void recreateCourier() {
        File json = new File("src/test/resources/newCourierData.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
        ;

        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409)
        ;

        deleteCourier(json);

    }

    @Test
    @Step("если одного из полей нет, запрос возвращает ошибку")
    public void createCourierMissingField(){
        File json = new File("src/test/resources/CourierDataMissingField.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400)
        ;
    }
}
