package com.example.task2codingbat.entity.enums;

public enum StarBadgeValue {
    EASY(5), //5
    MEDIUM(10), //10
    HARD(25); //25


    private int value;

    StarBadgeValue(int value) {
        this.value = value;
    }

    public int getID() {
        return value;
    }

}

