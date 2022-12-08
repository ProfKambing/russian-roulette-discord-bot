package me.kambing.utils;

public class UnExceedAbleInteger {
    private int integer;
    private final int initialValue;
    private final int maxValue;
    public UnExceedAbleInteger(int initialValue, int maxValue) {
        this.initialValue = initialValue;
        this.maxValue = maxValue;
        this.integer = initialValue;
    }

    public int getInteger() {
        if (integer > maxValue) {
            integer = initialValue;
        }
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public void increaseInteger() {
        integer++;
    }
    public int getRandom() {
        return (int) ((Math.random() * ((maxValue + 1) - initialValue)) + initialValue);
    }
}
