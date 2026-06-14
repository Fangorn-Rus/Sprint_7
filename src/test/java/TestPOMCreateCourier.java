import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;
import static io.restassured.RestAssured.given;

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

    @Step("курьера можно создать")
    public Response createCourier(File newCourierData) {
        Response response =  given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(newCourierData)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

        courierId = getCourierId(newCourierData);
        return response;
    }

    @Step("нельзя создать двух одинаковых курьеров")
    public Response recreateCourier(File newCourierData) {
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(newCourierData)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

        courierId = getCourierId(newCourierData);

        return given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(newCourierData)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

    }

    @Step("если одного из полей нет, запрос возвращает ошибку")
    public Response createCourierMissingField(File createCourierMissingField){
        return given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(createCourierMissingField)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;
    }

    @Step("удаление курьера")
    public Response deleteCourier() {
            return given()
                    .header("Content-type", "application/json")
                    .spec(RequestTest.requestSpec)
                    .when()
                    .delete(Endpoints.CREATE_COURIER + "/" + courierId)
            ;
    }
}
