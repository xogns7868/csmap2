package com.example.administrator.csmap2.fragment;
import android.graphics.drawable.Drawable;

public class ListViewItem {
    private int type ;
    private Drawable iconDrawable ;
    private String titleStr ;
    private String Context ;

    public void setType(int type) { this.type = type ; }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setContext(String context) { Context = context ;}

    public int getType() { return this.type ; }
    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getContext() { return this.Context;}
    public String getTitle() {
        return this.titleStr ;
    }
}
