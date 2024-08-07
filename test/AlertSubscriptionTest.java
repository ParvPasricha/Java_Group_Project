

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import model.AlertSubscription;

import static org.junit.jupiter.api.Assertions.*;

public class AlertSubscriptionTest {
    private AlertSubscription alertSubscription;

    @BeforeEach
    public void setUp() {
        alertSubscription = new AlertSubscription("New York", "email", Arrays.asList("Apple", "Banana"));
    }

    @Test
    public void testGetLocation() {
        assertEquals("New York", alertSubscription.getLocation());
    }

    @Test
    public void testSetLocation() {
        alertSubscription.setLocation("Los Angeles");
        assertEquals("Los Angeles", alertSubscription.getLocation());
    }

    @Test
    public void testGetCommunicationMethod() {
        assertEquals("email", alertSubscription.getCommunicationMethod());
    }

    @Test
    public void testSetCommunicationMethod() {
        alertSubscription.setCommunicationMethod("phone");
        assertEquals("phone", alertSubscription.getCommunicationMethod());
    }

    @Test
    public void testGetFoodPreferences() {
        assertEquals(Arrays.asList("Apple", "Banana"), alertSubscription.getFoodPreferences());
    }

    @Test
    public void testSetFoodPreferences() {
        alertSubscription.setFoodPreferences(Arrays.asList("Orange", "Grapes"));
        assertEquals(Arrays.asList("Orange", "Grapes"), alertSubscription.getFoodPreferences());
    }
}
