package com.example.travel2;

public class Schedule {
    private String day;
    private String distance;
    private String picture;
    private String title;
    private String content;

    public Schedule(String _day,String _distance,String _picture,String _title,String _content){
        this.day = _day;
        this.distance = _distance;
        this.picture = _picture;
        this.title = _title;
        this.content = _content;
    }

    public String getDay() {
        return day;
    }

    public String getDistance() {
        return distance;
    }

    public String getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
