package com.pluspow.model;

import java.io.Serializable;

public class ItemCounts implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int item = 0;
    
    private int hoto = 0;

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getHoto() {
        return hoto;
    }

    public void setHoto(int hoto) {
        this.hoto = hoto;
    }

}
