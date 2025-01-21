package com.example.travel2;

public class Comment {
    private String name;
    private int star;
    private String comment;

    public Comment(String _name, int _star , String _comment){
        this.name = _name;
        this.star = _star;
        this.comment = _comment;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }

    public String getComment() {
        return comment;
    }
}
