import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestPOMCreateCourier {
    private final String  login;
    private final String password;
    private final String firstName;

    public TestPOMCreateCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    private int courierId;
    private int getCourierId(CreateCourierDTO json) {
        Response response = given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.LOGIN_COURIER);

        return response.jsonPath().getInt("id");
    }

    @Step("курьера можно создать")
    public Response createCourier() {
        CreateCourierDTO obj = new CreateCourierDTO(login, password, firstName);

        Response response =  given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

        courierId = getCourierId(obj);
        return response;
    }

    @Step("нельзя создать двух одинаковых курьеров")
    public Response recreateCourier() {
        CreateCourierDTO obj = new CreateCourierDTO(login, password, firstName);

        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

        courierId = getCourierId(obj);

        return given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;

    }

    @Step("если одного из полей нет, запрос возвращает ошибку")
    public Response createCourierMissingField(){
        CreateCourierDTO obj = new CreateCourierDTO(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
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
