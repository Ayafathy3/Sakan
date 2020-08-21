package com.aya.sakan.ui.search;

public class Town {
    private int id;
    private String town_ar;
    private String town_en;

    public Town() {
    }

    public Town(int id, String town_ar, String town_en) {
        this.id = id;
        this.town_ar = town_ar;
        this.town_en = town_en;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTown_ar() {
        return town_ar;
    }
}
