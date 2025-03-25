package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.StringWriter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

        Worker baker1 = new Baker(1, 1, store);
        Worker baker2 = new Baker(2, 2, store);
        Worker courier1 = new Courier("Nikita Krainov", 3, store);
        Worker courier2 = new Courier("Ochir Khubanov", 2, store);

        Thread t1 = new Thread(baker1);
        Thread t2 = new Thread(baker2);
        Thread t3 = new Thread(courier1);
        Thread t4 = new Thread(courier2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        for (int i = 1; i <= 5; i++) {
            store.placeOrder(new Order(i));
        }

        store.stopAcceptingOrders();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        List<Order> remaining = store.getPizzas(5);
        assertEquals(0, remaining.size(), "All orders should be delivered");
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

        Worker baker = new Baker(1, 1, store);
        Worker courier = new Courier("Ochir Khubanov", 5, store);

        Thread t1 = new Thread(baker);
        Thread t2 = new Thread(courier);

        t1.start();
        t2.start();

        for (int i = 1; i <= 5; i++) {
            store.placeOrder(new Order(i));
        }

        store.stopAcceptingOrders();

        t1.join();
        t2.join();

        List<Order> remaining = store.getPizzas(5);
        assertEquals(0, remaining.size(), "All orders should be delivered");
    }
    @Test
    void testMainExecutionWithLogs() throws IOException, InterruptedException {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("%msg%n")
                .build();

        StringWriter writer = new StringWriter();
        WriterAppender appender = WriterAppender.newBuilder()
                .setName("TestAppender")
                .setLayout(layout)
                .setTarget(writer)
                .build();

        appender.start();
        context.getRootLogger().addAppender(appender);
        context.updateLoggers();

        try {
            // Убедитесь, что config.json содержит orderCount: 20
            Main.main(new String[]{});

            // Увеличьте время ожидания для завершения всех потоков
            Thread.sleep(15000);

            String logs = writer.toString();
            long placedOrders = logs.lines()
                    .filter(line -> line.contains("placed successfully"))
                    .count();

            long cookedOrders = logs.lines()
                    .filter(line -> line.contains("is cooking"))
                    .count();

            long deliveredPizzas = logs.lines()
                    .filter(line -> line.contains("delivering") && line.contains("pizzas"))
                    .mapToLong(line -> {
                        String[] parts = line.split(" ");
                        return Long.parseLong(parts[parts.length - 2]);
                    })
                    .sum();

            assertEquals(20, placedOrders, "Размещено заказов");
            assertEquals(20, cookedOrders, "Приготовлено заказов");
            assertEquals(20, deliveredPizzas, "Доставлено пицц");
        } finally {
            appender.stop();
            context.getRootLogger().removeAppender(appender);
            context.updateLoggers();
        }
    }
    @Test
    void testPlaceOrderDuringWork() throws IOException, InterruptedException {
        final Logger logger = LogManager.getLogger(PizazzStoreTest.class);
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("%msg%n")
                .build();

        StringWriter writer = new StringWriter();
        WriterAppender appender = WriterAppender.newBuilder()
                .setName("TestAppender")
                .setLayout(layout)
                .setTarget(writer)
                .build();

        appender.start();
        context.getRootLogger().addAppender(appender);
        context.updateLoggers();
        logger.info("Starting Pizzeria application...");
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizazzCfg config = mapper.readValue(new File("config.json"), PizazzCfg.class);

            PizazzStore store = new PizazzStore(config.getStorageCapacity());
            Pizzeria pizzeria = new Pizzeria(store);
            pizzeria.initializeFromConfig("config.json");
            // add order
            store.placeOrder(new Order(config.getOrderCount() + 1));
            Thread.sleep(7000);
            pizzeria.stopPizzeria(config.getWorkTime());


            // Проверка логов
            String logs = writer.toString();
            long placedOrders = logs.lines().filter(line -> line.contains("placed successfully")).count();
            long cookedOrders = logs.lines().filter(line -> line.contains("is cooking")).count();
            long deliveredPizzas = logs.lines()
                    .filter(line -> line.contains("delivering") && line.contains("pizzas"))
                    .mapToLong(line -> {
                        // Извлечение числа
                        String[] parts = line.split(" ");
                        return Long.parseLong(parts[parts.length - 2]); // индекс числа
                    })
                    .sum();
            assertEquals(21, placedOrders, "All 21 orders should be placed");
            assertEquals(21, cookedOrders, "All 21 orders should be cooked");
            assertEquals(21, deliveredPizzas, "All 21 orders should be delivered");
        }
        catch (IOException | InterruptedException e) {
            logger.error("An error occurred: {}", e.getMessage());
        }
        finally {
            appender.stop();
            context.getRootLogger().removeAppender(appender);
            context.updateLoggers();
        }
    }

}
