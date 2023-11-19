package com.example.mytarea;

public class DataClass3 {
    private String title;
    private String startTime;
    private String endTime;

    public DataClass3(String title, String startTime, String endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDataTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
