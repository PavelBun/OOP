package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {
    private final Queue<Order> queue = new LinkedList<>();

    public synchronized void add(Order order) {
        queue.add(order);
        notifyAll(); // Пробуждаем ожидающих пекарей
    }

    public synchronized Order take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Ожидание, пока заказов нет
        }
        return queue.poll(); // Берем первый заказ (FIFO)
    }
    public synchronized boolean isEmpty(){
        return queue.isEmpty();
    }
    public synchronized int size(){
        return queue.size();
    }
}
