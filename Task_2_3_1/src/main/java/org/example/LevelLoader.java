package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class LevelLoader {
    private static final String LEVELS_FILE = "/levels/levels.json";

    public static LevelConfig loadLevel(int levelId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = LevelLoader.class.getResourceAsStream(LEVELS_FILE);

        LevelList levelList = mapper.readValue(is, LevelList.class);

        return levelList.levels.stream()
                .filter(l -> l.getId() == levelId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Level not found"));
    }
}
