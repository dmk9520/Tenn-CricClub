package com.example.jeet.tenncricclub;

public class TournamentModel {
    String teamname1 = "";

    String teamname2 = "";

    String date = "";
    String time = "";


    public TournamentModel(String teamname1, String teamname2, String date, String time) {
        this.teamname1 = teamname1;
        this.teamname2 = teamname2;
        this.date = date;
        this.time = time;

    }

    public String getTeamname1() {
        return teamname1;
    }

    public void setTeamname1(String teamname1) {
        this.teamname1 = teamname1;
    }

    public String getTeamname2() {
        return teamname2;
    }

    public void setTeamname2(String teamname2) {
        this.teamname2 = teamname2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
