package com.sbehnken.plethora.model;

public class UserEntry {

    private String word;
    private Integer points;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public UserEntry(String word, Integer points) {
        this.word = word;
        this.points = points;
    }
}
