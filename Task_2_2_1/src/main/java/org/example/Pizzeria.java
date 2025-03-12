package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Pizzeria {
    private static final Logger logger = LogManager.getLogger(Pizzeria.class);

    private final PizazzStore pizazzStore;
    private final List<Baker> bakers;
    private final List<Courier> couriers;

    public Pizzeria(PizazzStore pizazzStore) {
        this.pizazzStore = pizazzStore;
        this.bakers = new LinkedList<>();
        this.couriers = new LinkedList<>();
    }

    public void initializeFromConfig(String configFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PizazzCfg config = objectMapper.readValue(new File(configFilePath), PizazzCfg.class);

        // Создаем и запускаем пекарей
        for (int i = 0; i < config.getBakers().length; i++) {
            Baker baker = new Baker(i + 1, config.getBakers()[i], pizazzStore);
            bakers.add(baker);
            baker.start();
        }

        // Создаем и запускаем курьеров
        for (int i = 0; i < config.getCouriers().length; i++) {
            Courier courier = new Courier(config.getCourierName()[i], config.getCouriers()[i], pizazzStore);
            couriers.add(courier);
            courier.start();
        }

        // Размещаем заказы
        for (int i = 1; i <= config.getOrderCount(); i++) {
            pizazzStore.placeOrder(new Order(i));
        }
    }

    public void stopPizzeria(int workTime) throws InterruptedException {
        // Ждем завершения рабочего времени
        Thread.sleep(workTime * 800);

        // Останавливаем прием заказов и завершаем работу
        pizazzStore.stopAcceptingOrders();

        // Ждем завершения всех пекарей
        for (Baker baker : bakers) {
            baker.join();
        }

        // Ждем завершения всех курьеров
        for (Courier courier : couriers) {
            courier.join();
        }

        logger.info("Pizzeria closed, bakers and couriers stopped working.");
    }
}