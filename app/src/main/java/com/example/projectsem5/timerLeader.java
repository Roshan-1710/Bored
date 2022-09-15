package com.example.projectsem5;

public class timerLeader {

    String Name, Score;

    public timerLeader() {
    }

    public timerLeader(String name, String score) {
        Name = name;
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getData(){ return Name+" "+Score; }
}
