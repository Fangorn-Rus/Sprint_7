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
    public void testGetOrder(){

        obj.getOrder("/api/v1/orders?limit=10&page=0")
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders[0].id", notNullValue())
                ;
    }

}
