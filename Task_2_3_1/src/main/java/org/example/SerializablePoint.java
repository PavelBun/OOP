package org.example;

public class SerializablePoint {
    public int x;
    public int y;

    public SerializablePoint() {} // Нужен для Jackson

    public SerializablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public javafx.geometry.Point2D toPoint2D() {
        return new javafx.geometry.Point2D(x, y);
    }
}
