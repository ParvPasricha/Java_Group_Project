package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumerTest {
    private Consumer consumer;

    @BeforeEach
    public void setUp() {
        consumer = new Consumer("ConsumerName", "consumer@example.com", "password");
    }

    @Test
    public void testGetUsername() {
        assertEquals("ConsumerName", consumer.getUsername());
    }

    @Test
    public void testGetEmail() {
        assertEquals("consumer@example.com", consumer.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", consumer.getPassword());
    }

    @Test
    public void testGetUserType() {
        assertEquals("consumer", consumer.getUserType());
    }
}
