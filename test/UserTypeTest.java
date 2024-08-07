package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTypeTest {

    @Test
    public void testUserTypeValues() {
        UserType[] expected = {UserType.ADMIN, UserType.RETAILER, UserType.CUSTOMER};
        UserType[] actual = UserType.values();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testValueOf() {
        assertEquals(UserType.ADMIN, UserType.valueOf("ADMIN"));
        assertEquals(UserType.RETAILER, UserType.valueOf("RETAILER"));
        assertEquals(UserType.CUSTOMER, UserType.valueOf("CUSTOMER"));
    }
}
