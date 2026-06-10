import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCreateCourier {
    TestPOMCreateCourier obj;

    @Before
    public void init() {
        RequestTest.setUp();
        obj =  new TestPOMCreateCourier();
    }

    @After
    public void cleanUp() {
        obj.deleteCourier();
    }

    @Test
    public void TestCourier(){
        obj.createCourier();
        obj.recreateCourier();
        obj.createCourierMissingField();
    }
}
