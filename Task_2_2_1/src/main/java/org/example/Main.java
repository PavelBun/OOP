package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        PizazzCfg config = objectMapper.readValue(new File("config.json"), PizazzCfg.class);
        PizazzStore pizazzStore =  new PizazzStore(config.getStorageCapacity());
        List<Baker> bakers = new LinkedList<>();
        List<Courier> couriers = new LinkedList<>();
        for (int i = 0; i < config.getBakers().length; i++) {
            Baker baker = new Baker(i+1, config.getBakers()[i], pizazzStore);
            bakers.add(baker);
            baker.start();
        }
        for (int i = 0; i < config.getCouriers().length; i++) {
            Courier courier = new Courier(config.getCourierName()[i], config.getCouriers()[i], pizazzStore);
            couriers.add(courier);
            courier.start();
        }
        for (int i = 1; i < config.getOrderCount(); i++) {
            pizazzStore.placeOrder(new Order(i));
        }
        Thread.sleep(config.getWorkTime() * 1000);
        pizazzStore.stopAcceptingOrders();
        for (Baker baker : bakers) {
            baker.join();
        }

        for (Courier courier : couriers) {
            courier.join();
        }

    }
}