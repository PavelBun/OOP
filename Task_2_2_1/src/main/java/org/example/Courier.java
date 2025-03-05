package org.example;

import java.util.List;

public class Courier extends Thread{
    String name;
    int trunkCapacity;
    private PizazzStore store;
    public Courier (String name, int trunkCapacity, PizazzStore store){
        this.name = name;
        this.trunkCapacity = trunkCapacity;
        this.store = store;
    }
    @Override
    public void run(){
        try{
            while(true){
                List<Order> orders = store.getPizzas(trunkCapacity);
                if (orders.isEmpty()){break;}
                System.out.println("Courier: " + name + " is delivering " + orders.size() + " pizzas" );
                Thread.sleep(8000);
                System.out.println("Courier " + name + " delivered order");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
