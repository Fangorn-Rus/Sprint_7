import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestPOMLoginCourier {

    @DisplayName("создание курьера")
    public static void createCourier(File json){
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;
    }

    @DisplayName("удаление курьера")
    public static void deleteCourier(File json){
        Response response = given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.LOGIN_COURIER)
                ;
        int courierId = response.jsonPath().getInt("id");
        String deleteUrl = Endpoints.CREATE_COURIER + courierId;
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .when()
                .delete(deleteUrl);
    }


    @DisplayName("курьер может авторизоваться")
    public void loginCourier(){
        File createCourierJson = new File("src/test/resources/newCourierData.json");
        File loginCourierJson = new File("src/test/resources/loginCourier.json");

        createCourier(createCourierJson);

        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(loginCourierJson)
                .when()
                .post(Endpoints.LOGIN_COURIER)
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK)
        ;

        deleteCourier(createCourierJson);

    }

    @DisplayName("если какого-то поля нет, запрос возвращает ошибку")
    public void loginCourierMissingField(){
        File loginCourierMissingFieldLoginJson = new File("src/test/resources/CourierDataMissingFieldLogin.json");
        File loginCourierMissingFieldPasswordJson = new File("src/test/resources/CourierDataMissingFieldPassword.json");

        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(loginCourierMissingFieldLoginJson)
                .when()
                .post(Endpoints.LOGIN_COURIER)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST)
        ;

        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(loginCourierMissingFieldPasswordJson)
                .when()
                .post(Endpoints.LOGIN_COURIER)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST)
        ;
    }

    @DisplayName("система вернёт ошибку, если неправильно указать логин или пароль")
    public void loginCourierWrongField(){
        File loginCourierMissingFieldJson = new File("src/test/resources/loginCourierWrongField.json");
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(loginCourierMissingFieldJson)
                .when()
                .post(Endpoints.LOGIN_COURIER)
                .then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND)
        ;
    }
}
