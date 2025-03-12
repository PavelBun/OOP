package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
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
    @Test
    void testBakersAndCouriersWorkCorrectly() throws InterruptedException {
        PizazzStore store = new PizazzStore(10);

        Baker baker1 = new Baker(1, 1, store);
        Baker baker2 = new Baker(2, 2, store);

        Courier courier1 = new Courier("Nikita Krainov", 3, store);
        Courier courier2 = new Courier("Ochir Khubanov", 2, store);

        baker1.start();
        baker2.start();
        courier1.start();
        courier2.start();

        for (int i = 1; i <= 5; i++) {
            store.placeOrder(new Order(i));
        }

        store.stopAcceptingOrders();
        Thread.sleep(10000);

        baker1.join();
        baker2.join();
        courier1.join();
        courier2.join();

        int deliveredOrders =  5 - store.getPizzas(5).size();
        assertEquals(5, deliveredOrders, "All orders should be delivered");
    }
    @Test
    void testConfigWithMismatchedCourierArrays() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File("src/test/resources/config_mismatched.json");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PizazzCfg config = objectMapper.readValue(configFile, PizazzCfg.class);
            if (config.getCouriers().length != config.getCourierName().length) {
                throw new IllegalArgumentException("Mismatched courier arrays: couriers and courierName must have the same length");
            }
        });

        assertEquals("Mismatched courier arrays: couriers and courierName must have the same length", exception.getMessage());
    }
    @Test
    void testAllOrdersAreDelivered() throws InterruptedException {

        PizazzStore store = new PizazzStore(10);

        Baker baker = new Baker(1, 1, store);

        Courier courier = new Courier("Ochir Khubanov", 5, store);

        baker.start();
        courier.start();

        for (int i = 1; i <= 5; i++) {
            store.placeOrder(new Order(i));
        }

        Thread.sleep(10000);

        store.stopAcceptingOrders();

        baker.join();
        courier.join();

        int deliveredOrders =  5 - store.getPizzas(5).size();
        assertEquals(5, deliveredOrders, "All orders should be delivered");
    }
}