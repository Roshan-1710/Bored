package com.example.projectsem5;

public class limitlessLeader {
    String Name,Time,Score;

    public limitlessLeader() {
    }

    public limitlessLeader(String name, String time, String score) {
        Name = name;
        Time = time;
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
