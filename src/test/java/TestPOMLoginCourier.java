import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TestPOMLoginCourier {
    private final String  login;
    private final String password;
    private final String firstName;

    public TestPOMLoginCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("создание курьера")
    public void createCourier(){
        LoginCourierDTO obj = new LoginCourierDTO(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_COURIER)
        ;
    }

    @Step("удаление курьера")
    public void deleteCourier(){
        LoginCourierDTO obj = new LoginCourierDTO(login, password, firstName);
        Response response = given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
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
    public Response loginCourier(){
        LoginCourierDTO obj = new LoginCourierDTO(login, password, firstName);
        createCourier();

        Response response = given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.LOGIN_COURIER)
        ;

        deleteCourier();
        return response;

    }

    @Step("если какого-то поля нет, запрос возвращает ошибку")
    public Response loginCourierMissingField(){
        LoginCourierDTO obj = new LoginCourierDTO(login, password, firstName);
             return given()
                    .header("Content-type", "application/json")
                    .spec(RequestTest.requestSpec)
                    .body(obj)
                    .when()
                    .post(Endpoints.LOGIN_COURIER)
                    ;
    }

    @Step("система вернёт ошибку, если неправильно указать логин или пароль")
    public Response loginCourierWrongField(){
        LoginCourierDTO obj = new LoginCourierDTO(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .spec(RequestTest.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.LOGIN_COURIER)
        ;
    }
}
