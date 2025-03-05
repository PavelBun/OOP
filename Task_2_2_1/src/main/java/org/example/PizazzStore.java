package org.example;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
public class PizazzStore {
    private final Queue<Order> orders = new LinkedList<>();
    private final Queue<Order> storage = new LinkedList<>();
    private final int capacity;
    private boolean acceptingOrders = true;
    public PizazzStore(int capacity) {
        this.capacity = capacity;
    }
    public synchronized void placeOrder(Order order){
        if(!acceptingOrders){return;}
        orders.add(order);
        notifyAll();
    }
    public synchronized Order getOrder() throws InterruptedException {
        while (orders.isEmpty() && acceptingOrders) {
            wait();
        }
        return orders.poll();
    }
    public synchronized void storePizza(Order order) throws InterruptedException{
        while (storage.size() >= capacity){
            wait();
        }
        storage.add(order);
        System.out.println("Order: " + order.getId() + " is ready");
        notifyAll();
    }
    public synchronized List<Order> getPizzas(int max) throws InterruptedException{
        while(storage.isEmpty()){
            wait();
        }
        List<Order> batch = new LinkedList<>();
        while (!storage.isEmpty() && batch.size() < max){
            batch.add(storage.poll());
        }
        notifyAll();
        return batch;
    }
    public synchronized void stopAcceptingOrders() {
        acceptingOrders = false;
        notifyAll();
    }
}
