import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {
    TestPOMCreateCourier obj;

    @Before
    public void init() {
        RequestTest.setUp();
    }

    @After
    public void cleanUp() {
        obj.deleteCourier().then().statusCode(SC_OK);
    }


    @Test
    public void testCreateCourier() {
        obj = new TestPOMCreateCourier("Sprint7courier20", "12345", "Alex");
        obj.createCourier()
                .then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true))
                ;
    }

    @Test
    public void testRecreateCourier() {
        obj = new TestPOMCreateCourier("Sprint7courier20", "12345", "Alex");
        obj.recreateCourier()
                .then().assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                ;
    }

    @Test
    public void testCourierDataMissingLogin() {
        obj = new TestPOMCreateCourier(null, "12345", "Alex");
        obj.createCourierMissingField()
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                ;
    }

    @Test
    public void testCourierDataMissingPassword() {
        obj = new TestPOMCreateCourier("Sprint7courier20", null, "Alex");
        obj.createCourierMissingField()
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
        ;
    }
}
