package com.example.travel2;

public class Attractions {
    private int id;
    private String picture;
    private String title;
    private int tag1_position;
    private String tag1;
    private int tag2_position;
    private String tag2;
    private int tag3_position;
    private String tag3;
    private String content;
    private String phone;
    private String phoneNote;
    private String address;

    public Attractions(int _id,String _picture,String _title,int _tag1_position,String _tag1,
                       int _tag2_position,String _tag2,int _tag3_position,String _tag3,String _content,
                       String _phone,String _phoneNote,String _address){
        this.id = _id;
        this.picture = _picture;
        this.title = _title;
        this.tag1_position = _tag1_position;
        this.tag1 = _tag1;
        this.tag2_position = _tag2_position;
        this.tag2 = _tag2;
        this.tag3_position = _tag3_position;
        this.tag3 = _tag3;
        this.content = _content;
        this.phone = _phone;
        this.phoneNote = _phoneNote;
        this.address = _address;

    }

    public int getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public int getTag1_position() {
        return tag1_position;
    }
    public String getTag1() {
        return tag1;
    }

    public int getTag2_position() {
        return tag2_position;
    }

    public String getTag2() {
        return tag2;
    }

    public int getTag3_position() {
        return tag3_position;
    }

    public String getTag3() {
        return tag3;
    }

    public String getContent() {
        return content;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoneNote() {
        return phoneNote;
    }

    public String getAddress() {
        return address;
    }
}
