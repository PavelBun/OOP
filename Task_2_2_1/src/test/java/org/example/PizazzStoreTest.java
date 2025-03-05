package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PizazzStoreTest {
    @Test
    void testOrderPlacement() {
        PizazzStore pizzeria = new PizazzStore(5);
        Order order = new Order(1);

        pizzeria.placeOrder(order);

        Order retrievedOrder = null;
        try {
            retrievedOrder = pizzeria.getOrder();
        } catch (InterruptedException e) {
            fail("InterruptedException thrown");
        }

        assertNotNull(retrievedOrder);
        assertEquals(1, retrievedOrder.getId());
    }

    @Test
    void testStorageCapacity() throws InterruptedException {
        PizazzStore pizzeria = new PizazzStore(2);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        pizzeria.storePizza(order1);
        pizzeria.storePizza(order2);

        assertEquals(2, pizzeria.getPizzas(2).size());
    }

    @Test
    void testCourierPickup() throws InterruptedException {
        PizazzStore pizzeria = new PizazzStore(5);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        pizzeria.storePizza(order1);
        pizzeria.storePizza(order2);

        List<Order> pickedUpOrders = pizzeria.getPizzas(3);

        assertEquals(2, pickedUpOrders.size());
    }

    @Test
    void testStopAcceptingOrders() throws InterruptedException {
        PizazzStore pizzeria = new PizazzStore(5);
        pizzeria.stopAcceptingOrders();

        Order order = new Order(1);
        pizzeria.placeOrder(order);

        assertNull(pizzeria.getOrder());
    }
}