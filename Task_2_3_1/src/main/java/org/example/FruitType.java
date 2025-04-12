package org.example;

public enum FruitType {
    APPLE(1, 1.0, "/images/apple.png", 1),
    COCONUT(1, 0.8, "/images/coconut.png", 1),
    PINEAPPLE(2, 1.2, "/images/pineapple.png", 2);


    private final int growth;
    private final double speedMultiplier;
    private final String imagePath;
    private final double sizeMultiplier;

    FruitType(int growth, double speedMultiplier, String imagePath, double sizeMultiplier) {
        this.growth = growth;
        this.speedMultiplier = speedMultiplier;
        this.imagePath = imagePath;
        this.sizeMultiplier = sizeMultiplier;
    }

    // Геттеры
    public int getGrowth() { return growth; }
    public double getSpeedMultiplier() { return speedMultiplier; }
    public String getImagePath() { return imagePath; }
    public double getSizeMultiplier() { return sizeMultiplier; }
}