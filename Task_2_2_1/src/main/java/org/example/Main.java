package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Pizzeria application...");
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizazzCfg config = mapper.readValue(new File("config.json"), PizazzCfg.class);

            PizazzStore store = new PizazzStore(config.getStorageCapacity());
            Pizzeria pizzeria = new Pizzeria(store);

            pizzeria.initializeFromConfig("config.json");
            pizzeria.stopPizzeria(config.getWorkTime());
        } catch (IOException | InterruptedException e) {
            logger.error("An error occurred: {}", e.getMessage());
        }
    }
}