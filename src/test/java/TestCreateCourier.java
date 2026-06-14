import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {
    TestPOMCreateCourier obj;
    File newCourierData = new File("src/test/resources/newCourierData.json");
    File newCourierDataMissingLogin = new File("src/test/resources/newCourierDataMissingLogin.json");
    File newCourierDataMissingPassword = new File("src/test/resources/newCourierDataMissingPassword.json");

    @Before
    public void init() {
        RequestTest.setUp();
        obj =  new TestPOMCreateCourier();
    }

    @After
    public void cleanUp() {
        obj.deleteCourier().then().statusCode(SC_OK);
    }

    @Test
    public void TestCourier(){
        obj.createCourier(newCourierData)
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);

        obj.recreateCourier(newCourierData)
                .then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);

        obj.createCourierMissingField(newCourierDataMissingLogin)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);

        obj.createCourierMissingField(newCourierDataMissingPassword)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}
