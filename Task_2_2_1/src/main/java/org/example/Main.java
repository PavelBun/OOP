package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Pizzeria application...");

        try {
            // Создаем хранилище
            PizazzStore pizazzStore = new PizazzStore(10); // Вместимость хранилища можно вынести в конфиг

            // Создаем менеджер пиццерии
            Pizzeria pizzeria = new Pizzeria(pizazzStore);

            // Инициализируем пиццерию из конфига
            pizzeria.initializeFromConfig("config.json");

            // Запускаем пиццерию на определенное время (в секундах)
            pizzeria.stopPizzeria(60); // Время работы можно вынести в конфиг
        } catch (IOException | InterruptedException e) {
            logger.error("An error occurred while running the pizzeria: {}", e.getMessage());
        }
    }
}