import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestPOMCreateCourier {

    private int courierId;
    private int getCourierId(File json) {
        Response response = given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.LOGIN_COURIER);

        return response.jsonPath().getInt("id");
    }

    @DisplayName("курьера можно создать")
    public void createCourier() {
        File json = new File("src/test/resources/newCourierData.json");
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.CREATE_COURIER)
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED)
        ;

        courierId = getCourierId(json);
    }
    @DisplayName("нельзя создать двух одинаковых курьеров")
    public void recreateCourier() {
        File json = new File("src/test/resources/newCourierData.json");
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

        courierId = getCourierId(json);

        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.CREATE_COURIER)
                .then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT)
        ;

    }

    @DisplayName("если одного из полей нет, запрос возвращает ошибку")
    public void createCourierMissingField(){
        File json = new File("src/test/resources/newCourierDataMissingPassword.json");
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.CREATE_COURIER)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST)
        ;
    }

    @DisplayName("удаление курьера")
    public void deleteCourier() {
        if (courierId > 0) {
            given()
                    .header("Content-type", "application/json")
                    .spec(RequestTest.requestSpec)
                    .when()
                    .delete(Endpoints.CREATE_COURIER + "/" + courierId)
                    .then().statusCode(SC_OK);
        }
    }
}
