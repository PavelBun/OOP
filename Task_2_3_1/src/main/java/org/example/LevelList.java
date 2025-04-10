package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LevelList {
    @JsonProperty("levels")
    public List<LevelConfig> levels;
}
