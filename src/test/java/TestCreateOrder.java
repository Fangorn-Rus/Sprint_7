import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreateOrder {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] colors;
        TestPOMCreateOrder obj;

    public TestCreateOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    @Before
    public void init() {
        RequestTest.setUp();
        obj = new TestPOMCreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);

    }


    @Parameterized.Parameters(name = "{8}")
    public static Object[][] getCredentials() {
        return new Object[][]{
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{"BLACK"}
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{"GREY"}
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{"BLACK", "GREY"}
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        new String[]{}
                }
        };
    }

    @Test
    public void testCreateOrder(){
        obj.createOrder()
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }

}
