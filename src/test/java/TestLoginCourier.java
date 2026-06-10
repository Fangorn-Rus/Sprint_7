import org.junit.Before;
import org.junit.Test;

public class TestLoginCourier {
    TestPOMLoginCourier obj;

    @Before
    public void init() {
        RequestTest.setUp();
        obj = new TestPOMLoginCourier();
    }

    @Test
    public void loginCourier(){
        obj.loginCourier();
        obj.loginCourierMissingField();
        obj.loginCourierWrongField();
    }
}
