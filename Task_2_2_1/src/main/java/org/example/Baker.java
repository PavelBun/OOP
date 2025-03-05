package org.example;

public class Baker extends Thread {
    private int cookingSpeed;
    private int id;
    private final PizazzStore store;
    public Baker(int id, int cookingSpeed, PizazzStore store){
        this.id = id;
        this.cookingSpeed = cookingSpeed;
        this.store = store;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Order order  = store.getOrder();
                if (order == null) {break;}
                System.out.println("Order: " + order.getId() + " is coocking by baker number: " + id);
                Thread.sleep(cookingSpeed * 2000);
                store.storePizza(order);
            }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
