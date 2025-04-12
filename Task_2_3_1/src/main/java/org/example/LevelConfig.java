package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LevelConfig {
    @JsonProperty private int id;
    @JsonProperty private int targetLength;
    @JsonProperty private List<SerializablePoint> obstacles;

    public int getId() { return id; }
    public int getTargetLength() { return targetLength; }
    public List<javafx.geometry.Point2D> getObstacles() {
        return obstacles.stream().map(SerializablePoint::toPoint2D).toList();
    }
}
