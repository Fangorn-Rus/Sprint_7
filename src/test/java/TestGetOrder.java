import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetOrder {
    TestPOMGetOrder obj;

    @Before
    public void init() {
        RequestTest.setUp();
        obj = new TestPOMGetOrder();
    }

    @Test
    public void getOrder(){

        obj.getOrder()
                .then().assertThat().body("orders[0].id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

}
