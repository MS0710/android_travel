package com.example.travel2;

public class ScheduleShort {
    private String mainTitle;
    private String picture;
    private String tag1;
    private String tag2;
    private String tag3;

    public ScheduleShort(String _mainTitle,String _picture,String _tag1,String _tag2,String _tag3){
        this.mainTitle = _mainTitle;
        this.picture = _picture;
        this.tag1 = _tag1;
        this.tag2 = _tag2;
        this.tag3 = _tag3;
    }
    public String getMainTitle() {
        return mainTitle;
    }
    public String getPicture() {
        return picture;
    }
    public String getTag1() {
        return tag1;
    }
    public String getTag2() {
        return tag2;
    }
    public String getTag3() {
        return tag3;
    }

}
