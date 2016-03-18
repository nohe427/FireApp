package com.alexandernohe.clttrailofhistoryreasearchapp;

/**
 * Created by alex7370 on 3/15/2016.
 */
public class StatueInformation {
    private String mName;
    private double x;
    private double y;

    public StatueInformation() {
        mName = "TEST";
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public StatueInformation(String Name, double X, double Y)
    {
        this.mName = Name;
        this.x = X;
        this.y = Y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name)
    {
        this.mName = Name;
    }
}
