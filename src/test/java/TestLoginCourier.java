import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestLoginCourier {
    TestPOMLoginCourier obj;

    @Before
    public void init() {
        RequestTest.setUp();
    }

    @Test
    public void testLoginCourier() {
        obj = new TestPOMLoginCourier("Sprint7courier20", "12345", "Alex");
        obj.loginCourier()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue())
;
    }

    @Test
    public void testLoginCourierMissingFieldLogin() {
        obj = new TestPOMLoginCourier(null, "12345", "Alex");
        obj.loginCourierMissingField()
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"))
                ;

    }
    @Test
    public void testLoginCourierMissingFieldPassword() {
        obj = new TestPOMLoginCourier("Sprint7courier20", null, "Alex");
        obj.loginCourierMissingField()
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"))
                ;
    }

    @Test
    public void testLoginCourierWrongFieldLogin() {
        obj = new TestPOMLoginCourier("Sprint7courierFake", "12345", "Alex");
        obj.loginCourierWrongField()
                .then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"))
                ;
    }

    @Test
    public void testLoginCourierWrongFieldPassword() {
        obj = new TestPOMLoginCourier("Sprint7courier20", "12345Fake", "Alex");
        obj.loginCourierWrongField()
                .then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"))
        ;
    }
}





