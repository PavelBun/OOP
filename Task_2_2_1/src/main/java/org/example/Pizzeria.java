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
    private final List<Worker> workers = new LinkedList<>();

    private final List<Thread> threads = new LinkedList<>();

    public Pizzeria(PizazzStore pizazzStore) {
        this.pizazzStore = pizazzStore;
    }

    public void initializeFromConfig(String configFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PizazzCfg config = objectMapper.readValue(new File(configFilePath), PizazzCfg.class);

        // Создаем пекарей
        for (int i = 0; i < config.getBakers().length; i++) {
            Worker baker = new Baker(i + 1, config.getBakers()[i], pizazzStore);
            workers.add(baker);
            Thread bakerThread = new Thread(baker);
            threads.add(bakerThread);
            bakerThread.start();
        }

        // Создаем курьеров
        for (int i = 0; i < config.getCouriers().length; i++) {
            Worker courier = new Courier(config.getCourierName()[i], config.getCouriers()[i], pizazzStore);
            workers.add(courier);
            Thread courierThread = new Thread(courier);
            threads.add(courierThread);
            courierThread.start();
        }


        // Размещаем заказы
        for (int i = 1; i <= config.getOrderCount(); i++) {
            pizazzStore.placeOrder(new Order(i));
        }
    }

    public void stopPizzeria(int workTime) throws InterruptedException {
        Thread.sleep(workTime * 1000L);
        pizazzStore.stopAcceptingOrders();

        // Остановка всех работников
        workers.forEach(Worker::stop);

        // Прерывание потоков
        threads.forEach(Thread::interrupt);

        // Ожидание завершения
        for (Thread thread : threads) {
            thread.join();
        }


        logger.info("Pizzeria closed, all workers stopped.");
    }
}