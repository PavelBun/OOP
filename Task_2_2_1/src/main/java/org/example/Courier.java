package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Courier implements Worker{
    private static final Logger logger = LogManager.getLogger(Courier.class);

    String name;
    int trunkCapacity;
    private PizazzStore store;
    private volatile boolean running = true;

    public Courier(String name, int trunkCapacity, PizazzStore store) {
        this.name = name;
        this.trunkCapacity = trunkCapacity;
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (!store.isShutdown()) {
                List<Order> orders = store.getPizzas(trunkCapacity);
                if (orders.isEmpty()) {
                    break;
                }
                logger.info("Courier: {} is delivering {} pizzas", name, orders.size());
                Thread.sleep(8000);
                logger.info("Courier {} delivered order", name);
            }
        } catch (InterruptedException e) {
            logger.error("Courier {} was interrupted: {}", name, e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info("Courier {} has finished work.", name);
    }

    @Override
    public void stop() {
        running = false;
    }
}