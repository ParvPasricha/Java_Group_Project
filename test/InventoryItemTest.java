package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemTest {
    private InventoryItem inventoryItem;

    @BeforeEach
    public void setUp() {
        inventoryItem = new InventoryItem();
        inventoryItem.setItemId(1);
        inventoryItem.setItemName("Apple");
        inventoryItem.setQuantity(10);
        inventoryItem.setExpirationDate(Date.valueOf("2024-08-10"));
        inventoryItem.setSurplus(false);
    }

    @Test
    public void testGetItemId() {
        assertEquals(1, inventoryItem.getItemId());
    }

    @Test
    public void testSetItemId() {
        inventoryItem.setItemId(2);
        assertEquals(2, inventoryItem.getItemId());
    }

    @Test
    public void testGetItemName() {
        assertEquals("Apple", inventoryItem.getItemName());
    }

    @Test
    public void testSetItemName() {
        inventoryItem.setItemName("Banana");
        assertEquals("Banana", inventoryItem.getItemName());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(10, inventoryItem.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        inventoryItem.setQuantity(20);
        assertEquals(20, inventoryItem.getQuantity());
    }

    @Test
    public void testGetExpirationDate() {
        assertEquals(Date.valueOf("2024-08-10"), inventoryItem.getExpirationDate());
    }

    @Test
    public void testSetExpirationDate() {
        inventoryItem.setExpirationDate(Date.valueOf("2024-08-15"));
        assertEquals(Date.valueOf("2024-08-15"), inventoryItem.getExpirationDate());
    }

    @Test
    public void testIsSurplus() {
        assertFalse(inventoryItem.isSurplus());
    }

    @Test
    public void testSetSurplus() {
        inventoryItem.setSurplus(true);
        assertTrue(inventoryItem.isSurplus());
    }
}
