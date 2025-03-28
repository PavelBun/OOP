package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class PizazzStore {
    private static final Logger logger = LogManager.getLogger(PizazzStore.class);

    private final BlockingQueue<Order> orders = new BlockingQueue<>();
    private final BlockingQueue<Order> storage = new BlockingQueue<>();
    private final int capacity;
    private boolean acceptingOrders = true;
    private boolean isShutdown = false;

    public PizazzStore(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void placeOrder(Order order) {
        if (!acceptingOrders) {
            logger.warn("Order {} was rejected because the store is not accepting orders.", order.getId());
            return;
        }
        orders.add(order);
        logger.info("Order {} placed successfully.", order.getId());
        notifyAll();
    }

    public synchronized Order getOrder() throws InterruptedException {
        while (orders.isEmpty() && !isShutdown) {
            wait();
        }
        if (isShutdown && orders.isEmpty()) {
            logger.info("No more orders to process. Shutting down.");
            return null;
        }
        Order order = orders.take();
        logger.info("Order {} taken by baker.", order.getId());
        return order;
    }

    public synchronized void storePizza(Order order) throws InterruptedException {
        while (storage.size() >= capacity && !isShutdown) {
            wait();
        }
        if (isShutdown) {
            logger.warn("Order {} was not stored because the store is shutting down.", order.getId());
            return;
        }
        storage.add(order);
        logger.info("Order {} is ready and stored.", order.getId());
        notifyAll();
    }

    public synchronized List<Order> getPizzas(int max) throws InterruptedException {
        while (storage.isEmpty() && !isShutdown) {
            wait();
        }
        if (isShutdown && storage.isEmpty()) {
            logger.info("No more pizzas to deliver. Shutting down.");
            return new LinkedList<>();
        }
        List<Order> batch = new LinkedList<>();
        while (!storage.isEmpty() && batch.size() < max) {
            batch.add(storage.take());
        }
        logger.info("{} pizzas taken by courier.", batch.size());
        notifyAll();
        return batch;
    }

    public synchronized void stopAcceptingOrders() {
        acceptingOrders = false;
        isShutdown = true;
        logger.info("Store is no longer accepting orders. Shutting down.");
        notifyAll();
    }

    public synchronized boolean isShutdown() {
        return isShutdown;
    }
}