import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestLoginCourier {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Step("создание курьера")
    public static void createCourier(File json){
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
        ;
    }

    @Step("удаление курьера")
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
    @Step("курьер может авторизоваться")
    public void loginCourier(){
        File createCourierJson = new File("src/test/resources/newCourierData.json");
        File loginCourierJson = new File("src/test/resources/loginCourier.json");

        createCourier(createCourierJson);

        given()
                .header("Content-type", "application/json")
                .body(loginCourierJson)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200)
        ;

        deleteCourier(createCourierJson);

    }

    @Test
    @Step("если какого-то поля нет, запрос возвращает ошибку")
    public void loginCourierMissingField(){
        File loginCourierMissingFieldJson = new File("src/test/resources/loginCourierMissingField.json");

        given()
                .header("Content-type", "application/json")
                .body(loginCourierMissingFieldJson)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400)
        ;
    }

    @Test
    @Step("система вернёт ошибку, если неправильно указать логин или пароль")
    public void loginCourierWrongField(){
        File loginCourierMissingFieldJson = new File("src/test/resources/loginCourierWrongField.json");
        given()
                .header("Content-type", "application/json")
                .body(loginCourierMissingFieldJson)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404)
        ;
    }


}
