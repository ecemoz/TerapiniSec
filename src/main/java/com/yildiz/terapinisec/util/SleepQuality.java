package com.yildiz.terapinisec.util;

public enum SleepQuality {
    VERY_POOR(1),
    POOR(2),
    AVERAGE(3),
    GOOD(4),
    EXCELLENT(5);

    private final int value;

    SleepQuality(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SleepQuality fromValue(int value) {
        for (SleepQuality quality : SleepQuality.values()) {
            if (quality.getValue() == value) {
                return quality;
            }
        }
        throw new IllegalArgumentException("Invalid SleepQuality value: " + value);
    }
}