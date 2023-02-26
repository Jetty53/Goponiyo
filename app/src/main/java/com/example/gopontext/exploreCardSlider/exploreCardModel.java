package com.example.gopontext.exploreCardSlider;

public class exploreCardModel {

    private String title;
    private String desc;
    private int id;

    public exploreCardModel(String title, String desc, int id) {
        this.title = title;
        this.desc = desc;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
