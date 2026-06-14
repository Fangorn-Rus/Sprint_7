import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;
import static io.restassured.RestAssured.given;

public class TestPOMLoginCourier {

    @Step("создание курьера")
    public static void createCourier(File json){
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(json)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;
    }

    @Step("удаление курьера")
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


    @Step("курьер может авторизоваться")
    public Response loginCourier(File newCourierData, File loginCourier){

        createCourier(newCourierData);

        Response response = given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(loginCourier)
                .when()
                .post(Endpoints.LOGIN_COURIER)
        ;

        deleteCourier(newCourierData);
        return response;

    }

    @Step("если какого-то поля нет, запрос возвращает ошибку")
    public Response loginCourierMissingField(File file){
             return given()
                    .header("Content-type", "application/json")
                    .spec(RequestTest.requestSpec)
                    .body(file)
                    .when()
                    .post(Endpoints.LOGIN_COURIER)
                    ;
    }

    @Step("система вернёт ошибку, если неправильно указать логин или пароль")
    public Response loginCourierWrongField(File loginCourierWrongField){

        return given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(loginCourierWrongField)
                .when()
                .post(Endpoints.LOGIN_COURIER)
        ;
    }
}
