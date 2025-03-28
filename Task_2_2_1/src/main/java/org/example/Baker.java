package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Baker implements Worker {
    private static final Logger logger = LogManager.getLogger(Baker.class);

    private int cookingSpeed;
    private int id;
    private final PizazzStore store;
    private volatile boolean running = true;

    public Baker(int id, int cookingSpeed, PizazzStore store) {
        this.id = id;
        this.cookingSpeed = cookingSpeed;
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (!store.isShutdown()) {
                Order order = store.getOrder();
                if (order == null) {
                    break;
                }
                logger.info("Order: {} is cooking by baker number: {}", order.getId(), id);
                Thread.sleep(cookingSpeed * 1000);
                store.storePizza(order);
            }
        } catch (InterruptedException e) {
            logger.error("Baker {} was interrupted: {}", id, e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info("Baker {} has finished work.", id);
    }
    @Override
    public void stop() {
        running = false;
    }
}