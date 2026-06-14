import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestLoginCourier {
    TestPOMLoginCourier obj;
    File loginCourierMissingFieldLoginJson = new File("src/test/resources/CourierDataMissingFieldLogin.json");
    File loginCourierMissingFieldPasswordJson = new File("src/test/resources/CourierDataMissingFieldPassword.json");
    File createCourierJson = new File("src/test/resources/newCourierData.json");
    File loginCourierJson = new File("src/test/resources/loginCourier.json");
    File loginCourierMissingFieldJson = new File("src/test/resources/loginCourierWrongField.json");

    @Before
    public void init() {
        RequestTest.setUp();
        obj = new TestPOMLoginCourier();
    }

    @Test
    public void loginCourier() {
        obj.loginCourier(createCourierJson, loginCourierJson)
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void loginCourierMissingFieldLogin() {
        obj.loginCourierMissingField(loginCourierMissingFieldLoginJson)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }
    @Test
    public void loginCourierMissingFieldPassword() {
        obj.loginCourierMissingField(loginCourierMissingFieldPasswordJson)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void loginCourierWrongField() {
        obj.loginCourierWrongField(loginCourierMissingFieldJson)
                .then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
    }


